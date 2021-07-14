/*
 * Copyright 2014-2019 Groupon, Inc
 * Copyright 2014-2019 The Billing Project, LLC
 *
 * The Billing Project licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package org.killbill.billing.plugin.qualpay;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import org.joda.money.CurrencyUnit;
import org.joda.time.DateTime;
import org.killbill.billing.ObjectType;
import org.killbill.billing.account.api.Account;
import org.killbill.billing.catalog.api.Currency;
import org.killbill.billing.osgi.libs.killbill.OSGIConfigPropertiesService;
import org.killbill.billing.osgi.libs.killbill.OSGIKillbillAPI;
import org.killbill.billing.osgi.libs.killbill.OSGIKillbillLogService;
import org.killbill.billing.payment.api.PaymentApiException;
import org.killbill.billing.payment.api.PaymentMethodPlugin;
import org.killbill.billing.payment.api.PaymentTransaction;
import org.killbill.billing.payment.api.PluginProperty;
import org.killbill.billing.payment.api.TransactionType;
import org.killbill.billing.payment.plugin.api.GatewayNotification;
import org.killbill.billing.payment.plugin.api.HostedPaymentPageFormDescriptor;
import org.killbill.billing.payment.plugin.api.PaymentMethodInfoPlugin;
import org.killbill.billing.payment.plugin.api.PaymentPluginApiException;
import org.killbill.billing.payment.plugin.api.PaymentPluginStatus;
import org.killbill.billing.payment.plugin.api.PaymentTransactionInfoPlugin;
import org.killbill.billing.plugin.api.PluginProperties;
import org.killbill.billing.plugin.api.core.PluginCustomField;
import org.killbill.billing.plugin.api.payment.PluginPaymentMethodInfoPlugin;
import org.killbill.billing.plugin.api.payment.PluginPaymentPluginApi;
import org.killbill.billing.plugin.qualpay.client.MpesaClientWrapper;
import org.killbill.billing.plugin.qualpay.client.PGApi;
import org.killbill.billing.plugin.qualpay.client.PGApiCaptureRequest;
import org.killbill.billing.plugin.qualpay.client.PGApiLineItem;
import org.killbill.billing.plugin.qualpay.client.PGApiRefundRequest;
import org.killbill.billing.plugin.qualpay.client.PGApiTransactionRequest;
import org.killbill.billing.plugin.qualpay.client.PGApiVoidRequest;
import org.killbill.billing.plugin.qualpay.dao.QualpayDao;
import org.killbill.billing.plugin.qualpay.dao.gen.tables.QualpayPaymentMethods;
import org.killbill.billing.plugin.qualpay.dao.gen.tables.QualpayResponses;
import org.killbill.billing.plugin.qualpay.dao.gen.tables.records.QualpayPaymentMethodsRecord;
import org.killbill.billing.plugin.qualpay.dao.gen.tables.records.QualpayResponsesRecord;
import org.killbill.billing.plugin.qualpay.models.APIResourceConstants;
import org.killbill.billing.plugin.qualpay.models.Business2CustomerReq;
import org.killbill.billing.plugin.qualpay.models.OnlinePaymentRequest;
import org.killbill.billing.plugin.qualpay.models.OnlineSuccessfullRes;
import org.killbill.billing.plugin.qualpay.models.B2C.Business2CustomerResponse;
import org.killbill.billing.util.api.CustomFieldApiException;
import org.killbill.billing.util.callcontext.CallContext;
import org.killbill.billing.util.callcontext.TenantContext;
import org.killbill.billing.util.customfield.CustomField;
import org.killbill.clock.Clock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import io.swagger.client.api.CustomerVaultApi;
import io.swagger.client.model.AddBillingCardRequest;
import io.swagger.client.model.AddCustomerRequest;
import io.swagger.client.model.BillingCard;
import io.swagger.client.model.CustomerResponse;
import io.swagger.client.model.CustomerVault;
import io.swagger.client.model.DeleteBillingCardRequest;
import io.swagger.client.model.GatewayResponse;
import io.swagger.client.model.GetBillingCardsResponse;
import io.swagger.client.model.GetBillingResponse;
import qpPlatform.ApiClient;
import qpPlatform.ApiException;
import qpPlatform.Configuration;

public class QualpayPaymentPluginApi extends
        PluginPaymentPluginApi<QualpayResponsesRecord, QualpayResponses, QualpayPaymentMethodsRecord, QualpayPaymentMethods> {

    private static final Logger logger = LoggerFactory.getLogger(QualpayPaymentPluginApi.class);

    public static final String PROPERTY_OVERRIDDEN_TRANSACTION_STATUS = "overriddenTransactionStatus";

    private final QualpayConfigPropertiesConfigurationHandler qualpayConfigPropertiesConfigurationHandler;
    private final QualpayDao dao;

    public QualpayPaymentPluginApi(
            final QualpayConfigPropertiesConfigurationHandler qualpayConfigPropertiesConfigurationHandler,
            final OSGIKillbillAPI killbillAPI, final OSGIConfigPropertiesService configProperties,
            final OSGIKillbillLogService logService, final Clock clock, final QualpayDao dao) {
        super(killbillAPI, configProperties, logService, clock, dao);
        this.qualpayConfigPropertiesConfigurationHandler = qualpayConfigPropertiesConfigurationHandler;
        this.dao = dao;
    }

    @Override
    protected PaymentTransactionInfoPlugin buildPaymentTransactionInfoPlugin(final QualpayResponsesRecord record) {
        return QualpayPaymentTransactionInfoPlugin.build(record);
    }

    @Override
    protected PaymentMethodPlugin buildPaymentMethodPlugin(final QualpayPaymentMethodsRecord record) {
        return QualpayPaymentMethodPlugin.build(record);
    }

    @Override
    protected PaymentMethodInfoPlugin buildPaymentMethodInfoPlugin(final QualpayPaymentMethodsRecord record) {
        return new PluginPaymentMethodInfoPlugin(UUID.fromString(record.getKbAccountId()),
                UUID.fromString(record.getKbPaymentMethodId()), false, record.getQualpayId());
    }

    @Override
    public void addPaymentMethod(final UUID kbAccountId, final UUID kbPaymentMethodId,
            final PaymentMethodPlugin paymentMethodProps, final boolean setDefault,
            final Iterable<PluginProperty> properties, final CallContext context) throws PaymentPluginApiException {
                // customerId (kbClientAccountId) has the key QUALPAY_CUSTOMER_ID on plugin
            final String qualpayId = PluginProperties.findPluginPropertyValue("QUALPAY_CUSTOMER_ID", properties);
            //
            // Add the magic Custom Field
            final PluginCustomField customField = new PluginCustomField(kbAccountId, ObjectType.ACCOUNT,
                    "QUALPAY_CUSTOMER_ID", qualpayId, clock.getUTCNow());
            
            try {
                final QualpayConfigProperties qualpayConfigProperties = qualpayConfigPropertiesConfigurationHandler
                        .getConfigurable(context.getTenantId());
                killbillAPI.getSecurityApi().login(qualpayConfigProperties.getKbUsername(),
                        qualpayConfigProperties.getKbPassword());
                killbillAPI.getCustomFieldUserApi().addCustomFields(ImmutableList.<CustomField>of(customField),
                        context);
            } catch (CustomFieldApiException e) {
                // TODO Auto-generated catch block
                throw new PaymentPluginApiException("unable to add customfield to killbill", e);
            } finally {
                killbillAPI.getSecurityApi().logout();
            }

            final Map<String, Object> additionalDataMap = PluginProperties.toMap(properties);
            final DateTime utcNow = clock.getUTCNow();
            try {
                dao.addPaymentMethod(kbAccountId, kbPaymentMethodId, additionalDataMap, qualpayId, utcNow,
                        context.getTenantId());
            } catch (final SQLException e) {
                throw new PaymentPluginApiException("Unable to add payment method to database", e);
            }
        }

    @Override
    protected String getPaymentMethodId(final QualpayPaymentMethodsRecord record) {
        return record.getKbPaymentMethodId();
    }

    @Override
    public void deletePaymentMethod(final UUID kbAccountId, final UUID kbPaymentMethodId,
            final Iterable<PluginProperty> properties, final CallContext context) throws PaymentPluginApiException {
        // Delete our local copy
        super.deletePaymentMethod(kbAccountId, kbPaymentMethodId, properties, context);
    }

    @Override
    public List<PaymentMethodInfoPlugin> getPaymentMethods(final UUID kbAccountId, final boolean refreshFromGateway,
            final Iterable<PluginProperty> properties, final CallContext context) throws PaymentPluginApiException {
        // If refreshFromGateway isn't set, simply read our tables
        if (!refreshFromGateway) {
            return super.getPaymentMethods(kbAccountId, refreshFromGateway, properties, context);
        }

        // Refresh the state
        return super.getPaymentMethods(kbAccountId, false, properties, context);
    }

    private void syncPaymentMethods(final UUID kbAccountId, final Iterable<BillingCard> billingCards,
            final Map<String, QualpayPaymentMethodsRecord> existingPaymentMethodByQualpayId, final CallContext context)
            throws PaymentApiException, SQLException {
        for (final BillingCard billingCard : billingCards) {
            final Map<String, Object> additionalDataMap = QualpayPluginProperties.toAdditionalDataMap(billingCard);

            final QualpayPaymentMethodsRecord existingPaymentMethodRecord = existingPaymentMethodByQualpayId
                    .remove(billingCard.getCardId());
            if (existingPaymentMethodRecord == null) {
                // We don't know about it yet, create it
                logger.info("Creating new local Qualpay payment method {}", billingCard.getCardId());
                final List<PluginProperty> properties = PluginProperties.buildPluginProperties(additionalDataMap);
                final PaymentMethodPlugin paymentMethodInfo = new QualpayPaymentMethodPlugin(null,
                        billingCard.getCardId(), properties);
                killbillAPI.getPaymentApi().addPaymentMethod(getAccount(kbAccountId, context), billingCard.getCardId(),
                        QualpayActivator.PLUGIN_NAME, false, paymentMethodInfo, ImmutableList.<PluginProperty>of(),
                        context);
            } else {
                logger.info("Updating existing local Qualpay payment method {}", billingCard);
                dao.updatePaymentMethod(UUID.fromString(existingPaymentMethodRecord.getKbPaymentMethodId()),
                        additionalDataMap, billingCard.getCardId(), clock.getUTCNow(), context.getTenantId());
            }
        }
    }

    @Override
    public PaymentTransactionInfoPlugin authorizePayment(final UUID kbAccountId, final UUID kbPaymentId,
            final UUID kbTransactionId, final UUID kbPaymentMethodId, final BigDecimal amount, final Currency currency,
            final Iterable<PluginProperty> properties, final CallContext context) throws PaymentPluginApiException {
        try {
            return executeInitialTransaction(TransactionType.AUTHORIZE, kbAccountId, kbPaymentId, kbTransactionId,
                    kbPaymentMethodId, amount, currency, properties, context);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new PaymentPluginApiException("INTERNAL", "#authorizePayment, please contact support team");
        }
    }

    @Override
    public PaymentTransactionInfoPlugin capturePayment(final UUID kbAccountId, final UUID kbPaymentId,
            final UUID kbTransactionId, final UUID kbPaymentMethodId, final BigDecimal amount, final Currency currency,
            final Iterable<PluginProperty> properties, final CallContext context) throws PaymentPluginApiException {
               final QualpayResponsesRecord responsesRecord = new QualpayResponsesRecord();
               return QualpayPaymentTransactionInfoPlugin.build(responsesRecord);
        }

    @Override
    public PaymentTransactionInfoPlugin purchasePayment(final UUID kbAccountId, final UUID kbPaymentId,
            final UUID kbTransactionId, final UUID kbPaymentMethodId, final BigDecimal amount, final Currency currency,
            final Iterable<PluginProperty> properties, final CallContext context) throws PaymentPluginApiException {
        try {
            return executeInitialTransaction(TransactionType.PURCHASE, kbAccountId, kbPaymentId, kbTransactionId,
                    kbPaymentMethodId, amount, currency, properties, context);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new PaymentPluginApiException("INTERNAL", "#purchasePayment error, please contact support team");
        }
    }

    @Override
    public PaymentTransactionInfoPlugin voidPayment(final UUID kbAccountId, final UUID kbPaymentId,
            final UUID kbTransactionId, final UUID kbPaymentMethodId, final Iterable<PluginProperty> properties,
            final CallContext context) throws PaymentPluginApiException {
                final QualpayResponsesRecord responsesRecord = new QualpayResponsesRecord();
               return QualpayPaymentTransactionInfoPlugin.build(responsesRecord);
            }

    @Override
    public PaymentTransactionInfoPlugin creditPayment(final UUID kbAccountId, final UUID kbPaymentId,
            final UUID kbTransactionId, final UUID kbPaymentMethodId, final BigDecimal amount, final Currency currency,
            final Iterable<PluginProperty> properties, final CallContext context) throws PaymentPluginApiException {
                try {
            return executeInitialTransaction(TransactionType.CREDIT, kbAccountId, kbPaymentId, kbTransactionId,
                    kbPaymentMethodId, amount, "PromotionPayment", currency, properties, context);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new PaymentPluginApiException("something went wrong with creditPayment please contact admin", e);
        }
            }
    @Override
    public PaymentTransactionInfoPlugin refundPayment(final UUID kbAccountId, final UUID kbPaymentId,
            final UUID kbTransactionId, final UUID kbPaymentMethodId, final BigDecimal amount, final Currency currency,
            final Iterable<PluginProperty> properties, final CallContext context) throws PaymentPluginApiException {
               final QualpayResponsesRecord responsesRecord = new QualpayResponsesRecord();
               return QualpayPaymentTransactionInfoPlugin.build(responsesRecord);
    }

    private PaymentTransactionInfoPlugin creditClient(final UUID kbAccountId, final UUID kbClientAccountId,
            final UUID kbTransactionId, final UUID kbPaymentMethodId,final UUID kbPaymentId, final BigDecimal amount,
            final String commandId, final Currency currency,
            final Iterable<PluginProperty> properties, final CallContext context) throws PaymentPluginApiException, IOException {
                 //TODO: probably we should use executeInitalTransaction
			return executeInitialTransaction(TransactionType.PURCHASE, kbAccountId, kbPaymentId, kbTransactionId,
                kbPaymentMethodId, amount, commandId, currency, properties, context);
    }

    @VisibleForTesting
    ApiClient buildApiClient(final TenantContext context, final boolean platform) {
        final QualpayConfigProperties qualpayConfigProperties = qualpayConfigPropertiesConfigurationHandler
                .getConfigurable(context.getTenantId());

        final ApiClient apiClient = Configuration.getDefaultApiClient();
        apiClient.setUsername(qualpayConfigProperties.getApiKey());
        apiClient.setBasePath(qualpayConfigProperties.getBaseUrl() + (platform ? "/platform" : ""));
        apiClient.setConnectTimeout(Integer.parseInt(qualpayConfigProperties.getConnectionTimeout()));
        apiClient.setReadTimeout(Integer.parseInt(qualpayConfigProperties.getReadTimeout()));
        apiClient.setUserAgent("KillBill/1.0");

        return apiClient;
    }

    MpesaClientWrapper buildMpesaClient(final TenantContext context) throws PaymentPluginApiException, IOException {
             final QualpayConfigProperties mpesaConfigProperties = qualpayConfigPropertiesConfigurationHandler
                .getConfigurable(context.getTenantId());
                MpesaClientWrapper mpesaClient = null;
                try {
                    mpesaClient = new MpesaClientWrapper(mpesaConfigProperties.getBaseUrl(),
                                                        mpesaConfigProperties.getApiKey(),
                                                        mpesaConfigProperties.getApiKey(),//TODO: its ApiSecret
                                                         null, null, false);
                } catch (GeneralSecurityException e1) {
                    //TODO: handle exception
                    mpesaClient.close();
                    throw new PaymentPluginApiException("error connecting to Mpesa Servers", e1);
                }
                return mpesaClient;
    }

    @Override
    public HostedPaymentPageFormDescriptor buildFormDescriptor(final UUID kbAccountId, final Iterable<PluginProperty> customFields, final Iterable<PluginProperty> properties, final CallContext context) throws PaymentPluginApiException {
        throw new PaymentPluginApiException("INTERNAL", "#buildFormDescriptor not yet implemented, please contact support@killbill.io");
    }

    @Override
    public GatewayNotification processNotification(final String notification, final Iterable<PluginProperty> properties, final CallContext context) throws PaymentPluginApiException {
        throw new PaymentPluginApiException("INTERNAL", "#processNotification not yet implemented, please contact support@killbill.io");
    }

    private abstract static class TransactionExecutor<T> {

        public T execute(final Account account, final QualpayPaymentMethodsRecord paymentMethodsRecord) throws ApiException, SQLException, IOException, PaymentPluginApiException {
            throw new UnsupportedOperationException();

        }

        public T execute(final Account account, final QualpayPaymentMethodsRecord paymentMethodsRecord, final QualpayResponsesRecord previousResponse) throws ApiException, PaymentPluginApiException, IOException {
            throw new UnsupportedOperationException();
        }
    }

    private PaymentTransactionInfoPlugin executeInitialTransaction(final TransactionType transactionType,
                                                                    final UUID kbAccountId,
                                                                    final UUID kbPaymentId,
                                                                    final UUID kbTransactionId,
                                                                    final UUID kbPaymentMethodId,
                                                                    final BigDecimal amount,
                                                                    final String commandId,
                                                                    final Currency currency,
                                                                    final Iterable<PluginProperty> properties,
                                                                    final CallContext context)throws PaymentPluginApiException, IOException {
        return executeInitialTransaction(transactionType, new TransactionExecutor<Business2CustomerResponse>() {
                                            @Override
                                            public Business2CustomerResponse execute(final Account account,
                                                    final QualpayPaymentMethodsRecord paymentMethodsRecord)throws ApiException, SQLException, IOException, PaymentPluginApiException {
                                               final MpesaClientWrapper mpesaApiClient = buildMpesaClient(context);
                                                //TODO mpesaConfigProps to replease ApiResourceConstants
                                                final QualpayConfigProperties mpesaConfigProps = qualpayConfigPropertiesConfigurationHandler
                                                                .getConfigurable(context.getTenantId());

                                                Double clientAmount = amount.doubleValue();
                                                Business2CustomerReq b2cRequest = new Business2CustomerReq(
                                                                        APIResourceConstants.SecurityConstants.INITIATOR_NAME, 
                                                                        APIResourceConstants.SecurityConstants.INITIATOR_PASSWORD,
                                                                        commandId, //"PromotionPayment"
                                                                        clientAmount, //TODO: SANDBOX_SHORTCODE From configprops
                                                                        String.valueOf(APIResourceConstants.SANDBOX_SHORTCODE),
                                                                        account.getExternalKey(), "test b2c model", 
                                                                        "https://64.227.127.143:8080/plugin/qualpay/timeout",
                                                                        "https://64.227.127.143:8080/plugin/qualpay/results",
                                                                        "here is your cut");
                                                Business2CustomerResponse response = mpesaApiClient.mpesaB2CRequest(b2cRequest);
                                                mpesaApiClient.close();
                                                return response; 
                                            }
        }, kbAccountId, kbPaymentId, kbTransactionId, kbPaymentMethodId, amount, commandId, currency, properties, context);
    }

    private PaymentTransactionInfoPlugin executeInitialTransaction(final TransactionType transactionType,
                                                                   final TransactionExecutor<Business2CustomerResponse> transactionExecutor,
                                                                   final UUID kbAccountId,
                                                                   final UUID kbPaymentId,
                                                                   final UUID kbTransactionId,
                                                                   final UUID kbPaymentMethodId,
                                                                   final BigDecimal amount,
                                                                   final String commandId,
                                                                   final Currency currency,
                                                                   final Iterable<PluginProperty> properties,
                                                                   final TenantContext context) throws PaymentPluginApiException, IOException {
        final Account account = getAccount(kbAccountId, context);
        final QualpayPaymentMethodsRecord nonNullPaymentMethodsRecord = getQualpayPaymentMethodsRecord(kbPaymentMethodId, context);
        final DateTime utcNow = clock.getUTCNow();

        final Business2CustomerResponse response;
        if (shouldSkipQualpay(properties)) {
            throw new UnsupportedOperationException("skip_gw=true not yet implemented, please contact support@killbill.io");
        } else {
            try {
                response = transactionExecutor.execute(account, nonNullPaymentMethodsRecord);
            } catch (final ApiException e) {
                throw new PaymentPluginApiException("Error connecting to Qualpay", e);
            } catch (final SQLException e) {
                throw new PaymentPluginApiException("Unable to submit payment, we encountered a database error", e);
            }
        }

        try {//...............................................TODO: deal with response from gateway to mpesa
            final QualpayResponsesRecord responsesRecord = dao.addResponse(kbAccountId, kbPaymentId, kbTransactionId, transactionType, amount, currency, response, utcNow, context.getTenantId());
            return QualpayPaymentTransactionInfoPlugin.build(responsesRecord);
        } catch (final SQLException e) {
            throw new PaymentPluginApiException("Payment went through, but we encountered a database error. Payment details: " + response.toString(), e);
        }
    }
    

    private PaymentTransactionInfoPlugin executeInitialTransaction(final TransactionType transactionType,
                                                                   final UUID kbAccountId,
                                                                   final UUID kbPaymentId,
                                                                   final UUID kbTransactionId,
                                                                   final UUID kbPaymentMethodId,
                                                                   final BigDecimal amount,
                                                                   final Currency currency,
                                                                   final Iterable<PluginProperty> properties,
                                                                   final CallContext context) throws PaymentPluginApiException, IOException {
        return executeInitialTransaction(transactionType, new TransactionExecutor<OnlineSuccessfullRes>() {
                                            @Override
                                            public OnlineSuccessfullRes execute(final Account account,
                                                    final QualpayPaymentMethodsRecord paymentMethodsRecord) throws ApiException, SQLException {
                                                
                                                final OnlinePaymentRequest onlinePaymentRequest = new OnlinePaymentRequest(
                                                                                "CustomerPayBillOnline", 
                                                                                amount.doubleValue(), 
                                                                                account.getExternalKey(), 
                                                                                kbTransactionId.toString(),
                                                                                qualpayConfigPropertiesConfigurationHandler
                                                                                .getConfigurable(context.getTenantId()).getChargeDescription()); 
                                                MpesaClientWrapper gateAwayClient = null;
                                        try {
                                            gateAwayClient = buildMpesaClient(context);
                                        } catch (PaymentPluginApiException | IOException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }


                                                
                                                final QualpayPaymentMethodsRecord paymentMethod = dao.getPaymentMethod(kbPaymentMethodId,
                                                        context.getTenantId());
                                            

                                                logger.debug("Creating Qualpay transaction: {}", onlinePaymentRequest);
                                                switch (transactionType) {
                                                case AUTHORIZE:
                                                    return gateAwayClient.mpesaOnlineRequest(onlinePaymentRequest);// pgApi.authorize(pgApiTransactionRequest);
                                                case PURCHASE:
                                                    return gateAwayClient.mpesaOnlineRequest(onlinePaymentRequest); // pgApi.sale(pgApiTransactionRequest);
                                                default:
                                                    throw new UnsupportedOperationException(transactionType.toString());
                                                }
                                                
                                            } 
                                        },
                                         kbAccountId,
                                         kbPaymentId,
                                         kbTransactionId,
                                         kbPaymentMethodId,
                                         amount,
                                         currency,
                                         properties,
                                         context);
    }

    private PaymentTransactionInfoPlugin executeInitialTransaction(final TransactionType transactionType,
                                                                   final TransactionExecutor<OnlineSuccessfullRes> transactionExecutor,
                                                                   final UUID kbAccountId,
                                                                   final UUID kbPaymentId,
                                                                   final UUID kbTransactionId,
                                                                   final UUID kbPaymentMethodId,
                                                                   final BigDecimal amount,
                                                                   final Currency currency,
                                                                   final Iterable<PluginProperty> properties,
                                                                   final TenantContext context) throws PaymentPluginApiException, IOException {
        final Account account = getAccount(kbAccountId, context);
        final QualpayPaymentMethodsRecord nonNullPaymentMethodsRecord = getQualpayPaymentMethodsRecord(kbPaymentMethodId, context);
        final DateTime utcNow = clock.getUTCNow();

        final OnlineSuccessfullRes response;
        if (shouldSkipQualpay(properties)) {
            throw new UnsupportedOperationException("skip_gw=true not yet implemented, please contact support@killbill.io");
        } else {
            try {
                response = transactionExecutor.execute(account, nonNullPaymentMethodsRecord);
            } catch (final ApiException e) {
                throw new PaymentPluginApiException("Error connecting to Qualpay", e);
            } catch (final SQLException e) {
                throw new PaymentPluginApiException("Unable to submit payment, we encountered a database error", e);
            }
        }

        try {//...............................................TODO: deal with response from gateway to mpesa
            final QualpayResponsesRecord responsesRecord = dao.addResponse(kbAccountId, kbPaymentId, kbTransactionId, transactionType, amount, currency, response, utcNow, context.getTenantId());
            return QualpayPaymentTransactionInfoPlugin.build(responsesRecord);
        } catch (final SQLException e) {
            throw new PaymentPluginApiException("Payment went through, but we encountered a database error. Payment details: " + response.toString(), e);
        }
    }

    private PaymentTransactionInfoPlugin executeFollowUpTransaction(final TransactionType transactionType,
                                                                    final TransactionExecutor<GatewayResponse> transactionExecutor,
                                                                    final UUID kbAccountId,
                                                                    final UUID kbPaymentId,
                                                                    final UUID kbTransactionId,
                                                                    final UUID kbPaymentMethodId,
                                                                    @Nullable final BigDecimal amount,
                                                                    @Nullable final Currency currency,
                                                                    final Iterable<PluginProperty> properties,
                                                                    final TenantContext context) throws PaymentPluginApiException {
        final Account account = getAccount(kbAccountId, context);
        final QualpayPaymentMethodsRecord nonNullPaymentMethodsRecord = getQualpayPaymentMethodsRecord(kbPaymentMethodId, context);

        final QualpayResponsesRecord previousResponse;
        try {
            previousResponse = dao.getSuccessfulAuthorizationResponse(kbPaymentId, context.getTenantId());
            if (previousResponse == null) {
                throw new PaymentPluginApiException(null, "Unable to retrieve previous payment response for kbTransactionId " + kbTransactionId);
            }
        } catch (final SQLException e) {
            throw new PaymentPluginApiException("Unable to retrieve previous payment response for kbTransactionId " + kbTransactionId, e);
        }

        final DateTime utcNow = clock.getUTCNow();

        final GatewayResponse response;
        if (shouldSkipQualpay(properties)) {
            throw new UnsupportedOperationException("skip_gw=true not yet implemented, please contact support@killbill.io");
        } else {
            try {
                response = transactionExecutor.execute(account, nonNullPaymentMethodsRecord, previousResponse);
            } catch (final ApiException e) {
                throw new PaymentPluginApiException("Error connecting to Qualpay", e);
            }
        }

        try {
            final QualpayResponsesRecord responsesRecord = dao.addResponse(kbAccountId, kbPaymentId, kbTransactionId, transactionType, amount, currency, response, utcNow, context.getTenantId());
            return QualpayPaymentTransactionInfoPlugin.build(responsesRecord);
        } catch (final SQLException e) {
            throw new PaymentPluginApiException("Payment went through, but we encountered a database error. Payment details: " + (response.toString()), e);
        }
    }

    @VisibleForTesting
    Long getMerchantId(final TenantContext context) {
        final QualpayConfigProperties qualpayConfigProperties = qualpayConfigPropertiesConfigurationHandler.getConfigurable(context.getTenantId());
        return Long.valueOf(MoreObjects.firstNonNull(qualpayConfigProperties.getMerchantId(), "0"));
    }

    private String getCustomerId(final UUID kbAccountId, final CallContext context) throws PaymentPluginApiException {
        final String qualpayCustomerId = getCustomerIdNoException(kbAccountId, context);
        if (qualpayCustomerId == null) {
            throw new PaymentPluginApiException("INTERNAL", "Missing QUALPAY_CUSTOMER_ID custom field");
        }
        return qualpayCustomerId;
    }

    private String getCustomerIdNoException(final UUID kbAccountId, final CallContext context) {
        final List<CustomField> customFields = killbillAPI.getCustomFieldUserApi().getCustomFieldsForAccountType(kbAccountId, ObjectType.ACCOUNT, context);
        String qualpayCustomerId = null;
        for (final CustomField customField : customFields) {
            if ("QUALPAY_CUSTOMER_ID".equals(customField.getFieldName())) {
                qualpayCustomerId = customField.getFieldValue();
                break;
            }
        }
        return qualpayCustomerId;
    }

    private QualpayPaymentMethodsRecord getQualpayPaymentMethodsRecord(@Nullable final UUID kbPaymentMethodId, final TenantContext context) throws PaymentPluginApiException {
        QualpayPaymentMethodsRecord paymentMethodsRecord = null;

        if (kbPaymentMethodId != null) {
            try {
                paymentMethodsRecord = dao.getPaymentMethod(kbPaymentMethodId, context.getTenantId());
            } catch (final SQLException e) {
                throw new PaymentPluginApiException("Failed to retrieve payment method", e);
            }
        }

        return MoreObjects.firstNonNull(paymentMethodsRecord, emptyRecord(kbPaymentMethodId));
    }

    private QualpayPaymentMethodsRecord emptyRecord(@Nullable final UUID kbPaymentMethodId) {
        final QualpayPaymentMethodsRecord record = new QualpayPaymentMethodsRecord();
        if (kbPaymentMethodId != null) {
            record.setKbPaymentMethodId(kbPaymentMethodId.toString());
        }
        return record;
    }

    private boolean shouldSkipQualpay(final Iterable<PluginProperty> properties) {
        return "true".equals(PluginProperties.findPluginPropertyValue("skipGw", properties)) || "true".equals(PluginProperties.findPluginPropertyValue("skip_gw", properties));
    }
}

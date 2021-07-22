package org.killbill.billing.plugin.qualpay;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import org.checkerframework.checker.units.qual.A;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.killbill.billing.account.api.Account;
import org.killbill.billing.account.api.AccountApiException;
import org.killbill.billing.catalog.api.Currency;
import org.killbill.billing.invoice.api.Invoice;
import org.killbill.billing.invoice.api.InvoiceApiException;
import org.killbill.billing.invoice.api.InvoiceItem;
import org.killbill.billing.invoice.api.InvoiceItemType;
import org.killbill.billing.invoice.api.formatters.InvoiceItemFormatter;
import org.killbill.billing.osgi.api.PluginInfo;
import org.killbill.billing.osgi.libs.killbill.OSGIKillbillAPI;
import org.killbill.billing.osgi.libs.killbill.OSGIKillbillLogService;
import org.killbill.billing.plugin.api.PluginCallContext;
import org.killbill.billing.plugin.api.PluginTenantContext;
import org.killbill.billing.plugin.api.invoice.PluginInvoiceItem;
import org.killbill.billing.payment.api.PluginProperty;
import org.killbill.billing.tenant.api.TenantApiException;
import org.killbill.billing.util.callcontext.CallContext;
import org.killbill.billing.util.callcontext.TenantContext;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.fasterxml.jackson.core.JsonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.killbill.billing.plugin.qualpay.models.ConfirmationRequest;

public class Confirmation {

    private static final Logger logger = LoggerFactory.getLogger(Confirmation.class);

    private OSGIKillbillAPI killbillAPI;
    private UUID tenantId;
    private TenantContext tenantContext;

    public Confirmation(final OSGIKillbillAPI apiKillbill) {
        this.killbillAPI = apiKillbill;
        this.tenantId = UUID.randomUUID();
        try { // TODO: .........................................//bob should come from
              // ...............................................//configproperties
            this.tenantId = this.killbillAPI.getTenantUserApi().getTenantByApiKey("bob").getId();
        } catch (TenantApiException e) {
            // TODO: nothing can happen without tenantId just shutdown we cant get user acc
            // neither
            // can we insert userCredits
            logger.error("unable to get tenantId from killbillAPI hence can't get user Account\\n"
                    + "neither can we insert credits hence we are shutting down");
            // TODO: how do we shutdown? we really need to shutdown
            e.printStackTrace();
        }
        this.tenantContext = new PluginTenantContext(null, tenantId);
    }

    // THis function works well and passes all tests
    public Account getUserAccount(String phoneNumber) {
        try {
            if (!Strings.isNullOrEmpty(phoneNumber))
                return this.killbillAPI.getAccountUserApi().getAccountByKey(phoneNumber, tenantContext);
        } catch (AccountApiException apiException) {
            // TODO: if user does not exist lets create one Shall we
            logger.error("error trying to get user Account does that mean user credits got lost? {}", apiException);
            return null;
        }
        return null;
    }

    public List<InvoiceItem> insertCreditToUserAccount(ConfirmationRequest confirmationJson) {

        Account acc = getUserAccount(confirmationJson.getMSISDN().toString());
        UUID accountUuid = acc.getId();
        DateTimeZone timeZone = acc.getTimeZone();
        CallContext pluginCallContext = new PluginCallContext(QualpayActivator.PLUGIN_NAME, DateTime.now(timeZone),
                accountUuid, tenantId);
        BigDecimal userAmount = BigDecimal.valueOf(confirmationJson.getTransAmount());
        ArrayList<PluginProperty> pluginIterables = new ArrayList<PluginProperty>();
       
        try {
            InvoiceItem plgInvoiceItem = createInvoiceItem(accountUuid,timeZone, userAmount);
            ArrayList<InvoiceItem> invoiceItems = new ArrayList<InvoiceItem>();
            invoiceItems.add(plgInvoiceItem); 

            return this.killbillAPI.getInvoiceUserApi().insertCredits(accountUuid, LocalDate.now(timeZone),
                    invoiceItems, true, pluginIterables, pluginCallContext);
        } catch (Exception invoiceApiException) {
            logger.error("something went wrong trying to add credits. This should never happen '{}'",
                    invoiceApiException.getMessage());
            return null;
        }
    }

    public InvoiceItem createInvoiceItem(UUID accountId, DateTimeZone zoneTime, BigDecimal cashAmount) {

       InvoiceItem itemInvoice = new PluginInvoiceItem(UUID.randomUUID(), InvoiceItemType.CREDIT_ADJ, null
       , accountId, null, LocalDate.now(zoneTime), null, cashAmount, Currency.KES,
        "Mpesa for Watching Movies", null, null, null, null, null, null, null, null, null, null, null, null, null, null,
        null, DateTime.now(zoneTime), null);
        
        return itemInvoice;
    }

}
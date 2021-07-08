package org.killbill.billing.plugin.qualpay.client;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.annotation.Nullable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableMap;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.killbill.billing.plugin.qualpay.models.APIResourceConstants;
import org.killbill.billing.plugin.qualpay.models.Business2CustomerReq;
import org.killbill.billing.plugin.qualpay.models.B2C.*;
import org.killbill.billing.plugin.qualpay.models.OnlinePaymentRequest;
import org.killbill.billing.plugin.qualpay.models.OnlineSuccessfullRes;
import org.killbill.billing.plugin.qualpay.models.RegisterURLRequest;
import org.killbill.billing.plugin.qualpay.models.RegisterURLResponse;
import org.killbill.billing.plugin.qualpay.models.SimulateTransactionRequest;
import org.killbill.billing.plugin.qualpay.models.SimulateTransactionResponse;
import org.killbill.billing.plugin.qualpay.models.TransactionStatusRequest;
import org.killbill.billing.plugin.qualpay.models.TransactionStatusResponse;
import org.killbill.billing.plugin.util.http.HttpClient;
import org.killbill.billing.plugin.util.http.InvalidRequest;
import org.killbill.billing.plugin.util.http.ResponseFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MpesaClientWrapper extends HttpClient {

    public static final String EXCEPTION_CLASS = "exceptionClass";
    public static final String EXCEPTION_MESSAGE = "exceptionMessage";
    public static final String MPESA_MESSAGE = "mpesaMessage";

    private static final Logger logger = LoggerFactory.getLogger(MpesaClientWrapper.class);

    private final String apiKey;
    private final String apiSecret;
    private final String token;

    public MpesaClientWrapper(String url, String apiKey, String apiSecret, String proxyHost, Integer proxyPort,
            Boolean strictSSL) throws GeneralSecurityException {
        super(url, null, null, proxyHost, proxyPort, strictSSL);
        // TODO Auto-generated constructor stub
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.token = getToken();
    }

    public static void main(String[] args) {
        MpesaClientWrapper mpesaGateway = null;
        /**TransactionStatusRequest tStatusRequest = new TransactionStatusRequest("testapi",
              "19195-3838049-2", "254725085687", IdentifierTypes.SHORTCODE.ordinal(), 
              "https://192.168.100.2/kb/mpkillbill/callmeback" 
              ,"https://192.168.100.2/kb/mpkillbill/qtimeout" , "keeping up with this payment", "100");
        **/
         //SimulateTransactionRequest sTransactionRequest = new SimulateTransactionRequest(600425,40.00,"254725085687", "mpkb_transaction101");
         RegisterURLRequest rUrlRequest = new RegisterURLRequest(600425, "http://64.227.127.143:8080/plugins/hello-world-plugin/Confirmation", 
                                                                "http://64.227.127.143:8080/plugins/hello-world-plugin/Validation");
        /**MpesaCoreBase mCoreBase = new MpesaCoreBase(600425);
        System.out.println(String.format("shortCode = %d, passkey=%s,\\ntimestamp=%d\\npassword=%s",
         mCoreBase.BusinessShortCode, mCoreBase.Passkey,mCoreBase.timestamp,mCoreBase.Password));
         **/
         OnlinePaymentRequest onlineRequest = new OnlinePaymentRequest("CustomerPayBillOnline", 
                                                                   Double.valueOf("50.00") , 
                                                                   "254725085687" , "wiflixdaily",
                                                                    "movies subscription");

        OnlineSuccessfullRes response = new OnlineSuccessfullRes();
        Business2CustomerReq b2cRequest = new Business2CustomerReq("testapi", 
                                                        "SECURITY_CREDENTIALS",
                                                         "PromotionPayment", 
                                                        Double.valueOf("50.00"), 
                                                        "600990", 
                                                        "254708374149", "sanitizing b2c model", 
                                                        "https://64.227.127.143:8080/plugin/qualpay/timeout", 
                                                        "https://64.227.127.143:8080/plugin/qualpay/results", 
                                                        "here is your cut"); 
        Business2CustomerResponse b2cResponse = new Business2CustomerResponse();
        try {
            mpesaGateway = new MpesaClientWrapper(APIResourceConstants.SANDBOX_URL,
                    APIResourceConstants.SecurityConstants.APIKEY, APIResourceConstants.SecurityConstants.APISECRET,
                    null, null, false);
                    response = mpesaGateway.mpesaOnlineRequest(onlineRequest); //mpesaB2CRequest(b2cRequest); //mpesaOnlineRequest(onlineRequest); //triggerSimulateTransaction(tStatusRequest);
                    logger.warn(String.format("api token from saf: %s", mpesaGateway.token));
        } catch (GeneralSecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.warn("response from mpesa online request {}", response);
       
    }

    public String getToken() {
        String url = String.format("%s%s", this.url, APIResourceConstants.SECURE_TOKEN_URL);
        String appKeySecret = this.apiKey + ":" + this.apiSecret;
        byte[] keySecretBytes = appKeySecret.getBytes(StandardCharsets.ISO_8859_1);
        String auth = Base64.encodeBase64String(keySecretBytes);

        String tokenJson = " { access_token: '' , expires_in : '' }";

        try {
            tokenJson = this.httpClient.prepareGet(url)
                    .addHeader("Authorization", "Basic " + auth)
                    .setRequestTimeout(6000).execute().get()
                    .getResponseBody();
            logger.info("got a token from safcom: %s", tokenJson);
        } catch (IOException | InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            logger.error("unable to get token from safaricom error: ", e);
        }

        JSONObject jsonObject = new JSONObject(tokenJson);
        return  jsonObject.getString("access_token");

    }

    public String getToken(String credentialsUri) {
        String appKeySecret = apiKey + ":" + apiSecret;
        byte[] keySecretBytes = appKeySecret.getBytes(StandardCharsets.ISO_8859_1);
        String auth = Base64.encodeBase64String(keySecretBytes);
        final Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "Basic " + auth);
        headers.put("cache-control", "no-cache");
        String tokenResults = "";
        try {
            tokenResults = doCallAndReturnTextResponse(GET, credentialsUri, null,
                ImmutableMap.<String, String>of(), headers);
        } catch (InvalidRequest | InterruptedException | ExecutionException | IOException | TimeoutException
                | URISyntaxException e) {
            // TODO Auto-generated catch block
            logger.error("unable to get token from safcom error: ", e);
        }
        return tokenResults;
    }

    public OnlineSuccessfullRes mpesaOnlineRequest(OnlinePaymentRequest mPaymentRequest) {
        try {
            return doCall(POST,
                          APIResourceConstants.INITIATE_ONLINE_PAYMENT,
                          mapper.writeValueAsString(mPaymentRequest),
                          ImmutableMap.<String,String>of(),
                          OnlineSuccessfullRes.class);
        }   catch (InterruptedException | ExecutionException | TimeoutException | IOException | URISyntaxException e) {
            
            logger.error("unable to make mpesa online request with safcom details '{}'", mPaymentRequest, e);
            return toOnlinePaymentResponse(e, null);
        } catch (InvalidRequest invalidReq){
            String body;
            try {
                body = invalidReq.getResponse() != null ? invalidReq.getResponse().getResponseBody() : null;
            } catch (final IOException ignored) {
                body = null;
            }
            logger.error("Unable to register server url with safcom ='{}', body='{}'", mPaymentRequest, body, invalidReq);
            return toOnlinePaymentResponse(invalidReq, body);
        }
        
    }
    private OnlineSuccessfullRes toOnlinePaymentResponse(final Throwable invalidReq,@Nullable String body) {
        OnlineSuccessfullRes onlineResponse = new OnlineSuccessfullRes();
        onlineResponse.setExactMessage(getErrorMessage(invalidReq, body));
         return onlineResponse;
    }

    public Business2CustomerResponse mpesaB2CRequest(Business2CustomerReq b2cRequest) {
        try {
            return doCall(POST,
                          APIResourceConstants.B2C_PAYMENT_REQUEST,
                          mapper.writeValueAsString(b2cRequest),
                          ImmutableMap.<String,String>of(),
                          Business2CustomerResponse.class);
        }   catch (InterruptedException | ExecutionException | TimeoutException | IOException | URISyntaxException e) {
            
            logger.error("unable to make mpesa online request with safcom details '{}'", b2cRequest, e);
            return toB2CResponse(e, null);
        } catch (InvalidRequest invalidReq){
            String body;
            try {
                body = invalidReq.getResponse() != null ? invalidReq.getResponse().getResponseBody() : null;
            } catch (final IOException ignored) {
                body = null;
            }
            logger.error("Unable to register server url with safcom ='{}', body='{}'", b2cRequest, body, invalidReq);
            return toB2CResponse(invalidReq, body);
        }
        
    }
    private Business2CustomerResponse toB2CResponse(final Throwable invalidReq,@Nullable String body) {
        Business2CustomerResponse b2cResponse = new Business2CustomerResponse();
        b2cResponse.setExactMessage(getErrorMessage(invalidReq, body));
        return b2cResponse;
    }

    public SimulateTransactionResponse triggerSimulateTransaction(final SimulateTransactionRequest sTransactionRequest){
        try{
            return doCall(POST,
                        APIResourceConstants.SIMULATE_C2B_TRANSACTION,
                        mapper.writeValueAsString(sTransactionRequest),
                        ImmutableMap.<String,String>of(),
                        SimulateTransactionResponse.class);
        }  catch (InterruptedException | ExecutionException | TimeoutException | IOException | URISyntaxException e) {
            
            logger.warn("unable to simulate transaction with safcom details '{}'", sTransactionRequest, e);
            return toSimulateTransactionResponse(e, null);
        } catch (InvalidRequest invalidReq){
            String body;
            try {
                body = invalidReq.getResponse() != null ? invalidReq.getResponse().getResponseBody() : null;
            } catch (final IOException ignored) {
                body = null;
            }
            logger.warn("Unable to register server url with safcom ='{}', body='{}'", sTransactionRequest, body, invalidReq);
            return toSimulateTransactionResponse(invalidReq, body);
        }
    }

    private SimulateTransactionResponse toSimulateTransactionResponse(final Throwable invalidReq,@Nullable String body) {
         SimulateTransactionResponse sTransactionResponse = new SimulateTransactionResponse();
         sTransactionResponse.setExactMessage(getErrorMessage(invalidReq, body));
         return sTransactionResponse;
    }

    public TransactionStatusResponse checktransactionStatus(final TransactionStatusRequest tStatusRequest){
        try {
            return doCall(POST, APIResourceConstants.TRANSACTIONS_STATUS, mapper.writeValueAsString(tStatusRequest),
                        ImmutableMap.<String,String>of(), TransactionStatusResponse.class);
            
        } catch (InterruptedException | ExecutionException | TimeoutException | IOException | URISyntaxException e) {
            logger.warn("unable to make Simulate Transaction Request on safcom details '{}' ", tStatusRequest, e);
            return toTransactionStatusResponse(e, null);
        }catch (InvalidRequest invalidReq){
            String body;
            try {
                body = invalidReq.getResponse() != null ? invalidReq.getResponse().getResponseBody() : null;
            } catch (final IOException ignored) {
                body = null;
            }
            logger.warn("Unable to register server url with safcom ='{}', body='{}'", tStatusRequest, body, invalidReq);
            return toTransactionStatusResponse(invalidReq, body);
        }
    }

    private TransactionStatusResponse toTransactionStatusResponse(final Throwable e,@Nullable String body){
        TransactionStatusResponse  tStatusResponse = new TransactionStatusResponse();
        tStatusResponse.setExactMessage(getErrorMessage(e, body));
        return tStatusResponse;
    }

    public RegisterURLResponse registerMPesaPaybillRequest(final RegisterURLRequest rUrlRequest) {
        try {
            return doCall(POST, APIResourceConstants.REGISTER_URL, mapper.writeValueAsString(rUrlRequest), ImmutableMap.<String,String>of(),
                   RegisterURLResponse.class);
        } catch (InterruptedException | ExecutionException | TimeoutException | IOException | URISyntaxException e) {
            
            logger.warn("unable to register server url with safcom details '{}'", rUrlRequest, e);
            return toRegisterURLResponse(e);
        } catch (InvalidRequest invalidReq){
            String body;
            try {
                body = invalidReq.getResponse() != null ? invalidReq.getResponse().getResponseBody() : null;
            } catch (final IOException ignored) {
                body = null;
            }
            logger.warn("Unable to register server url with safcom ='{}', body='{}'", rUrlRequest, body, invalidReq);
            return toRegisterURLResponse(invalidReq, body);
        }
    }


    private RegisterURLResponse toRegisterURLResponse(final Throwable e){
        return toRegisterURLResponse(e, null);
    }

    private RegisterURLResponse toRegisterURLResponse(final Throwable e, @Nullable final String body) {
        final RegisterURLResponse rUrlResponse = new RegisterURLResponse();
        rUrlResponse.setExactMessage(getErrorMessage(e, body));
        return rUrlResponse;
    }

    @Override
    protected <T> T doCall(final String verb, final String uri, final String body, 
    final Map<String, String> options, final Class<T> clazz) throws InterruptedException, ExecutionException, TimeoutException, IOException, URISyntaxException, InvalidRequest {
        final String url = String.format("%s%s", this.url, uri);

        final AsyncHttpClient.BoundRequestBuilder builder = getBuilderWithHeaderAndQuery(verb, url, options);
        if (!GET.equals(verb) && !HEAD.equals(verb)) {
            if (body != null) {
                logger.info("Mpesa request: {}", body);
                builder.setBody(body);
            }
        }

        setHeaders(body, builder);

        return executeAndWait(builder, DEFAULT_HTTP_TIMEOUT_SEC, clazz, ResponseFormat.JSON);
    }

    @Override
    protected <T> T deserializeResponse(final Response response, final Class<T> clazz, ResponseFormat format) throws IOException {
        final String responseBody = response.getResponseBody();
        logger.info("Mpesa response: {}", responseBody);
        return super.deserializeResponse(response, clazz, format);
    }

    public void setHeaders(final String body, final AsyncHttpClient.BoundRequestBuilder builder){
        builder.addHeader(APIResourceConstants.SecurityConstants.AUTHORIZE, 
                   APIResourceConstants.SecurityConstants.TOKEN + token);
        builder.addHeader("User-Agent", "KillBill 0.22");
        builder.addHeader("accept", APPLICATION_JSON);
        builder.addHeader("Content-Type", APPLICATION_JSON);   

    }

    private String getErrorMessage(final Throwable e, @Nullable final String body) {
        final Map<String, String> bodyMap = new HashMap<String, String>();
        bodyMap.put(EXCEPTION_CLASS, e.getClass().getCanonicalName());
        bodyMap.put(EXCEPTION_MESSAGE, e.getMessage());
        if (body != null) {
            bodyMap.put(MPESA_MESSAGE, body);
        }

        String messageJSON;
        try {
            messageJSON = mapper.writeValueAsString(bodyMap);
        } catch (final JsonProcessingException ignored) {
            messageJSON = null;
        }
        return messageJSON;
    }

    private byte[] toHex(final byte[] arr) {
        final String hex = byteArrayToHex(arr);
        return hex.getBytes();
    }

    private String byteArrayToHex(final byte[] a) {
        final StringBuilder sb = new StringBuilder(a.length * 2);
        for (final byte b : a) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

}
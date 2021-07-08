
package org.killbill.billing.plugin.qualpay.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "MerchantRequestID",
    "CheckoutRequestID",
    "ResultCode",
    "ResultDesc",
    "CallbackMetadata"
})
@Generated("jsonschema2pojo")
public class StkCallback implements Serializable
{

    @JsonProperty("MerchantRequestID")
    private String merchantRequestID;
    @JsonProperty("CheckoutRequestID")
    private String checkoutRequestID;
    @JsonProperty("ResultCode")
    private Integer resultCode;
    @JsonProperty("ResultDesc")
    private String resultDesc;
    @JsonProperty("CallbackMetadata")
    private CallbackMetadata callbackMetadata;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 6631747304410556631L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public StkCallback() {
    }

    /**
     * 
     * @param callbackMetadata
     * @param resultCode
     * @param checkoutRequestID
     * @param resultDesc
     * @param merchantRequestID
     */
    public StkCallback(String merchantRequestID, String checkoutRequestID, Integer resultCode, String resultDesc, CallbackMetadata callbackMetadata) {
        super();
        this.merchantRequestID = merchantRequestID;
        this.checkoutRequestID = checkoutRequestID;
        this.resultCode = resultCode;
        this.resultDesc = resultDesc;
        this.callbackMetadata = callbackMetadata;
    }

    @JsonProperty("MerchantRequestID")
    public String getMerchantRequestID() {
        return merchantRequestID;
    }

    @JsonProperty("MerchantRequestID")
    public void setMerchantRequestID(String merchantRequestID) {
        this.merchantRequestID = merchantRequestID;
    }

    @JsonProperty("CheckoutRequestID")
    public String getCheckoutRequestID() {
        return checkoutRequestID;
    }

    @JsonProperty("CheckoutRequestID")
    public void setCheckoutRequestID(String checkoutRequestID) {
        this.checkoutRequestID = checkoutRequestID;
    }

    @JsonProperty("ResultCode")
    public Integer getResultCode() {
        return resultCode;
    }

    @JsonProperty("ResultCode")
    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    @JsonProperty("ResultDesc")
    public String getResultDesc() {
        return resultDesc;
    }

    @JsonProperty("ResultDesc")
    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    @JsonProperty("CallbackMetadata")
    public CallbackMetadata getCallbackMetadata() {
        return callbackMetadata;
    }

    @JsonProperty("CallbackMetadata")
    public void setCallbackMetadata(CallbackMetadata callbackMetadata) {
        this.callbackMetadata = callbackMetadata;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(StkCallback.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("merchantRequestID");
        sb.append('=');
        sb.append(((this.merchantRequestID == null)?"<null>":this.merchantRequestID));
        sb.append(',');
        sb.append("checkoutRequestID");
        sb.append('=');
        sb.append(((this.checkoutRequestID == null)?"<null>":this.checkoutRequestID));
        sb.append(',');
        sb.append("resultCode");
        sb.append('=');
        sb.append(((this.resultCode == null)?"<null>":this.resultCode));
        sb.append(',');
        sb.append("resultDesc");
        sb.append('=');
        sb.append(((this.resultDesc == null)?"<null>":this.resultDesc));
        sb.append(',');
        sb.append("callbackMetadata");
        sb.append('=');
        sb.append(((this.callbackMetadata == null)?"<null>":this.callbackMetadata));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}


package org.killbill.billing.plugin.qualpay.models.B2C;

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
    "ResultType",
    "ResultCode",
    "ResultDesc",
    "OriginatorConversationID",
    "ConversationID",
    "TransactionID",
    "ResultParameters",
    "ReferenceData"
})
@Generated("jsonschema2pojo")
public class Result implements Serializable
{

    @JsonProperty("ResultType")
    private Integer resultType;
    @JsonProperty("ResultCode")
    private Integer resultCode;
    @JsonProperty("ResultDesc")
    private String resultDesc;
    @JsonProperty("OriginatorConversationID")
    private String originatorConversationID;
    @JsonProperty("ConversationID")
    private String conversationID;
    @JsonProperty("TransactionID")
    private String transactionID;
    @JsonProperty("ResultParameters")
    private ResultParameters resultParameters;
    @JsonProperty("ReferenceData")
    private ReferenceData referenceData;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -6170714233756779797L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Result() {
    }

    /**
     * 
     * @param referenceData
     * @param resultParameters
     * @param conversationID
     * @param resultCode
     * @param originatorConversationID
     * @param resultDesc
     * @param resultType
     * @param transactionID
     */
    public Result(Integer resultType, Integer resultCode, String resultDesc, String originatorConversationID, String conversationID, String transactionID, ResultParameters resultParameters, ReferenceData referenceData) {
        super();
        this.resultType = resultType;
        this.resultCode = resultCode;
        this.resultDesc = resultDesc;
        this.originatorConversationID = originatorConversationID;
        this.conversationID = conversationID;
        this.transactionID = transactionID;
        this.resultParameters = resultParameters;
        this.referenceData = referenceData;
    }

    @JsonProperty("ResultType")
    public Integer getResultType() {
        return resultType;
    }

    @JsonProperty("ResultType")
    public void setResultType(Integer resultType) {
        this.resultType = resultType;
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

    @JsonProperty("OriginatorConversationID")
    public String getOriginatorConversationID() {
        return originatorConversationID;
    }

    @JsonProperty("OriginatorConversationID")
    public void setOriginatorConversationID(String originatorConversationID) {
        this.originatorConversationID = originatorConversationID;
    }

    @JsonProperty("ConversationID")
    public String getConversationID() {
        return conversationID;
    }

    @JsonProperty("ConversationID")
    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    @JsonProperty("TransactionID")
    public String getTransactionID() {
        return transactionID;
    }

    @JsonProperty("TransactionID")
    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    @JsonProperty("ResultParameters")
    public ResultParameters getResultParameters() {
        return resultParameters;
    }

    @JsonProperty("ResultParameters")
    public void setResultParameters(ResultParameters resultParameters) {
        this.resultParameters = resultParameters;
    }

    @JsonProperty("ReferenceData")
    public ReferenceData getReferenceData() {
        return referenceData;
    }

    @JsonProperty("ReferenceData")
    public void setReferenceData(ReferenceData referenceData) {
        this.referenceData = referenceData;
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
        sb.append(Result.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("resultType");
        sb.append('=');
        sb.append(((this.resultType == null)?"<null>":this.resultType));
        sb.append(',');
        sb.append("resultCode");
        sb.append('=');
        sb.append(((this.resultCode == null)?"<null>":this.resultCode));
        sb.append(',');
        sb.append("resultDesc");
        sb.append('=');
        sb.append(((this.resultDesc == null)?"<null>":this.resultDesc));
        sb.append(',');
        sb.append("originatorConversationID");
        sb.append('=');
        sb.append(((this.originatorConversationID == null)?"<null>":this.originatorConversationID));
        sb.append(',');
        sb.append("conversationID");
        sb.append('=');
        sb.append(((this.conversationID == null)?"<null>":this.conversationID));
        sb.append(',');
        sb.append("transactionID");
        sb.append('=');
        sb.append(((this.transactionID == null)?"<null>":this.transactionID));
        sb.append(',');
        sb.append("resultParameters");
        sb.append('=');
        sb.append(((this.resultParameters == null)?"<null>":this.resultParameters));
        sb.append(',');
        sb.append("referenceData");
        sb.append('=');
        sb.append(((this.referenceData == null)?"<null>":this.referenceData));
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

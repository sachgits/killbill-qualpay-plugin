
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
    "InitiatorName",
    "SecurityCredential",
    "CommandID",
    "Amount",
    "PartyA",
    "PartyB",
    "Remarks",
    "QueueTimeOutURL",
    "ResultURL",
    "Occassion"
})
@Generated("jsonschema2pojo")
public class Business2CustomerReq implements Serializable
{

    @JsonProperty("InitiatorName")
    private String initiatorName;
    @JsonProperty("SecurityCredential")
    private String securityCredential;
    @JsonProperty("CommandID")
    private String commandID;
    @JsonProperty("Amount")
    private Double amount;
    @JsonProperty("PartyA")
    private String partyA;
    @JsonProperty("PartyB")
    private String partyB;
    @JsonProperty("Remarks")
    private String remarks;
    @JsonProperty("QueueTimeOutURL")
    private String queueTimeOutURL;
    @JsonProperty("ResultURL")
    private String resultURL;
    @JsonProperty("Occassion")
    private String occassion;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 5464902211931733235L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Business2CustomerReq() {
    }

    /**
     * 
     * @param partyA
     * @param amount
     * @param queueTimeOutURL
     * @param partyB
     * @param initiatorName
     * @param resultURL
     * @param securityCredential
     * @param commandID
     * @param remarks
     * @param occassion
     */
    public Business2CustomerReq(String initiatorName, String securityCredential, String commandID, Double amount, String partyA, String partyB, String remarks, String queueTimeOutURL, String resultURL, String occassion) {
        super();
        this.initiatorName = initiatorName;
        this.securityCredential = securityCredential;
        this.commandID = commandID;
        this.amount = amount;
        this.partyA = partyA;
        this.partyB = partyB;
        this.remarks = remarks;
        this.queueTimeOutURL = queueTimeOutURL;
        this.resultURL = resultURL;
        this.occassion = occassion;
    }

    @JsonProperty("InitiatorName")
    public String getInitiatorName() {
        return initiatorName;
    }

    @JsonProperty("InitiatorName")
    public void setInitiatorName(String initiatorName) {
        this.initiatorName = initiatorName;
    }

    @JsonProperty("SecurityCredential")
    public String getSecurityCredential() {
        return securityCredential;
    }

    @JsonProperty("SecurityCredential")
    public void setSecurityCredential(String securityCredential) {
        this.securityCredential = securityCredential;
    }

    @JsonProperty("CommandID")
    public String getCommandID() {
        return commandID;
    }

    @JsonProperty("CommandID")
    public void setCommandID(String commandID) {
        this.commandID = commandID;
    }

    @JsonProperty("Amount")
    public Double getAmount() {
        return amount;
    }

    @JsonProperty("Amount")
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @JsonProperty("PartyA")
    public String getPartyA() {
        return partyA;
    }

    @JsonProperty("PartyA")
    public void setPartyA(String partyA) {
        this.partyA = partyA;
    }

    @JsonProperty("PartyB")
    public String getPartyB() {
        return partyB;
    }

    @JsonProperty("PartyB")
    public void setPartyB(String partyB) {
        this.partyB = partyB;
    }

    @JsonProperty("Remarks")
    public String getRemarks() {
        return remarks;
    }

    @JsonProperty("Remarks")
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @JsonProperty("QueueTimeOutURL")
    public String getQueueTimeOutURL() {
        return queueTimeOutURL;
    }

    @JsonProperty("QueueTimeOutURL")
    public void setQueueTimeOutURL(String queueTimeOutURL) {
        this.queueTimeOutURL = queueTimeOutURL;
    }

    @JsonProperty("ResultURL")
    public String getResultURL() {
        return resultURL;
    }

    @JsonProperty("ResultURL")
    public void setResultURL(String resultURL) {
        this.resultURL = resultURL;
    }

    @JsonProperty("Occassion")
    public String getOccassion() {
        return occassion;
    }

    @JsonProperty("Occassion")
    public void setOccassion(String occassion) {
        this.occassion = occassion;
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
        sb.append(Business2CustomerReq.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("initiatorName");
        sb.append('=');
        sb.append(((this.initiatorName == null)?"<null>":this.initiatorName));
        sb.append(',');
        sb.append("securityCredential");
        sb.append('=');
        sb.append(((this.securityCredential == null)?"<null>":this.securityCredential));
        sb.append(',');
        sb.append("commandID");
        sb.append('=');
        sb.append(((this.commandID == null)?"<null>":this.commandID));
        sb.append(',');
        sb.append("amount");
        sb.append('=');
        sb.append(((this.amount == null)?"<null>":this.amount));
        sb.append(',');
        sb.append("partyA");
        sb.append('=');
        sb.append(((this.partyA == null)?"<null>":this.partyA));
        sb.append(',');
        sb.append("partyB");
        sb.append('=');
        sb.append(((this.partyB == null)?"<null>":this.partyB));
        sb.append(',');
        sb.append("remarks");
        sb.append('=');
        sb.append(((this.remarks == null)?"<null>":this.remarks));
        sb.append(',');
        sb.append("queueTimeOutURL");
        sb.append('=');
        sb.append(((this.queueTimeOutURL == null)?"<null>":this.queueTimeOutURL));
        sb.append(',');
        sb.append("resultURL");
        sb.append('=');
        sb.append(((this.resultURL == null)?"<null>":this.resultURL));
        sb.append(',');
        sb.append("occassion");
        sb.append('=');
        sb.append(((this.occassion == null)?"<null>":this.occassion));
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

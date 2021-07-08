package org.killbill.billing.plugin.qualpay.models;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonAutoDetect(getterVisibility= Visibility.DEFAULT,setterVisibility= Visibility.DEFAULT,fieldVisibility= Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class CommonRespose {
    @JsonProperty("ResultType")
    public int ResultType;
    @JsonProperty("ResultCode")
    public int ResultCode;

    @JsonProperty("ResultDesc")
    public String ResultDesc;

    @JsonProperty("OriginatorConversationID")
    public String OriginatorConversationID;

    @JsonProperty("ConversationID")
    public String ConversationID;

    @JsonProperty("TransactionID")
    public String TransactionID;

    @JsonProperty("ResultParameters")
    public ResultParameters resultParameters;
}

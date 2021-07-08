package org.killbill.billing.plugin.qualpay.models;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonAutoDetect(getterVisibility= Visibility.DEFAULT,setterVisibility= Visibility.DEFAULT,fieldVisibility= Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class C2BPaymentConfirmationRequest {
    @JsonProperty("TransactionType")
    public String TransactionType;

    @JsonProperty("TransID")
    public String TransID;

    @JsonProperty("TransTime")
    public Integer TransTime;

    @JsonProperty("TransAmount")
    public Float TransAmount;

    @JsonProperty("BusinessShortCode")
    public int BusinessShortCode;

    @JsonProperty("BillRefNumber")
    public String BillRefNumber;

    @JsonProperty("InvoiceNumber")
    public String InvoiceNumber;

    @JsonProperty("OrgAccountBalance")
    public Float OrgAccountBalance;

    @JsonProperty("ThirdPartyTransID")
    public  String ThirdPartyTransID;

    @JsonProperty("MSISDN")
    public String MSISDN;

    @JsonProperty("FirstName")
    public String FirstName;

    @JsonProperty("MiddleName")
    public String MiddleName;

    @JsonProperty("LastName")
    public String LastName;
}

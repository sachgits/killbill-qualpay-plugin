/*
 * Copyright 2014-2020 Groupon, Inc
 * Copyright 2014-2020 The Billing Project, LLC
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

 /**
  *  // Validation Response
  {
    "TransactionType":"",
    "TransID":"LGR219G3EY",
    "TransTime":"20170727104247",
    "TransAmount":"10.00",
    "BusinessShortCode":"600134",
    "BillRefNumber":"xyz",
    "InvoiceNumber":"",
    "OrgAccountBalance":"",
    "ThirdPartyTransID":"",
    "MSISDN":"254708374149",
    "FirstName":"John",
    "MiddleName":"Doe",
    "LastName":""
  }
  */

package org.killbill.billing.plugin.qualpay.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"TransactionType",
"TransID",
"TransTime",
"TransAmount",
"BusinessShortCode",
"BillRefNumber",
"InvoiceNumber",
"OrgAccountBalance",
"ThirdPartyTransID",
"MSISDN",
"FirstName",
"MiddleName",
"LastName"
})
public class ConfirmationRequest implements Serializable
{

@JsonProperty("TransactionType")
private String transactionType;
@JsonProperty("TransID")
private String transID;
@JsonProperty("TransTime")
private String transTime;
@JsonProperty("TransAmount")
private Double transAmount;
@JsonProperty("BusinessShortCode")
private String businessShortCode;
@JsonProperty("BillRefNumber")
private String billRefNumber;
@JsonProperty("InvoiceNumber")
private String invoiceNumber;
@JsonProperty("OrgAccountBalance")
private Double orgAccountBalance;
@JsonProperty("ThirdPartyTransID")
private String thirdPartyTransID;
@JsonProperty("MSISDN")
private String mSISDN;
@JsonProperty("FirstName")
private String firstName;
@JsonProperty("MiddleName")
private String middleName;
@JsonProperty("LastName")
private String lastName;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();
private final static long serialVersionUID = 7772920306604279732L;

/**
* No args constructor for use in serialization
*
*/
public ConfirmationRequest() {
}

/**
*
* @param lastName
* @param transTime
* @param transID
* @param businessShortCode
* @param billRefNumber
* @param orgAccountBalance
* @param mSISDN
* @param transactionType
* @param transAmount
* @param firstName
* @param invoiceNumber
* @param middleName
* @param thirdPartyTransID
*/
public ConfirmationRequest(String transactionType, String transID, String transTime, Double transAmount, String businessShortCode, 
String billRefNumber, String invoiceNumber, Double orgAccountBalance, String thirdPartyTransID, 
String mSISDN, String firstName, String middleName, String lastName) {
super();
this.transactionType = transactionType;
this.transID = transID;
this.transTime = transTime;
this.transAmount = transAmount;
this.businessShortCode = businessShortCode;
this.billRefNumber = billRefNumber;
this.invoiceNumber = invoiceNumber;
this.orgAccountBalance = orgAccountBalance;
this.thirdPartyTransID = thirdPartyTransID;
this.mSISDN = mSISDN;
this.firstName = firstName;
this.middleName = middleName;
this.lastName = lastName;
}

@JsonProperty("TransactionType")
public String getTransactionType() {
return transactionType;
}

@JsonProperty("TransactionType")
public void setTransactionType(String transactionType) {
this.transactionType = transactionType;
}

@JsonProperty("TransID")
public String getTransID() {
return transID;
}

@JsonProperty("TransID")
public void setTransID(String transID) {
this.transID = transID;
}

@JsonProperty("TransTime")
public String getTransTime() {
return transTime;
}

@JsonProperty("TransTime")
public void setTransTime(String transTime) {
this.transTime = transTime;
}

@JsonProperty("TransAmount")
public Double getTransAmount() {
return transAmount;
}

@JsonProperty("TransAmount")
public void setTransAmount(Double transAmount) {
this.transAmount = transAmount;
}

@JsonProperty("BusinessShortCode")
public String getBusinessShortCode() {
return businessShortCode;
}

@JsonProperty("BusinessShortCode")
public void setBusinessShortCode(String businessShortCode) {
this.businessShortCode = businessShortCode;
}

@JsonProperty("BillRefNumber")
public String getBillRefNumber() {
return billRefNumber;
}

@JsonProperty("BillRefNumber")
public void setBillRefNumber(String billRefNumber) {
this.billRefNumber = billRefNumber;
}

@JsonProperty("InvoiceNumber")
public String getInvoiceNumber() {
return invoiceNumber;
}

@JsonProperty("InvoiceNumber")
public void setInvoiceNumber(String invoiceNumber) {
this.invoiceNumber = invoiceNumber;
}

@JsonProperty("OrgAccountBalance")
public Double getOrgAccountBalance() {
return orgAccountBalance;
}

@JsonProperty("OrgAccountBalance")
public void setOrgAccountBalance(Double orgAccountBalance) {
this.orgAccountBalance = orgAccountBalance;
}

@JsonProperty("ThirdPartyTransID")
public String getThirdPartyTransID() {
return thirdPartyTransID;
}

@JsonProperty("ThirdPartyTransID")
public void setThirdPartyTransID(String thirdPartyTransID) {
this.thirdPartyTransID = thirdPartyTransID;
}

@JsonProperty("MSISDN")
public String getMSISDN() {
return mSISDN;
}

@JsonProperty("MSISDN")
public void setMSISDN(String mSISDN) {
this.mSISDN = mSISDN;
}

@JsonProperty("FirstName")
public String getFirstName() {
return firstName;
}

@JsonProperty("FirstName")
public void setFirstName(String firstName) {
this.firstName = firstName;
}

@JsonProperty("MiddleName")
public String getMiddleName() {
return middleName;
}

@JsonProperty("MiddleName")
public void setMiddleName(String middleName) {
this.middleName = middleName;
}

@JsonProperty("LastName")
public String getLastName() {
return lastName;
}

@JsonProperty("LastName")
public void setLastName(String lastName) {
this.lastName = lastName;
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
return new ToStringBuilder(this).append("transactionType", transactionType)
.append("transID", transID).append("transTime", transTime)
.append("transAmount", transAmount).append("businessShortCode", businessShortCode)
.append("billRefNumber", billRefNumber).append("invoiceNumber", invoiceNumber)
.append("orgAccountBalance", orgAccountBalance).append("thirdPartyTransID", thirdPartyTransID)
.append("mSISDN", mSISDN).append("firstName", firstName).append("middleName", middleName)
.append("lastName", lastName).append("additionalProperties", additionalProperties)
.toString();
}

}

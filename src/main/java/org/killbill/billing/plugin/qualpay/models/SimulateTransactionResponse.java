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

package org.killbill.billing.plugin.qualpay.models;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonAutoDetect(getterVisibility= Visibility.DEFAULT,setterVisibility= Visibility.DEFAULT,fieldVisibility= Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class SimulateTransactionResponse {

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

    public String exactMessage;

	public void setExactMessage(String errorMessage) {
        exactMessage = errorMessage;
	}
}

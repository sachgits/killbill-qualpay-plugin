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

import java.util.Date;
@JsonAutoDetect(getterVisibility= Visibility.DEFAULT,setterVisibility= Visibility.DEFAULT,fieldVisibility= Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class OnlinePaymentRequest  extends MpesaCoreBase{

    public OnlinePaymentRequest(String transactionType, Double amount, String partyA,
    String accRef,String transDesc) {
        super(APIResourceConstants.SANDBOX_SHORTCODE);
        this.TransactionType = transactionType;
        this.Amount = amount;
        this.PartyA = partyA;
        this.PhoneNumber = partyA;
        this.PartyB = APIResourceConstants.SANDBOX_SHORTCODE;
        this.CallBackURL = "https://64.227.127.143:8080/plugin/callmeback";
        this.AccountReference = accRef; //kbTransactionid
        this.TransactionDesc = transDesc;
    }

    @JsonProperty("TransactionType")
    public String TransactionType = "CustomerPayBillOnline";

    @JsonProperty("Amount")
    public Double Amount;

    @JsonProperty("PartyA")
    public String PartyA;

    @JsonProperty("PartyB")
    public Integer PartyB;

    @JsonProperty("PhoneNumber")
    public String PhoneNumber;

    @JsonProperty("CallBackURL")
    public String CallBackURL;

    @JsonProperty("AccountReference")
    public String AccountReference;

    @JsonProperty("TransactionDesc")
    public String TransactionDesc;
}
/**
 * {    
   "BusinessShortCode":"174379",    
   "Password": "MTc0Mzc5YmZiMjc5ZjlhYTliZGJjZjE1OGU5N2RkNzFhNDY3Y2QyZTBjODkzMDU5YjEwZjc4ZTZiNzJhZGExZWQyYzkxOTIwMTYwMjE2MTY1NjI3",    
 "Timestamp":"20160216165627",    
 "TransactionType": "CustomerPayBillOnline",    
   "Amount":"1",    
  "PartyA":"254723372301",    
   "PartyB":"174379",    
 "PhoneNumber":"254723372301",    
 "CallBackURL":"https://darajambili.herokuapp.com/express-payment",    
 "AccountReference":"Test",    
 "TransactionDesc":"Test"
}
 */
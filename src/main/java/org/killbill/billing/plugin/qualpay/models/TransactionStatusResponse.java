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
@JsonIgnoreProperties(ignoreUnknown = false)
@JsonInclude(Include.NON_EMPTY)
@JsonRootName("Result")
public class TransactionStatusResponse extends CommonRespose {

    @JsonProperty("ReferenceData")
    public ReferenceData ReferenceData;

    public String ExactMessage;

    public void setExactMessage(String exactMsg){
        ExactMessage = exactMsg;
    }

}
/*
"ResultParameters":{
            "ResultParameter":[
            {
                "Key":"ReceiptNo",
                    "Value":"LGR919G2AV"
            },
            {
                "Key":"Conversation ID",
                    "Value":"AG_20170727_00004492b1b6d0078fbe"
            },
            {
                "Key":"FinalisedTime",
                    "Value":20170727101415
            },
            {
                "Key":"Amount",
                    "Value":10
            },
            {
                "Key":"TransactionStatus",
                    "Value":"Completed"
            },
            {
                "Key":"ReasonType",
                    "Value":"Salary Payment via API"
            },
            {
                "Key":"TransactionReason"
            },
            {
                "Key":"DebitPartyCharges",
                    "Value":"Fee For B2C Payment|KES|33.00"
            },
            {
                "Key":"DebitAccountType",
                    "Value":"Utility Account"
            },
            {
                "Key":"InitiatedTime",
                    "Value":20170727101415
            },
            {
                "Key":"Originator Conversation ID",
                    "Value":"19455-773836-1"
            },
            {
                "Key":"CreditPartyName",
                    "Value":"254708374149 - John Doe"
            },
            {
                "Key":"DebitPartyName",
                    "Value":"600134 - Safaricom157"
            }
        ]
        },
        "ReferenceData":{
            "ReferenceItem":{
                "Key":"Occasion",
                        "Value":"aaaa"
            }
        }
    }

 */

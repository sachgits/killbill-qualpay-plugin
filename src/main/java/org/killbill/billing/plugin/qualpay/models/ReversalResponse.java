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
@JsonRootName("Body")
public class ReversalResponse extends CommonRespose{

}
/**
    "Result":{
        "ResultType":0,
                "ResultCode":0,
                "ResultDesc":"The service request has been accepted successfully.",
                "OriginatorConversationID":"10819-695089-1",
                "ConversationID":"AG_20170727_00004efadacd98a01d15",
                "TransactionID":"LGR019G3J2",
                "ReferenceData":{
            "ReferenceItem":{
                "Key":"QueueTimeoutURL",
                        "Value":"https://internalsandbox.safaricom.co.ke/mpesa/reversalresults/v1/submit"
            }
        }
} */

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
public class OnlineCancelledRequest {

    @JsonProperty("stkCallback")
    public stkCallback StkCallback;

    @JsonInclude(Include.NON_NULL)
    @JsonRootName("stkCallback")
    public class stkCallback extends CommonRespose {

        @JsonProperty("MerchantRequestID")
        public String MerchantRequestID;
        @JsonProperty("CheckoutRequestID")
        public String CheckoutRequestID;
    }
}
/**
"Body":{
        "stkCallback":{
            "MerchantRequestID":"8555-67195-1",
                    "CheckoutRequestID":"ws_CO_27072017151044001",
                    "ResultCode":1032,
                    "ResultDesc":"[STK_CB - ]Request cancelled by user"
        }
    }

}
{    
   "Body": {
      "stkCallback": {
         "MerchantRequestID": "29115-34620561-1",
         "CheckoutRequestID": "ws_CO_191220191020363925",
         "ResultCode": 1032,
         "ResultDesc": "Request cancelled by user."
      }
   }
}
*/

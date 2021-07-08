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
public class OnlineAcceptedRequest {

    public class nameValue {

        @JsonProperty("Name")
        public String Name;

        @JsonProperty("Value")
        public String Value;
    }

    ;

    @JsonRootName("CallbackMetadata")
    public class CallbackMetadata {

        @JsonProperty("Item")
        public nameValue[] item;
    }

    ;

    @JsonRootName("stkCallback")
    public class stkCallback {

        @JsonProperty("MerchantRequestID")
        public String merchantRequestID;
        @JsonProperty("CheckoutRequestID")
        public String checkoutRequestID;
        @JsonProperty("ResultCode")
        public short resultCode;
        @JsonProperty("ResultDesc")
        public String resultDesc;
        @JsonProperty("CallbackMetadata")
        public CallbackMetadata callbackMetadata;

    }
}
   /** "Body":{
        "stkCallback":{
            "MerchantRequestID":"19465-780693-1",
             "CheckoutRequestID":"ws_CO_27072017154747416",
            "ResultCode":0,
            "ResultDesc":"The service request is processed successfully.",
             "CallbackMetadata":{
                "Item":[
                {
                    "Name":"Amount",
                        "Value":1
                },
                {
                    "Name":"MpesaReceiptNumber",
                        "Value":"LGR7OWQX0R"
                },
                {
                    "Name":"Balance"
                },
                {
                    "Name":"TransactionDate",
                        "Value":20170727154800
                },
                {
                    "Name":"PhoneNumber",
                        "Value":254721566839
                }
          ]
            }
        }
    }
}
{    
   "Body": {        
      "stkCallback": {            
         "MerchantRequestID": "29115-34620561-1",            
         "CheckoutRequestID": "ws_CO_191220191020363925",            
         "ResultCode": 0,            
         "ResultDesc": "The service request is processed successfully.",            
         "CallbackMetadata": {                
            "Item": [{                        
               "Name": "Amount",                        
               "Value": 1.00                    
            },                    
            {                        
               "Name": "MpesaReceiptNumber",                        
               "Value": "NLJ7RT61SV"                    
            },                    
            {                        
               "Name": "TransactionDate",                        
               "Value": 20191219102115                    
            },                    
            {                        
               "Name": "PhoneNumber",                        
               "Value": 254723372301                    
            }]            
         }        
      }    
   }
}
{    
   "MerchantRequestID": "29115-34620561-1",    
   "CheckoutRequestID": "ws_CO_191220191020363925",    
   "ResponseCode": "0",    
   "ResponseDescription": "Success. Request accepted for processing",    
   "CustomerMessage": "Success. Request accepted for processing"
}
*/
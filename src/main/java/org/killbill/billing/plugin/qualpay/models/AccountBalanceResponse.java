package org.killbill.billing.plugin.qualpay.models;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.Date;
@JsonAutoDetect(getterVisibility= Visibility.DEFAULT,setterVisibility= Visibility.DEFAULT,fieldVisibility= Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
@JsonRootName("Result")
public class AccountBalanceResponse  extends CommonRespose{

}
   /** "Result":{
        "ResultType":0,
                "ResultCode":0,
                "ResultDesc":"The service request has b een accepted successfully.",
                "OriginatorConversationID":"19464-802673-1",
                "ConversationID":"AG_20170728_0000589b6252f7f25488",
                "TransactionID":"LGS0000000",
                "ResultParameters":{
            "ResultParameter":[
            {
                "Key":"AccountBalance",
                    "Value":"Working Account|KES|46713.00|46713.00|0.00|0.00&Float Account|KES|0.00|0.00|0.00|0.00&Utility Account|KES|49217.00|49217.00|0.00|0.00&Charges Paid Account|KES|-220.00|-220.00|0.00|0.00&Organization Settlement Account|KES|0.00|0.00|0.00|0.00"
            },
            {
                "Key":"BOCompletedTime",
                    "Value":20170728095642
            }
}
*/
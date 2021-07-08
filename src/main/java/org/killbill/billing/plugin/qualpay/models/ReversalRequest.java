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
public class ReversalRequest {

    @JsonProperty("Initiator")
    public String Initiator;

    @JsonProperty("SecurityCredential")
    public String SecurityCredential;

    @JsonProperty("CommandID")
    public String CommandID = "TransactionReversal";

    @JsonProperty("TransacionID")
    public String TransactionID;

    @JsonProperty("Amount")
    public Float Amount;

    @JsonProperty("ReceiverParty")
    public int ReceiverParty;

    @JsonProperty("RecieverIdentifierType")
    public int RecieverIdentifierType = 4;

    @JsonProperty("ResultURL")
    public int ResultURL;

    @JsonProperty("QueueTimeOutURL")
    public String QueueTimeOutURL;

    @JsonProperty("Remarks")
    public String Remarks;

    @JsonProperty("Occasion")
    public String Occasion;
}

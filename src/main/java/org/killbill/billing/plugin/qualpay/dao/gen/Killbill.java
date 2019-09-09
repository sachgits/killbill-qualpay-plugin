/*
 * Copyright 2014-2019 Groupon, Inc
 * Copyright 2014-2019 The Billing Project, LLC
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

/*
 * This file is generated by jOOQ.
*/
package org.killbill.billing.plugin.qualpay.dao.gen;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;
import org.killbill.billing.plugin.qualpay.dao.gen.tables.QualpayPaymentMethods;
import org.killbill.billing.plugin.qualpay.dao.gen.tables.QualpayResponses;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Killbill extends SchemaImpl {

    private static final long serialVersionUID = -127110581;

    /**
     * The reference instance of <code>killbill</code>
     */
    public static final Killbill KILLBILL = new Killbill();

    /**
     * The table <code>killbill.qualpay_payment_methods</code>.
     */
    public final QualpayPaymentMethods QUALPAY_PAYMENT_METHODS = org.killbill.billing.plugin.qualpay.dao.gen.tables.QualpayPaymentMethods.QUALPAY_PAYMENT_METHODS;

    /**
     * The table <code>killbill.qualpay_responses</code>.
     */
    public final QualpayResponses QUALPAY_RESPONSES = org.killbill.billing.plugin.qualpay.dao.gen.tables.QualpayResponses.QUALPAY_RESPONSES;

    /**
     * No further instances allowed
     */
    private Killbill() {
        super("killbill", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            QualpayPaymentMethods.QUALPAY_PAYMENT_METHODS,
            QualpayResponses.QUALPAY_RESPONSES);
    }
}

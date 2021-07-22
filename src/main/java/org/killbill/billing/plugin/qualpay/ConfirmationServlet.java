package org.killbill.billing.plugin.qualpay;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.inject.Inject;
import javax.servlet.ServletRequest;

import org.jooby.Jooby;
import org.jooby.Result;
import org.jooby.Results;
import org.jooby.mvc.GET;
import org.jooby.mvc.POST;
import org.jooby.mvc.Body;
import org.jooby.mvc.Local;
import org.jooby.mvc.Path;
import org.jooby.MediaType;
import org.jooby.Status;
import org.killbill.billing.tenant.api.Tenant;
import org.killbill.billing.account.api.Account;
import org.killbill.billing.invoice.api.InvoiceItem;
import org.killbill.billing.osgi.libs.killbill.OSGIKillbillAPI;
import org.killbill.billing.plugin.core.PluginServlet;
import org.killbill.billing.plugin.qualpay.models.ConfirmationRequest;

import com.fasterxml.jackson.core.JsonProcessingException;

@Singleton
@Path("/Confirmation")
public class ConfirmationServlet extends Jooby{

    private final Confirmation confirmationReq;

    @Inject
    public ConfirmationServlet(OSGIKillbillAPI osgiKillbillAPI) {
        this.confirmationReq = new Confirmation(osgiKillbillAPI);
    }

    @POST
    public Result confirm(@Body ConfirmationRequest confirmationJson) {
        //TODO: deal with nulls please
        List<InvoiceItem> itemInvoice = this.confirmationReq.insertCreditToUserAccount(confirmationJson);
        return Results.json(itemInvoice.toString());
    }
}
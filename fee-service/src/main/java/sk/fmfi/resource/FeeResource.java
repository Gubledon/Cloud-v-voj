package sk.fmfi.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sk.fmfi.model.Fee;
import sk.fmfi.resource.dto.TransactionDTO;
import sk.fmfi.service.FeeService;

import java.util.List;

@Path("/fee")
public class FeeResource {

    private final FeeService service;

    @Inject
    public FeeResource(FeeService service) {
        this.service = service;
    }

    @GET
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFees(@QueryParam("iban") String iban) {
        List<Fee> fees;

        if (iban == null || iban.isBlank()) {
            fees = service.getAllFees();
        } else {
            fees = service.getFeesForIban(iban);
        }

        return Response.ok(fees).build();
    }

    @POST
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFee(TransactionDTO transactionDTO) {
        Fee fee = service.createFee(
                transactionDTO.getTransactionId(),
                transactionDTO.getIban(),
                transactionDTO.getAmount()
        );

        return Response.ok(fee).build();
    }
}
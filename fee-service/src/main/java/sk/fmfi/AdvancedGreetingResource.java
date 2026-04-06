package sk.fmfi;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/greeting")
public class AdvancedGreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String greeting(@QueryParam("subject") String subject) {
        if (subject == null || subject.isBlank()) {
            subject = "World";
        }
        return "Hello, " + subject;
    }
}
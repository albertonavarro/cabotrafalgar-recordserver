package com.navid.trafalgar.recordserverendpoints;

import com.navid.trafalgar.recordserverservices.UserServices;
import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/rest")
public class RestValidatorImpl {
    
    @Resource
    private UserServices userServices;

    @GET
    @Path("/validate/{input}")
    @Produces("text/plain")
    public String ping(@PathParam("input") String input) {
        
        //userServices.validateKey(input);
        
        return input + "verified";
    }
}
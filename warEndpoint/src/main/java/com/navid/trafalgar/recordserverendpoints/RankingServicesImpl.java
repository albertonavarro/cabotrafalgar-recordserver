package com.navid.trafalgar.recordserverendpoints;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.apache.cxf.jaxrs.model.wadl.Description;

@Description("Desc")
@Consumes("application/json")
@Produces("application/json")
public class RankingServicesImpl {
    
    @POST
    @Path("/store")
    @Description("Desc2")
    public StoreResponse storeGet( StoreRequest formParam) {
        return new StoreResponse();
        
    }
}
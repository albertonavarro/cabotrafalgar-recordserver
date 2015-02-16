package com.navid.recordserver.springboot.cxf.v2;

import com.navid.recordserver.v2.GetInfoResponse;
import com.navid.recordserver.v2.InfoResource;
import javax.jws.WebService;

public class InfoImpl implements InfoResource {

    @Override
    public GetInfoResponse get() {
        return new GetInfoResponse() {{
            setBuild("build");
            setLastClient("0.8.1");
            setMajorSupported("2");
        }};
    }
    
}

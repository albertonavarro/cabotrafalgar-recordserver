package com.navid.trafalgar.recordserver.endpoints.v2;

import com.navid.recordserver.v2.GetInfoResponse;
import com.navid.recordserver.v2.InfoResource;

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

/*
 */
package com.navid.trafalgar.recordserver.endpoints;

import com.navid.login.SystemCommands;
import com.navid.trafalgar.recordserver.services.RequestContext;
import com.navid.trafalgar.recordserver.services.RequestContextContainer;
import java.io.IOException;
import javax.annotation.Resource;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

public class AuthorisationFilter implements ContainerRequestFilter {

    @Resource(name = "client")
    private SystemCommands systemCommands;

    @Resource
    private RequestContextContainer requestContextContainer;

    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {

        final com.navid.login.UserInfo userInfo = systemCommands.getUserInfo(requestContextContainer.get().getRequestId());
        requestContextContainer.get().setUserId(userInfo.getUsername());

    }

}

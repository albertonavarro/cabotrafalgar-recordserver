/*
 */
package com.navid.trafalgar.recordserver.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RequestContextContainer {

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestContextContainer.class);

    private final ThreadLocal<RequestContext> requestContext = new ThreadLocal<>();

    public RequestContext get() {
        return requestContext.get();
    }

    public RequestContext delete() {
        RequestContext temp = requestContext.get();
        requestContext.set(null);
        LOGGER.debug("Request context deleted: {}", temp);
        return temp;
    }

    public RequestContext create() {
        RequestContext temp = new RequestContext();
        requestContext.set(temp);
        LOGGER.debug("Request context created: {}", temp);
        return temp;
    }

}

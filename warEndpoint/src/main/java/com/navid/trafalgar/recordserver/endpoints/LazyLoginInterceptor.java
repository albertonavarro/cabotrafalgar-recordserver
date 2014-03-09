/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navid.trafalgar.recordserver.endpoints;

import com.navid.login.SystemCommands;
import com.navid.login.UserInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxrs.interceptor.JAXRSInInterceptor;
import org.apache.cxf.message.Message;

/**
 *
 * @author alberto
 */
public class LazyLoginInterceptor extends JAXRSInInterceptor {

    private SystemCommands systemCommands;

    @Context
    private HttpHeaders httpHeaders;

    @Override
    public void handleMessage(Message message) throws Fault {

        UserInfo userinfo = getSystemCommands().getUserInfo("5");
        System.out.println("userinfo");

    }

    /**
     * @return the systemCommands
     */
    public SystemCommands getSystemCommands() {
        return systemCommands;
    }

    /**
     * @param systemCommands the systemCommands to set
     */
    public void setSystemCommands(SystemCommands systemCommands) {
        this.systemCommands = systemCommands;
    }

    @Context
    public void setHttpHeaders(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

}

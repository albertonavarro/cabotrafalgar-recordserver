/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.navid.trafalgar.recordserverendpoints;

import com.navid.login.SystemCommandsImplService;
import com.navid.login.UserInfo;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxrs.interceptor.JAXRSInInterceptor;
import org.apache.cxf.message.Message;

/**
 *
 * @author alberto
 */
public class LazyLoginInterceptor extends JAXRSInInterceptor{
    
    @Context
    HttpHeaders request;
    
    
    
     @Override
     public void handleMessage(Message message) throws Fault {
        try {
            SystemCommandsImplService client = new SystemCommandsImplService(new URL("http://localhost:8080/login/system?wsdl"));
            UserInfo userInfo = client.getSystemCommandsImplPort().getUserInfo("1");
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(LazyLoginInterceptor.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         
         
     }
}

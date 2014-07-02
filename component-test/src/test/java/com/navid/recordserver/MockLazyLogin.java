/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navid.recordserver;

import org.mockserver.client.server.MockServerClient;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 *
 * @author casa
 */
public class MockLazyLogin {

    public static void setUpSessionId(String uuid, String userId, Boolean verified) {

        new MockServerClient("localhost", 1080)
                .when(
                        request()
                        .withMethod("POST")
                        .withPath("/system"),
                        exactly(1)
                )
                .respond(
                        response()
                        .withBody("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
                                + "   <soap:Body>\n"
                                + "      <ns2:getUserInfoResponse xmlns:ns2=\"http://login.navid.com/\">\n"
                                + "         <return>\n"
                                + "            <userid>"+userId+"</userid>\n"
                                + "            <verified>"+verified+"</verified>\n"
                                + "         </return>\n"
                                + "      </ns2:getUserInfoResponse>\n"
                                + "   </soap:Body>\n"
                                + "</soap:Envelope>")
                );
    }

}

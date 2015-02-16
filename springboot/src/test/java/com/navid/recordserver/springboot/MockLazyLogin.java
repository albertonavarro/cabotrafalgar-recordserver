package com.navid.recordserver.springboot;

import org.mockserver.client.server.MockServerClient;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 *
 * @author casa
 */
public class MockLazyLogin {

    public static void setUpSessionId(String uuid, String username, String userId, Boolean verified, int times) {

        new MockServerClient("localhost", 1080)
                .when(
                        request()
                        .withMethod("POST")
                        .withPath("/services/system"),
                        exactly(times)
                )
                .respond(
                        response()
                        .withBody("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
                                + "   <soap:Body>\n"
                                + "      <ns2:getUserInfoResponse xmlns:ns2=\"http://lazylogin.navid.com/\">\n"
                                + "         <return>\n"
                                + "            <username>"+username+"</username>\n"
                                + "            <userid>"+userId+"</userid>\n"
                                + "            <verified>"+verified+"</verified>\n"
                                + "         </return>\n"
                                + "      </ns2:getUserInfoResponse>\n"
                                + "   </soap:Body>\n"
                                + "</soap:Envelope>")
                );
    }

}

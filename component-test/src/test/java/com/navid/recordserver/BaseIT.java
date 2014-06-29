package com.navid.recordserver;

import com.navid.login.UserCommands;
import com.navid.recordserver.jetty.EmbeddedJetty;
import com.navid.recordserver.v1.RankingResource;
import com.navid.trafalgar.recordserver.persistence.couchbase.CDBCandidateRecordRepository;
import com.navid.trafalgar.recordserver.persistence.couchbase.CouchbaseImpl;
import com.navid.trafalgar.recordserver.services.RequestContextContainer;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import static java.util.concurrent.TimeUnit.SECONDS;
import javax.annotation.Resource;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndProxy;
import static org.mockserver.integration.ClientAndProxy.startClientAndProxy;
import org.mockserver.integration.ClientAndServer;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import org.mockserver.model.Cookie;
import org.mockserver.model.Delay;
import org.mockserver.model.Header;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import org.mockserver.model.Parameter;
import static org.mockserver.model.StringBody.exact;
import static org.mockserver.model.StringBody.regex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author vero
 */
@Test
@ContextConfiguration(locations = {"classpath:conf/test-lazylogin-client.xml"})
public class BaseIT extends AbstractTestNGSpringContextTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseIT.class);

    private CouchbaseImpl repository;
    private StdCouchDbInstance instance;

    private final String newDatabaseName = "recordsure-" + System.nanoTime();

    @Resource(name = "test.clientUserLazyLogin")
    protected UserCommands userCommandsClient;

    @Resource(name = "test.clientRecordServer")
    protected RankingResource rankingService;

    @Resource
    protected RequestContextContainer requestContextContainer;

    private ClientAndProxy proxy;
    private ClientAndServer mockServer;

    @BeforeClass
    public void init() throws Exception {
        System.setProperty("env", "-ct");
        
        mockServer = startClientAndServer(1080);
        proxy = startClientAndProxy(1090);
        
        new MockServerClient("localhost", 1080)
        .when(
                request()
                        .withMethod("POST")
                        .withPath("/system"),
                exactly(1)
        )
        .respond(
                response()
                        .withBody("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
"   <soap:Body>\n" +
"      <ns2:getUserInfoResponse xmlns:ns2=\"http://login.navid.com/\">\n" +
"         <return>\n" +
"            <userid>2</userid>\n" +
"            <verified>false</verified>\n" +
"         </return>\n" +
"      </ns2:getUserInfoResponse>\n" +
"   </soap:Body>\n" +
"</soap:Envelope>")
        );

        requestContextContainer.create();
        requestContextContainer.get().setRequestId("reqId");

        WebApplicationContext context = EmbeddedJetty.runServer(6789);

        repository = context.getBean(CouchbaseImpl.class);
        instance = context.getBean(StdCouchDbInstance.class);

        CDBCandidateRecordRepository dbRepresentation = new CDBCandidateRecordRepository(new StdCouchDbConnector(newDatabaseName, instance));
        repository.setRepository(dbRepresentation);
        
        
    }

    @AfterClass
    public void tearDown() throws Exception {
        instance.deleteDatabase(newDatabaseName);

        EmbeddedJetty.stopServer();

        proxy.stop();
        mockServer.stop();
    }

}

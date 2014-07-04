package com.navid.recordserver;

import com.navid.recordserver.jetty.EmbeddedJetty;
import com.navid.recordserver.v1.RankingResource;
import com.navid.trafalgar.recordserver.persistence.couchbase.CDBCandidateRecordRepository;
import com.navid.trafalgar.recordserver.persistence.couchbase.CouchbaseImpl;
import com.navid.trafalgar.recordserver.services.RequestContextContainer;
import javax.annotation.Resource;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.mockserver.integration.ClientAndServer;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
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
    
    private ClientAndServer mockServer;

    @Resource(name = "test.clientRecordServer")
    protected RankingResource rankingService;

    @Resource
    protected RequestContextContainer requestContextContainer;
    
    @Value("${recordserver.port}")
    private int recordServerPort;
    
    @Value("${mockserver.port}")
    private int mockServerPort;
    
    @BeforeMethod
    public void beforeTest() {
        requestContextContainer.create();
    }
    
    @AfterMethod
    public void afterTest() {
        requestContextContainer.delete();
    }
    
    @BeforeClass
    public void init() throws Exception {
        //helping recordserver to choose what config file should use.
        System.setProperty("env", "-ct");
        
        mockServer = startClientAndServer(mockServerPort);
       
        WebApplicationContext context = EmbeddedJetty.runServer(recordServerPort);

        repository = context.getBean(CouchbaseImpl.class);
        instance = context.getBean(StdCouchDbInstance.class);

        CDBCandidateRecordRepository dbRepresentation = new CDBCandidateRecordRepository(new StdCouchDbConnector(newDatabaseName, instance));
        repository.setRepository(dbRepresentation);
    }

    @AfterClass
    public void tearDown() throws Exception {
        instance.deleteDatabase(newDatabaseName);

        EmbeddedJetty.stopServer();

        mockServer.stop();
    }

}

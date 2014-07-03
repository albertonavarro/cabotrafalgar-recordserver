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
    
    private ClientAndServer mockServer;

    @Resource(name = "test.clientRecordServer")
    protected RankingResource rankingService;

    @Resource
    protected RequestContextContainer requestContextContainer;
    
    @BeforeClass
    public void init() throws Exception {
        System.setProperty("env", "-ct");
        
        mockServer = startClientAndServer(1080);
        
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

        mockServer.stop();
    }

}

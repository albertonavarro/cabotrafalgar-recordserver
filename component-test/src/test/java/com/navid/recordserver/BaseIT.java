package com.navid.recordserver;

import com.navid.login.UserCommands;
import com.navid.recordserver.jetty.EmbeddedJetty;
import com.navid.recordserver.v1.RankingResource;
import com.navid.trafalgar.recordserver.persistence.couchbase.CDBCandidateRecordRepository;
import com.navid.trafalgar.recordserver.persistence.couchbase.CouchbaseImpl;
import java.util.concurrent.ExecutionException;
import javax.annotation.Resource;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
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

    @BeforeClass
    public void init() throws InterruptedException, ExecutionException {
        WebApplicationContext context = EmbeddedJetty.runServer().get();

        repository = context.getBean(CouchbaseImpl.class);
        instance = context.getBean(StdCouchDbInstance.class);
        
        CDBCandidateRecordRepository dbRepresentation = new CDBCandidateRecordRepository(new StdCouchDbConnector(newDatabaseName, instance));     
        repository.setRepository(dbRepresentation);
    }

    @AfterClass
    public void tearDown() throws Exception {
        instance.deleteDatabase(newDatabaseName);
  
        EmbeddedJetty.stopServer();
    }

}

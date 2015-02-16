package com.navid.recordserver.springboot;

import com.navid.recordserver.springboot.RecordServerSpringboot;
import com.navid.lazylogin.context.RequestContextContainer;
import com.navid.trafalgar.recordserver.persistence.couchbase.CDBCandidateRecordRepository;
import com.navid.trafalgar.recordserver.persistence.couchbase.CouchbaseImpl;
import javax.annotation.Resource;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.mockserver.integration.ClientAndServer;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
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
@ContextConfiguration(locations = {"classpath:conf/test-recordserver-client.xml"})
public class BaseIT extends AbstractTestNGSpringContextTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseIT.class);

    private CouchbaseImpl repository;
    private StdCouchDbInstance instance;

    private final String newDatabaseName = "recordserver-" + System.nanoTime();
    private ClientAndServer mockServer;

    ConfigurableApplicationContext app;

    @Resource
    protected RequestContextContainer requestContextContainer;

    @Value("${recordserver.port}")
    private int recordServerPort;
    
    @Value("${mockserver.port}")
    private int mockServerPort;
    
    @BeforeMethod
    public void beforeTest() {

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

        app = new SpringApplication(RecordServerSpringboot.class).run(new String[0]);

        repository = app.getBean(CouchbaseImpl.class);
        instance = app.getBean(StdCouchDbInstance.class);

        CDBCandidateRecordRepository dbRepresentation = new CDBCandidateRecordRepository(new StdCouchDbConnector(newDatabaseName, instance));
        repository.setRepository(dbRepresentation);
    }

    @AfterClass
    public void tearDown() throws Exception {
        instance.deleteDatabase(newDatabaseName);

        SpringApplication.exit(app, new ExitCodeGenerator() {

            @Override
            public int getExitCode() {
                return 0;
            }
        });

        mockServer.stop();
    }

}

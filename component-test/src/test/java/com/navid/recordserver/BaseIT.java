package com.navid.recordserver;

import com.navid.recordserver.v1.RankingResource;
import com.navid.trafalgar.recordserver.persistence.couchbase.CDBCandidateRecordRepository;
import com.navid.trafalgar.recordserver.persistence.couchbase.CouchbaseImpl;
import javax.annotation.Resource;
import org.ektorp.CouchDbConnector;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author vero
 */
@Test
@ContextConfiguration(locations = {"classpath:conf/config-main.xml", "classpath:conf/config-test.xml"})
public class BaseIT extends AbstractTestNGSpringContextTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseIT.class);
    
    @Resource(name = "recordsure.rankingendpoint")
    protected RankingResource rankingService;
    
    @Resource
    private CouchbaseImpl repository;
    
    @Resource( name= "couchDbInstance")
    private StdCouchDbInstance instance;
           
    private final String newDatabaseName = "recordsure-" + System.nanoTime();

    @BeforeClass
    public void init() {
        CDBCandidateRecordRepository dbRepresentation = new CDBCandidateRecordRepository(new StdCouchDbConnector(newDatabaseName, instance));     
        repository.setRepository(dbRepresentation);
    }
    
    @AfterClass
    public void tearDown() {
        instance.deleteDatabase(newDatabaseName);
    }
    
}

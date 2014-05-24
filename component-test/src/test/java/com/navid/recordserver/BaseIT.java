package com.navid.recordserver;

import com.navid.recordserver.v1.RankingResource;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author vero
 */
@Test
@ContextConfiguration(locations = {"classpath:conf/config-main.xml"})
public class BaseIT extends AbstractTestNGSpringContextTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseIT.class);
    
    @Resource(name = "recordsure.rankingendpoint")
    protected RankingResource rankingService;
    
    @BeforeClass
    public void init() {
        
    }
    
    
    
}

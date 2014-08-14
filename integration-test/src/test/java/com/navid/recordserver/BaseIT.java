package com.navid.recordserver;

import com.navid.lazylogin.context.RequestContextContainer;
import com.navid.lazylogin.CreateTokenRequest;
import com.navid.lazylogin.UserCommands;
import com.navid.recordserver.v1.RankingResource;
import javax.annotation.Resource;
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
@ContextConfiguration(locations = {"classpath:conf/test-lazylogin-client.xml"})
public class BaseIT extends AbstractTestNGSpringContextTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseIT.class);
    
    @Resource(name = "test.clientUserLazyLogin")
    protected UserCommands userCommandsClient;
    
    @Resource(name = "test.clientRecordServer")
    protected RankingResource recordServerClient;
    
    @Resource
    protected RequestContextContainer requestContextContainer;
    
    protected String token;
    
    @BeforeClass
    public void initToken() {        
        CreateTokenRequest request = new CreateTokenRequest() {{
            setEmail("somemail@somedomain.com");
        }};
        
        token = userCommandsClient.createToken(request).getSessionid().getSessionid();
        
        LOGGER.info("Got user token: " + token);
        
        requestContextContainer.get().setSessionId(token);
        
    }
    
    @AfterClass
    public void tearDown() {
        requestContextContainer.delete();
    }
    
    
    
}

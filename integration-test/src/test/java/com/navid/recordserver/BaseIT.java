package com.navid.recordserver;

import com.navid.login.CreateTokenRequest;
import com.navid.login.UserCommands;
import com.navid.recordserver.v1.RankingResource;
import com.navid.trafalgar.recordserver.services.RequestContextContainer;
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
@ContextConfiguration(locations = {"classpath:test-lazylogin-client.xml"})
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
        requestContextContainer.create();
        
        CreateTokenRequest request = new CreateTokenRequest() {{
            setEmail("somemail@somedomain.com");
        }};
        
        token = userCommandsClient.createToken(request).getSessionid().getSessionid();
        
        LOGGER.info("Got user token: " + token);
        
        requestContextContainer.get().setRequestId(token);
        
    }
    
    @AfterClass
    public void tearDown() {
        requestContextContainer.delete();
    }
    
    
    
}

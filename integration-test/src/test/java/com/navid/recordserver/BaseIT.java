package com.navid.recordserver;

import com.navid.codegen.recordserver.ApiClient;
import com.navid.codegen.recordserver.api.DefaultApi;
import com.navid.lazylogin.context.RequestContextContainer;
import com.lazylogin.client.user.v0.CreateTokenRequest;
import com.lazylogin.client.user.v0.UserCommands;
import javax.annotation.Resource;
import javax.wsdl.extensions.ElementExtensible;

import org.apache.cxf.binding.soap.SoapBindingConfiguration;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.wsdl.service.factory.ReflectionServiceFactoryBean;
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

    @Resource
    protected RequestContextContainer requestContextContainer;

    private final String url = System.getProperty("recordserverUrl");
    
    protected String token;

    protected String prefix;

    DefaultApi defaultApi;

    @BeforeClass
    public void initToken() {
        CreateTokenRequest request = new CreateTokenRequest() {{
            setEmail("somemail@somedomain.com");
        }};
        
        token = userCommandsClient.createToken(request).getSessionid().getSessionid();
        
        LOGGER.info("Got user token: " + token);

        requestContextContainer.get().setSessionId(token);

        prefix = Long.toString(System.nanoTime()) + "-";
    }
    
    @AfterClass
    public void tearDown() {
        requestContextContainer.delete();
    }

    public String getUrl() {
        return url;
    }
    
}

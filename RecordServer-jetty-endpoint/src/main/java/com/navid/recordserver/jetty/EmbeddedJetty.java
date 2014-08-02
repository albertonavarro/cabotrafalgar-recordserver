package com.navid.recordserver.jetty;

import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class EmbeddedJetty {

    private static final int DEFAULT_PORT = 8080;
    private static final String CONTEXT_PATH = "/";
    private static final String CONFIG_LOCATION = "classpath:conf/config-main.xml";
    private static final String MAPPING_URL = "/*";
    private static Server server;
    private static XmlWebApplicationContext context;

    public static void main(String[] args) throws Exception {
        new EmbeddedJetty().startJetty(getPortFromArgs(args));
        server.join();
    }

    public static WebApplicationContext runServer(final int port) throws Exception {
        
        
        new EmbeddedJetty().startJetty(port);
        return context;
    }

    private static int getPortFromArgs(String[] args) {
        if (args.length > 0) {
            try {
                return Integer.valueOf(args[0]);
            } catch (NumberFormatException ignore) {
            }
        }
        return DEFAULT_PORT;
    }

    public void startJetty(int port) throws Exception {
        server = new Server(port);
        server.setHandler(getServletContextHandler(getContext()));
        server.start();
    }

    public static void stopServer() throws Exception {
        if (server != null) {
            server.stop();
        }

    }

    private static ServletContextHandler getServletContextHandler(WebApplicationContext context) throws IOException {
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setErrorHandler(null);
        contextHandler.setContextPath(CONTEXT_PATH);
        contextHandler.addServlet(new ServletHolder(new CXFServlet()), MAPPING_URL);

        contextHandler.addEventListener(new ContextLoaderListener(context));
        //contextHandler.setResourceBase(new ClassPathResource("webapp").getURI().toString());
        return contextHandler;
    }
    
    private static Handler getServletStaticContextHandler() throws IOException, URISyntaxException {
        ResourceHandler resHandler = new ResourceHandler();
        resHandler.setResourceBase(Resource.newClassPathResource("/static").toString());
        ContextHandler ctx = new ContextHandler("/static"); /* the server uri path */
        ctx.setHandler(resHandler);
        return ctx;
    }

    private static XmlWebApplicationContext getContext() {
        context = new XmlWebApplicationContext();
        context.setConfigLocation(CONFIG_LOCATION);
        return context;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.rest.application.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author anf
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(org.apache.cxf.jaxrs.impl.PathSegmentImpl.class);
        resources.add(org.apache.cxf.jaxrs.provider.AbstractCachingMessageProvider.class);
        resources.add(org.apache.cxf.jaxrs.provider.CachingMessageBodyReader.class);
        resources.add(org.apache.cxf.jaxrs.provider.CachingMessageBodyWriter.class);
        resources.add(org.apache.cxf.jaxrs.provider.DataBindingProvider.class);
        resources.add(org.apache.cxf.jaxrs.provider.DataSourceProvider.class);
        resources.add(org.apache.cxf.jaxrs.provider.FormEncodingProvider.class);
        resources.add(org.apache.cxf.jaxrs.provider.JAXBElementProvider.class);
        resources.add(org.apache.cxf.jaxrs.provider.MultipartProvider.class);
        resources.add(org.apache.cxf.jaxrs.provider.RequestDispatcherProvider.class);
        resources.add(org.apache.cxf.jaxrs.provider.SourceProvider.class);
        resources.add(org.apache.cxf.jaxrs.provider.XPathProvider.class);
        resources.add(org.apache.cxf.jaxrs.provider.XSLTJaxbProvider.class);
        resources.add(org.apache.cxf.jaxrs.validation.ValidationExceptionMapper.class);
    }
    
}

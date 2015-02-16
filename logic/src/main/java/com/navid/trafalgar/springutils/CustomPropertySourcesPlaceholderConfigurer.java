
package com.navid.trafalgar.springutils;

import java.util.Map.Entry;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 *
 * @author casa
 */
public final class CustomPropertySourcesPlaceholderConfigurer extends PropertyPlaceholderConfigurer implements InitializingBean{

    private static final Logger LOG = LoggerFactory.getLogger(CustomPropertySourcesPlaceholderConfigurer.class);

    @Override
    public void afterPropertiesSet(){
        try{
            Properties loadedProperties = this.mergeProperties();
            for(Entry<Object, Object> singleProperty : loadedProperties.entrySet()){
                LOG.info("LoadedProperty: {}={}",singleProperty.getKey(), singleProperty.getValue());
            }
        }catch(Exception ex){
            LOG.error("Error loading properties", ex);
        }
    }
}
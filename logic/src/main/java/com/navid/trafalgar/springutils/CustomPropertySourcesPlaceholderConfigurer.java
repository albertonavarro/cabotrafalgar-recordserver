
package com.navid.trafalgar.springutils;

import java.util.Map.Entry;
import java.util.Properties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 *
 * @author casa
 */
public class CustomPropertySourcesPlaceholderConfigurer extends PropertyPlaceholderConfigurer implements InitializingBean{

    @Override
    public void afterPropertiesSet(){
        try{
            Properties loadedProperties = this.mergeProperties();
            for(Entry<Object, Object> singleProperty : loadedProperties.entrySet()){
                logger.info("LoadedProperty: "+singleProperty.getKey()+"="+singleProperty.getValue());
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
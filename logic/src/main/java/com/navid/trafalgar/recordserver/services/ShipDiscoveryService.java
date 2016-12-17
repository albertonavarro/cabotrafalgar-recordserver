package com.navid.trafalgar.recordserver.services;

import com.navid.trafalgar.model.AShipModel;
import org.reflections.Reflections;

import javax.annotation.PostConstruct;
import java.util.Set;

public class ShipDiscoveryService {
    
    @PostConstruct
    public void init() {
        Reflections reflections = new Reflections("com.navid.trafalgar");

        Set<Class<? extends AShipModel>> result = reflections.getSubTypesOf(AShipModel.class);
        
        

    }
}

package com.navid.trafalgar.recordserver.services;

import com.navid.trafalgar.model.AShipModel;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

@Component
public class ShipDiscoveryService {
    
    @PostConstruct
    public void init() {
        Reflections reflections = new Reflections("com.navid.trafalgar");

        Set<Class<? extends AShipModel>> result = reflections.getSubTypesOf(AShipModel.class);
        
        

    }
}

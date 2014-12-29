package com.navid.trafalgar.recordserver.endpoints.jmx;

import static com.google.common.collect.Lists.newArrayList;
import java.util.List;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource
public class JMXEndpoint {
    
    @ManagedOperation
    public final void deleteEntries(String user, String map, String ship) {
    }
    
    @ManagedAttribute
    public final List<String> getAvailableMaps() {
        return newArrayList("mock", "mocked");
    }

    @ManagedAttribute
    public final int getNumberOfUsers(){
        return 1;
    }

}

package com.navid.trafalgar.recordserver.endpoints.jmx;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import static com.google.common.collect.Lists.newArrayList;
import com.navid.trafalgar.recordserver.persistence.UsersReport;
import com.navid.trafalgar.recordserver.services.RecordServerServices;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Component
@ManagedResource(objectName = "recordServer:name=adminPanel")
public class JMXEndpoint {
    
    private static final Logger LOG = LoggerFactory.getLogger(JMXEndpoint.class);
    
    @Resource
    private RecordServerServices recordServerServices;
    
    @ManagedOperation
    public final void deleteEntries(String user, String map, String ship) {
    }
    
    @ManagedAttribute
    public final List<String> getAvailableMaps() {
        return newArrayList("mock", "mocked");
    }

    @ManagedAttribute
    public final int getNumberOfUsers(){
        return recordServerServices.getUsersReport().size();
    }
    
    @ManagedAttribute
    public final List<String> getUsersReport(){
        return Lists.transform(recordServerServices.getUsersReport(), new Function<UsersReport, String>(){
            @Override
            public String apply(UsersReport f) {
                return f.getUserName() + ": " + f.getGames();
            }
        });
    }

}

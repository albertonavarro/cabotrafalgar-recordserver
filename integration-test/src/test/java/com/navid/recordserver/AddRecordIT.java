package com.navid.recordserver;

import com.navid.recordserver.v1.AddRecordRequest;
import com.navid.recordserver.v1.AddRecordResponse;
import org.testng.annotations.Test;

/**
 *
 * @author vero
 */
public class AddRecordIT extends BaseIT {
    
    @Test
    public void smokeIT() {
        AddRecordRequest addRecordRequest = new AddRecordRequest();
        addRecordRequest.setId(1);
        addRecordRequest.setPayload(token);
        
        AddRecordResponse response = recordServerClient.post(addRecordRequest);
  
    }
    
}

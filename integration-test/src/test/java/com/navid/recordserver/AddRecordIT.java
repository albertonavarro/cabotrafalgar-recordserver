package com.navid.recordserver;

import com.navid.recordserver.v1.AddRecordRequest;
import com.navid.recordserver.v1.AddRecordResponse;
import com.navid.recordserver.v1.RankingResource;
import javax.annotation.Resource;
import static org.hamcrest.CoreMatchers.equalTo;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.Test;

/**
 *
 * @author vero
 */
public class AddRecordIT extends BaseIT {
    
    @Resource(name = "test.clientRecordServer")
    protected RankingResource recordServerClient;
    
    @Test
    public void smokeIT() {
        AddRecordRequest addRecordRequest = new AddRecordRequest();
        addRecordRequest.setPayload("{\"version\":1,\"header\":{\"map\":\"" + "one" + "\",\"shipModel\":\"ShipModelOneY\"},\"stepRecordList\":[{\"position\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"rotation\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"w\":1.0},\"timestamp\":0.13044842,\"eventList\":[]},{\"position\":{\"x\":-267.15237,\"y\":0.0,\"z\":-784.9582},\"rotation\":{\"x\":-0.08990571,\"y\":-0.89595354,\"z\":0.21214685,\"w\":-0.3796915},\"timestamp\":61.56301,\"eventList\":[\"MILLESTONE_REACHED\"]}]}");
        
        AddRecordResponse response = recordServerClient.post(addRecordRequest);
  
        MatcherAssert.assertThat(response.getPosition(), equalTo(1));
    }
    
}

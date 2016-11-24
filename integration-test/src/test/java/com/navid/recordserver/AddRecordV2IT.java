package com.navid.recordserver;

import javax.annotation.Resource;
import static org.hamcrest.CoreMatchers.equalTo;

import com.navid.codegen.recordserver.ApiClient;
import com.navid.codegen.recordserver.ApiException;
import com.navid.codegen.recordserver.api.DefaultApi;
import com.navid.codegen.recordserver.model.NewMapEntryRequest;
import com.navid.codegen.recordserver.model.NewMapEntryResponse;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.Test;

/**
 *
 * @author vero
 */
public class AddRecordV2IT extends BaseIT {
    
    @Test
    public void smokeIT() throws ApiException {
        DefaultApi defaultApi = new DefaultApi();
        ApiClient apiClient = defaultApi.getApiClient();
        apiClient.setBasePath(getUrl());

        NewMapEntryResponse response = defaultApi.postRanking(new NewMapEntryRequest().payload("{\"version\":1,\"header\":{\"map\":\"" + "one" + "\",\"shipModel\":\"ShipModelOneY\"},\"stepRecordList\":[{\"position\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"rotation\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"w\":1.0},\"timestamp\":0.13044842,\"eventList\":[]},{\"position\":{\"x\":-267.15237,\"y\":0.0,\"z\":-784.9582},\"rotation\":{\"x\":-0.08990571,\"y\":-0.89595354,\"z\":0.21214685,\"w\":-0.3796915},\"timestamp\":61.56301,\"eventList\":[\"MILLESTONE_REACHED\"]}]}"));

        MatcherAssert.assertThat(response.getPosition(), equalTo(1));
    }
    
}

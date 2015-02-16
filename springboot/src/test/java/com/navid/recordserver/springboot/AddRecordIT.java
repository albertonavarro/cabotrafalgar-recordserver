package com.navid.recordserver.springboot;

import com.navid.recordserver.v1.AddRecordRequest;
import com.navid.recordserver.v1.AddRecordResponse;
import com.navid.recordserver.v1.GetMapRecordsResponse;
import com.navid.recordserver.v1.GetRecordResponse;
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
    protected RankingResource rankingService;
    
    @Test
    public void addOneRecord() {
        //Given
        requestContextContainer.get().setSessionId("anyString");
        MockLazyLogin.setUpSessionId(getMockServerPort(), null, "username", "2", Boolean.FALSE, 1);
        
        AddRecordRequest addRecordRequest = new AddRecordRequest();
        addRecordRequest.setPayload(getPayload("one", "61.56301"));

        //When
        AddRecordResponse response = rankingService.post(addRecordRequest);

        //Then
        MatcherAssert.assertThat(response.getPosition(), equalTo(1));
    }

    @Test
    public void getByMap() {
        //Given
        requestContextContainer.get().setSessionId("anyString");
        MockLazyLogin.setUpSessionId(getMockServerPort(), null, "username", "2", Boolean.FALSE, 3);
        
        AddRecordRequest addRecordRequest = new AddRecordRequest();
        addRecordRequest.setPayload(getPayload("addMoreRecords", "61.56301"));

        rankingService.post(addRecordRequest);

        AddRecordRequest addRecordRequest2 = new AddRecordRequest();
        addRecordRequest2.setPayload(getPayload("addMoreRecords", "62.0"));

        rankingService.post(addRecordRequest2);

        GetMapRecordsResponse searchResult = rankingService.getMapsmap("addMoreRecords");
        
        MatcherAssert.assertThat(searchResult.getRankingEntry().size(), equalTo(2));
        MatcherAssert.assertThat(searchResult.getRankingEntry().get(0).getPosition(), equalTo(1));
        MatcherAssert.assertThat(searchResult.getRankingEntry().get(1).getPosition(), equalTo(2));
    }
    
    /*@Test
    public void getByUser() {
        //Given
        requestContextContainer.get().setRequestId("anyString");
        MockLazyLogin.setUpSessionId(null, "username", "2", Boolean.FALSE, 2);
        
        AddRecordRequest addRecordRequest = new AddRecordRequest();
        addRecordRequest.setPayload(getPayload("getByUser", "61.56301"));
        rankingService.post(addRecordRequest);

        AddRecordRequest addRecordRequest2 = new AddRecordRequest();
        addRecordRequest2.setPayload(getPayload("getByUser", "61.56301"));
        rankingService.post(addRecordRequest2);
        
        MockLazyLogin.setUpSessionId(null, "otherusername", "3", Boolean.FALSE, 3);

        AddRecordRequest addRecordRequest3 = new AddRecordRequest();
        addRecordRequest3.setPayload(getPayload("getByUser", "61.56301"));
        rankingService.post(addRecordRequest3);

        AddRecordRequest addRecordRequest4 = new AddRecordRequest();
        addRecordRequest4.setPayload(getPayload("getByUser", "61.56301"));
        rankingService.post(addRecordRequest4);
    
        GetMapRecordsResponse searchResult = rankingService.getUseruser("username");
        
        MatcherAssert.assertThat(searchResult.getRankingEntry().size(), equalTo(2));
        MatcherAssert.assertThat(searchResult.getRankingEntry().get(0).getPosition(), equalTo(1));
        MatcherAssert.assertThat(searchResult.getRankingEntry().get(1).getPosition(), equalTo(2));
    }
    */
    @Test
    public void getById() {
        //Given
        requestContextContainer.get().setSessionId("anyString");
        MockLazyLogin.setUpSessionId(getMockServerPort(), null, "username", "2", Boolean.FALSE, 3);
        
        AddRecordRequest addRecordRequest = new AddRecordRequest();
        addRecordRequest.setPayload(getPayload("getById", "61.56301"));

        AddRecordResponse response = rankingService.post(addRecordRequest);
        MatcherAssert.assertThat("response is null!", response != null );

        GetRecordResponse result = rankingService.getIdid(response.getId());
        MatcherAssert.assertThat("result is not right!", result.getPayload().equals(getPayload("getById", "61.56301")));
    }
    
    private String getPayload(String map, String finalTime) {
        return "{\"version\":1,\"header\":{\"map\":\"" + map + "\",\"shipModel\":\"ShipModelOneY\"},\"stepRecordList\":[{\"position\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"rotation\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"w\":1.0},\"timestamp\":0.13044842,\"eventList\":[]},{\"position\":{\"x\":-267.15237,\"y\":0.0,\"z\":-784.9582},\"rotation\":{\"x\":-0.08990571,\"y\":-0.89595354,\"z\":0.21214685,\"w\":-0.3796915},\"timestamp\":"+finalTime+",\"eventList\":[\"MILLESTONE_REACHED\"]}]}";
    }

}
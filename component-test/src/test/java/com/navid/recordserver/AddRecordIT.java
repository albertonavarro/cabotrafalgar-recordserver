package com.navid.recordserver;

import com.navid.recordserver.v1.AddRecordRequest;
import com.navid.recordserver.v1.AddRecordResponse;
import com.navid.recordserver.v1.GetMapRecordsResponse;
import static org.hamcrest.CoreMatchers.equalTo;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.Test;

/**
 *
 * @author vero
 */
public class AddRecordIT extends BaseIT {
    
    @Test
    public void addOneRecord() {
        //Given
        requestContextContainer.get().setRequestId("anyString");
        MockLazyLogin.setUpSessionId(null, "2", Boolean.FALSE, 1);
        
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
        requestContextContainer.get().setRequestId("anyString");
        MockLazyLogin.setUpSessionId(null, "2", Boolean.FALSE, 3);
        
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
    /*
    @Test
    public void getByUser() {
        AddRecordRequest addRecordRequest = new AddRecordRequest();
        addRecordRequest.setPayload("{\"version\":1,\"header\":{\"map\":\"" + "getByUser1" + "\",\"shipModel\":\"ShipModelOneX\"},\"stepRecordList\":[{\"position\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"rotation\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"w\":1.0},\"timestamp\":0.13044842,\"eventList\":[]},{\"position\":{\"x\":-267.15237,\"y\":0.0,\"z\":-784.9582},\"rotation\":{\"x\":-0.08990571,\"y\":-0.89595354,\"z\":0.21214685,\"w\":-0.3796915},\"timestamp\":61.56301,\"eventList\":[\"MILLESTONE_REACHED\"]}]}");
        rankingService.post(addRecordRequest);

        AddRecordRequest addRecordRequest2 = new AddRecordRequest();
        addRecordRequest2.setPayload("{\"version\":1,\"header\":{\"map\":\"" + "getByUser2" + "\",\"shipModel\":\"ShipModelOneX\"},\"stepRecordList\":[{\"position\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"rotation\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"w\":1.0},\"timestamp\":0.13044842,\"eventList\":[]},{\"position\":{\"x\":-267.15237,\"y\":0.0,\"z\":-784.9582},\"rotation\":{\"x\":-0.08990571,\"y\":-0.89595354,\"z\":0.21214685,\"w\":-0.3796915},\"timestamp\":62.0,\"eventList\":[\"MILLESTONE_REACHED\"]}]}");
        rankingService.post(addRecordRequest2);
        
        AddRecordRequest addRecordRequest3 = new AddRecordRequest();
        addRecordRequest3.setPayload("{\"version\":1,\"header\":{\"map\":\"" + "getByUser1" + "\",\"shipModel\":\"ShipModelOneX\"},\"stepRecordList\":[{\"position\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"rotation\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"w\":1.0},\"timestamp\":0.13044842,\"eventList\":[]},{\"position\":{\"x\":-267.15237,\"y\":0.0,\"z\":-784.9582},\"rotation\":{\"x\":-0.08990571,\"y\":-0.89595354,\"z\":0.21214685,\"w\":-0.3796915},\"timestamp\":61.56301,\"eventList\":[\"MILLESTONE_REACHED\"]}]}");
        rankingService.post(addRecordRequest3);

        AddRecordRequest addRecordRequest4 = new AddRecordRequest();
        addRecordRequest4.setPayload("{\"version\":1,\"header\":{\"map\":\"" + "getByUser2" + "\",\"shipModel\":\"ShipModelOneX\"},\"stepRecordList\":[{\"position\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"rotation\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"w\":1.0},\"timestamp\":0.13044842,\"eventList\":[]},{\"position\":{\"x\":-267.15237,\"y\":0.0,\"z\":-784.9582},\"rotation\":{\"x\":-0.08990571,\"y\":-0.89595354,\"z\":0.21214685,\"w\":-0.3796915},\"timestamp\":62.0,\"eventList\":[\"MILLESTONE_REACHED\"]}]}");
        rankingService.post(addRecordRequest4);
    
    }
    
    @Test
    public void getById() {
    
    }*/
    
    private String getPayload(String map, String finalTime) {
        return "{\"version\":1,\"header\":{\"map\":\"" + map + "\",\"shipModel\":\"ShipModelOneX\"},\"stepRecordList\":[{\"position\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"rotation\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"w\":1.0},\"timestamp\":0.13044842,\"eventList\":[]},{\"position\":{\"x\":-267.15237,\"y\":0.0,\"z\":-784.9582},\"rotation\":{\"x\":-0.08990571,\"y\":-0.89595354,\"z\":0.21214685,\"w\":-0.3796915},\"timestamp\":"+finalTime+",\"eventList\":[\"MILLESTONE_REACHED\"]}]}";
    }

}

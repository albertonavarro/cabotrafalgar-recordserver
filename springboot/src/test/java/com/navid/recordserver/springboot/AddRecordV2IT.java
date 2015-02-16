package com.navid.recordserver.springboot;

import com.navid.recordserver.v2.V2Resource;
import com.navid.recordserver.v2.AddRecordRequest;
import com.navid.recordserver.v2.AddRecordResponse;
import com.navid.recordserver.v2.GetMapRecordsResponse;
import com.navid.recordserver.v2.GetRecordResponse;
import javax.annotation.Resource;
import static org.hamcrest.CoreMatchers.equalTo;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.Test;

/**
 *
 * @author vero
 */
public class AddRecordV2IT extends BaseIT {

    @Resource(name = "test.clientRecordServerV2")
    protected V2Resource rankingService;

    @Test
    public void addOneRecord() {
        //Given
        requestContextContainer.get().setSessionId("anyString");
        MockLazyLogin.setUpSessionId(null, "username", "2", Boolean.FALSE, 1);

        AddRecordRequest addRecordRequest = new AddRecordRequest();
        addRecordRequest.setPayload(getPayload("one", "61.56301", "ShipModelOneY"));

        //When
        AddRecordResponse response = rankingService.postRanking(addRecordRequest);

        //Then
        MatcherAssert.assertThat(response.getPosition(), equalTo(1));
    }

    @Test
    public void getByMapSameShip() {
        //Given
        requestContextContainer.get().setSessionId("anyString");
        MockLazyLogin.setUpSessionId(null, "username", "2", Boolean.FALSE, 3);

        AddRecordRequest addRecordRequest = new AddRecordRequest();
        addRecordRequest.setPayload(getPayload("MapSame", "61.56301", "ShipModelOneY"));

        rankingService.postRanking(addRecordRequest);

        AddRecordRequest addRecordRequest2 = new AddRecordRequest();
        addRecordRequest2.setPayload(getPayload("MapSame", "62.0", "ShipModelOneY"));

        rankingService.postRanking(addRecordRequest2);

        GetMapRecordsResponse searchResult = rankingService.getRankingshipshipmapsmap("MapSame", "ShipModelOneY");

        MatcherAssert.assertThat(searchResult.getRankingEntry().size(), equalTo(2));
        MatcherAssert.assertThat(searchResult.getRankingEntry().get(0).getPosition(), equalTo(1));
        MatcherAssert.assertThat(searchResult.getRankingEntry().get(1).getPosition(), equalTo(2));
    }

    @Test
    public void getByMapDifferentShip() {
        //Given
        requestContextContainer.get().setSessionId("anyString");
        MockLazyLogin.setUpSessionId(null, "username", "2", Boolean.FALSE, 3);

        AddRecordRequest addRecordRequest = new AddRecordRequest();
        addRecordRequest.setPayload(getPayload("MapDifferent", "61.56301", "ShipModelOneY"));

        rankingService.postRanking(addRecordRequest);

        AddRecordRequest addRecordRequest2 = new AddRecordRequest();
        addRecordRequest2.setPayload(getPayload("MapDifferent", "62.0", "ShipModelOneZ"));

        rankingService.postRanking(addRecordRequest2);

        GetMapRecordsResponse searchResult = rankingService.getRankingshipshipmapsmap("MapDifferent", "ShipModelOneZ");

        MatcherAssert.assertThat(searchResult.getRankingEntry().size(), equalTo(1));
        MatcherAssert.assertThat(searchResult.getRankingEntry().get(0).getPosition(), equalTo(1));
    }

    @Test
    public void getById() {
        //Given
        requestContextContainer.get().setSessionId("anyString");
        MockLazyLogin.setUpSessionId(null, "username", "2", Boolean.FALSE, 3);

        AddRecordRequest addRecordRequest = new AddRecordRequest();
        addRecordRequest.setPayload(getPayload("getById", "61.56301", "ShipModelOneY"));

        AddRecordResponse response = rankingService.postRanking(addRecordRequest);
        MatcherAssert.assertThat("response is null!", response != null);

        GetRecordResponse result = rankingService.getRankingidid(response.getId());
        MatcherAssert.assertThat("result is not right!", result.getPayload().equals(getPayload("getById", "61.56301", "ShipModelOneY")));
    }

    private String getPayload(String map, String finalTime, String shipName) {
        return "{\"version\":1,\"header\":{\"map\":\"" + map + "\",\"shipModel\":\"" + shipName + "\"},\"stepRecordList\":[{\"position\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"rotation\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"w\":1.0},\"timestamp\":0.13044842,\"eventList\":[]},{\"position\":{\"x\":-267.15237,\"y\":0.0,\"z\":-784.9582},\"rotation\":{\"x\":-0.08990571,\"y\":-0.89595354,\"z\":0.21214685,\"w\":-0.3796915},\"timestamp\":" + finalTime + ",\"eventList\":[\"MILLESTONE_REACHED\"]}]}";
    }

}

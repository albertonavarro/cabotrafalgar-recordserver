package com.navid.recordserver.springboot;

import javax.annotation.Resource;
import static org.hamcrest.CoreMatchers.equalTo;

import com.navid.recordserverapi.server.api.IdApi;
import com.navid.recordserverapi.server.api.RankingApi;
import com.navid.recordserverapi.server.api.ShipApi;
import com.navid.recordserverapi.server.model.MapEntry;
import com.navid.recordserverapi.server.model.NewMapEntryRequest;
import com.navid.recordserverapi.server.model.NewMapEntryResponse;
import com.navid.recordserverapi.server.model.RankingEntry;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.Test;
import retrofit2.Retrofit;

/**
 *
 * @author vero
 */
public class AddRecordV2IT extends BaseIT {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://recordserver.trafalgar.ws:8081")
            .build();

    RetrofitApi rankingApi = retrofit.create(RetrofitApi.class);

    @Test
    public void addOneRecord() {
        //Given
        requestContextContainer.get().setSessionId("anyString");
        MockLazyLogin.setUpSessionId(getMockServerPort(), null, "username", "2", Boolean.FALSE, 1);

        NewMapEntryRequest addRecordRequest = new NewMapEntryRequest();
        addRecordRequest.setPayload(getPayload("one", "61.56301", "ShipModelOneY"));

        //When
        NewMapEntryResponse response = rankingApi.postRanking(addRecordRequest);

        //Then
        MatcherAssert.assertThat(response.getPosition(), equalTo(1));
    }

    @Test
    public void getByMapSameShip() {
        //Given
        requestContextContainer.get().setSessionId("anyString");
        MockLazyLogin.setUpSessionId(getMockServerPort(), null, "username", "2", Boolean.FALSE, 3);

        NewMapEntryRequest addRecordRequest = new NewMapEntryRequest();
        addRecordRequest.setPayload(getPayload("MapSame", "61.56301", "ShipModelOneY"));

        rankingApi.postRanking(addRecordRequest);

        NewMapEntryRequest addRecordRequest2 = new NewMapEntryRequest();
        addRecordRequest2.setPayload(getPayload("MapSame", "62.0", "ShipModelOneY"));

        rankingApi.postRanking(addRecordRequest2);

        RankingEntry searchResult = rankingApi.getByShipAndMap("MapSame", "ShipModelOneY");

        MatcherAssert.assertThat(searchResult.getRankingEntry().size(), equalTo(2));
        MatcherAssert.assertThat(searchResult.getRankingEntry().get(0).getPosition(), equalTo(1));
        MatcherAssert.assertThat(searchResult.getRankingEntry().get(1).getPosition(), equalTo(2));
    }

    @Test
    public void getByMapDifferentShip() {
        //Given
        requestContextContainer.get().setSessionId("anyString");
        MockLazyLogin.setUpSessionId(getMockServerPort(), null, "username", "2", Boolean.FALSE, 3);

        NewMapEntryRequest addRecordRequest = new NewMapEntryRequest();
        addRecordRequest.setPayload(getPayload("MapDifferent", "61.56301", "ShipModelOneY"));

        rankingApi.postRanking(addRecordRequest);

        NewMapEntryRequest addRecordRequest2 = new NewMapEntryRequest();
        addRecordRequest2.setPayload(getPayload("MapDifferent", "62.0", "ShipModelOneZ"));

        rankingApi.postRanking(addRecordRequest2);

        RankingEntry searchResult = rankingApi.getByShipAndMap("MapDifferent", "ShipModelOneZ");

        MatcherAssert.assertThat(searchResult.getRankingEntry().size(), equalTo(1));
        MatcherAssert.assertThat(searchResult.getRankingEntry().get(0).getPosition(), equalTo(1));
    }

    @Test
    public void getById() {
        //Given
        requestContextContainer.get().setSessionId("anyString");
        MockLazyLogin.setUpSessionId(getMockServerPort(), null, "username", "2", Boolean.FALSE, 3);

        NewMapEntryRequest addRecordRequest = new NewMapEntryRequest();
        addRecordRequest.setPayload(getPayload("getById", "61.56301", "ShipModelOneY"));

        NewMapEntryResponse response = rankingApi.postRanking(addRecordRequest);
        MatcherAssert.assertThat("response is null!", response != null);

        MapEntry result = rankingApi.getById(response.getId());
        MatcherAssert.assertThat("result is not right!", result.getPayload().equals(getPayload("getById", "61.56301", "ShipModelOneY")));
    }

    private String getPayload(String map, String finalTime, String shipName) {
        return "{\"version\":1,\"header\":{\"map\":\"" + map + "\",\"shipModel\":\"" + shipName + "\"},\"stepRecordList\":[{\"position\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"rotation\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"w\":1.0},\"timestamp\":0.13044842,\"eventList\":[]},{\"position\":{\"x\":-267.15237,\"y\":0.0,\"z\":-784.9582},\"rotation\":{\"x\":-0.08990571,\"y\":-0.89595354,\"z\":0.21214685,\"w\":-0.3796915},\"timestamp\":" + finalTime + ",\"eventList\":[\"MILLESTONE_REACHED\"]}]}";
    }

}

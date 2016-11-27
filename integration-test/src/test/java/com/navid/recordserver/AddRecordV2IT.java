package com.navid.recordserver;

import javax.annotation.Resource;
import static org.hamcrest.CoreMatchers.equalTo;

import com.navid.codegen.recordserver.ApiClient;
import com.navid.codegen.recordserver.ApiException;
import com.navid.codegen.recordserver.api.DefaultApi;
import com.navid.codegen.recordserver.model.MapEntry;
import com.navid.codegen.recordserver.model.NewMapEntryRequest;
import com.navid.codegen.recordserver.model.NewMapEntryResponse;
import com.navid.codegen.recordserver.model.RankingEntry;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author vero
 */
public class AddRecordV2IT extends BaseIT {

    DefaultApi defaultApi;

    @BeforeClass
    public void init() {
        defaultApi = new DefaultApi();
        ApiClient apiClient = defaultApi.getApiClient();
        apiClient.setBasePath(getUrl());
    }

    @Test
    public void addOneRecord() throws ApiException {
        //Given

        NewMapEntryRequest addRecordRequest = new NewMapEntryRequest();
        addRecordRequest.setPayload(getPayload(prefix + "one", "61.56301", "ShipModelOneZ"));

        //When
        NewMapEntryResponse response = defaultApi.postRanking(addRecordRequest, token, "correlationId");

        //Then
        MatcherAssert.assertThat(response.getPosition(), equalTo(1));
    }

    @Test
    public void getByMapSameShip() throws ApiException {
        //Given

        NewMapEntryRequest addRecordRequest = new NewMapEntryRequest();
        addRecordRequest.setPayload(getPayload(prefix + "MapSame", "61.56301", "ShipModelOneZ"));

        defaultApi.postRanking(addRecordRequest, token, "correlationId");

        NewMapEntryRequest addRecordRequest2 = new NewMapEntryRequest();
        addRecordRequest2.setPayload(getPayload(prefix + "MapSame", "62.0", "ShipModelOneZ"));

        defaultApi.postRanking(addRecordRequest2, token, "correlationId");

        RankingEntry searchResult = defaultApi.getByShipAndMap(prefix + "MapSame", "ShipModelOneZ", token, "correlationId");

        MatcherAssert.assertThat(searchResult.getRankingEntry().size(), equalTo(2));
        MatcherAssert.assertThat(searchResult.getRankingEntry().get(0).getPosition(), equalTo(1));
        MatcherAssert.assertThat(searchResult.getRankingEntry().get(1).getPosition(), equalTo(2));
    }

    @Test
    public void getByMapDifferentShip() throws ApiException {
        //Given

        NewMapEntryRequest addRecordRequest = new NewMapEntryRequest();
        addRecordRequest.setPayload(getPayload(prefix + "MapDifferent", "61.56301", "ShipModelOneY"));

        defaultApi.postRanking(addRecordRequest, token, "correlationId");

        NewMapEntryRequest addRecordRequest2 = new NewMapEntryRequest();
        addRecordRequest2.setPayload(getPayload(prefix + "MapDifferent", "62.0", "ShipModelOneZ"));

        defaultApi.postRanking(addRecordRequest2, token, "correlationId");

        RankingEntry searchResult = defaultApi.getByShipAndMap(prefix + "MapDifferent", "ShipModelOneZ", token, "correlationId");

        MatcherAssert.assertThat(searchResult.getRankingEntry().size(), equalTo(1));
        MatcherAssert.assertThat(searchResult.getRankingEntry().get(0).getPosition(), equalTo(1));
    }

    @Test
    public void getById() throws ApiException {
        //Given

        NewMapEntryRequest addRecordRequest = new NewMapEntryRequest();
        addRecordRequest.setPayload(getPayload(prefix + "getById", "61.56301", "ShipModelOneZ"));

        NewMapEntryResponse response = defaultApi.postRanking(addRecordRequest, token, "correlationId");
        MatcherAssert.assertThat("response is null!", response != null);

        MapEntry result = defaultApi.getById(response.getId(), token, "correlationId");
        MatcherAssert.assertThat("result is not right!", result.getPayload().equals(getPayload(prefix + "getById", "61.56301", "ShipModelOneZ")));
    }

    private String getPayload(String map, String finalTime, String shipName) {
        return "{\"version\":1,\"header\":{\"map\":\"" + map + "\",\"shipModel\":\"" + shipName + "\"},\"stepRecordList\":[{\"position\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"rotation\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"w\":1.0},\"timestamp\":0.13044842,\"eventList\":[]},{\"position\":{\"x\":-267.15237,\"y\":0.0,\"z\":-784.9582},\"rotation\":{\"x\":-0.08990571,\"y\":-0.89595354,\"z\":0.21214685,\"w\":-0.3796915},\"timestamp\":" + finalTime + ",\"eventList\":[\"MILLESTONE_REACHED\"]}]}";
    }
}

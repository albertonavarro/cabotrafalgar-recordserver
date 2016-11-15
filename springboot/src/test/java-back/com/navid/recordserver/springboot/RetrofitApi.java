package com.navid.recordserver.springboot;

import com.navid.recordserverapi.server.model.MapEntry;
import com.navid.recordserverapi.server.model.NewMapEntryRequest;
import com.navid.recordserverapi.server.model.NewMapEntryResponse;
import com.navid.recordserverapi.server.model.RankingEntry;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 * Created by alberto on 14/11/16.
 */
public interface RetrofitApi {

    @POST("/ranking")
    NewMapEntryResponse postRanking(@RequestBody NewMapEntryRequest body);

    @GET("/id/{id}")
    MapEntry getById(@PathVariable("id") String id);

    @GET("/ship/{ship}/maps/{map}")
    RankingEntry getByShipAndMap(@PathVariable("map") String map,  @PathVariable("ship") String ship);
}

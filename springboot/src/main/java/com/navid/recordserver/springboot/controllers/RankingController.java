package com.navid.recordserver.springboot.controllers;

import com.google.common.base.Function;
import com.navid.codegen.recordserver.api.IdApi;
import com.navid.codegen.recordserver.api.RankingApi;
import com.navid.codegen.recordserver.api.ShipApi;
import com.navid.codegen.recordserver.model.*;
import com.navid.trafalgar.recordserver.persistence.CandidateInfo;
import com.navid.trafalgar.recordserver.persistence.CandidateRecord;
import com.navid.trafalgar.recordserver.services.RecordServerService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by alberto on 13/11/16.
 */
@Controller
public class RankingController implements IdApi, ShipApi, RankingApi {

    private static final Logger logger = LoggerFactory.getLogger(RankingController.class);

    @Resource
    private RecordServerService recordServerServices;

    @Override
    public ResponseEntity<MapEntry> getById(
            @PathVariable("id") String id,
            @RequestHeader(value="sessionId") String sessionId,
            @RequestHeader(value="correlationId", required=false) String correlationId) {
        logger.debug("getRankingidid invoked");

        final CandidateRecord result = recordServerServices.getEntryById(id);

        return new ResponseEntity<>(new MapEntry().id(id).payload(result.getPayload()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<NewMapEntryResponse> postRanking(
            @RequestBody NewMapEntryRequest body,
            @RequestHeader(value="sessionId") String sessionId,
            @RequestHeader(value="correlationId", required=false) String correlationId) {

        logger.debug("postRanking invoked");

        final CandidateInfo uploadedCandidate = recordServerServices.addEntry(body.getPayload());

        return new ResponseEntity<>( new NewMapEntryResponse().id(uploadedCandidate.getId()).position(1).status("OK").verified(true), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RankingEntry> getByShipAndMap(
            @PathVariable("map") String map,
            @PathVariable("ship") String ship,
            @RequestHeader(value="sessionId") String sessionId,
            @RequestHeader(value="correlationId", required=false) String correlationId) {

        logger.debug("getByShipAndMap invoked");

        List<CandidateInfo> result = recordServerServices.getEntriesByShipAndMap(map, ship);

        RankingEntry response = new RankingEntry();

        for (CandidateInfo toTransform : result) {
            response.getRankingEntry().add(TRANSFORM_FUNCTION.apply(toTransform));
        }

        return new ResponseEntity<RankingEntry>(response, HttpStatus.OK);
    }

    private static final Function<CandidateInfo, RankingEntryRankingEntry> TRANSFORM_FUNCTION
            = f -> new RankingEntryRankingEntry()
            .position(f.getPosition())
            .time(f.getTime())
            .id(f.getId())
            .username(f.getUserName());


}

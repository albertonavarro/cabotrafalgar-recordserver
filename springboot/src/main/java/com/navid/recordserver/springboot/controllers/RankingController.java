package com.navid.recordserver.springboot.controllers;

import com.google.common.base.Function;
import com.navid.recordserverapi.server.api.IdApi;
import com.navid.recordserverapi.server.api.RankingApi;
import com.navid.recordserverapi.server.api.ShipApi;
import com.navid.recordserverapi.server.model.*;
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
            @ApiParam(value = "TODO",required=true ) @PathVariable("id") String id) {
        logger.debug("getRankingidid invoked");

        final CandidateRecord result = recordServerServices.getEntryById(id);

        return new ResponseEntity<MapEntry>(new MapEntry().id(id).payload(result.getPayload()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RankingEntry> getByShipAndMap(
            @ApiParam(value = "TODO",required=true ) @PathVariable("map") String map,
            @ApiParam(value = "TODO",required=true ) @PathVariable("ship") String ship) {
        logger.debug("getRankingshipshipmapsmap invoked");

        List<CandidateInfo> result = recordServerServices.getEntriesByShipAndMap(map, ship);

        RankingEntry response = new RankingEntry();

        for (CandidateInfo toTransform : result) {
            response.addRankingEntryItem(TRANSFORM_FUNCTION.apply(toTransform));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<NewMapEntryResponse> postRanking(@ApiParam(value = "New ranking request." ,required=true ) @RequestBody NewMapEntryRequest body) {
        logger.debug("postRanking invoked");

        final CandidateInfo uploadedCandidate = recordServerServices.addEntry(body.getPayload());

        NewMapEntryResponse response = new NewMapEntryResponse().id(uploadedCandidate.getId()).position(1).status("OK").verified(true);

        return new ResponseEntity<NewMapEntryResponse>(response, HttpStatus.OK);
    }

    private static final Function<CandidateInfo, RankingEntryRankingEntry> TRANSFORM_FUNCTION
            = new Function<CandidateInfo, RankingEntryRankingEntry>() {
        @Override
        public RankingEntryRankingEntry apply(final CandidateInfo f) {
            return new RankingEntryRankingEntry()
                    .position(f.getPosition())
                    .time(f.getTime())
                    .id(f.getId())
                    .username(f.getUserName());
        }
    };

}

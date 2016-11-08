package com.navid.recordserver.springboot.controller;

import com.google.common.base.Function;
import com.navid.codegen.recordserver.api.IdApi;
import com.navid.codegen.recordserver.api.RankingApi;
import com.navid.codegen.recordserver.api.ShipApi;
import com.navid.codegen.recordserver.model.MapEntry;
import com.navid.codegen.recordserver.model.NewMapEntryRequest;
import com.navid.codegen.recordserver.model.NewMapEntryResponse;
import com.navid.codegen.recordserver.model.RankingEntry;
import com.navid.codegen.recordserver.model.RankingEntryInner;
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

@Controller
public class SwaggerController implements IdApi, RankingApi, ShipApi {

    private static final Logger logger = LoggerFactory.getLogger(SwaggerController.class);

    @Resource
    private RecordServerService recordServerServices;

    @Override
    public ResponseEntity<MapEntry> getById(
            @ApiParam(value = "TODO",required=true ) @PathVariable("id") String id) {
        logger.debug("getRankingidid invoked");

        final CandidateRecord result = recordServerServices.getEntryById(id);

        return new ResponseEntity<>(new MapEntry().id(id).payload(result.getPayload()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<NewMapEntryResponse> postRanking(
            @ApiParam(value = "New ranking request." ,required=true ) @RequestBody NewMapEntryRequest body) {

        logger.debug("postRanking invoked");

        final CandidateInfo uploadedCandidate = recordServerServices.addEntry(body.getPayload());

        return new ResponseEntity<>( new NewMapEntryResponse().id(uploadedCandidate.getId()).position(1).status("OK").verified(true), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RankingEntry> getByShipAndMap(
            @ApiParam(value = "TODO",required=true ) @PathVariable("map") String map,
            @ApiParam(value = "TODO",required=true ) @PathVariable("ship") String ship) {

        logger.debug("getByShipAndMap invoked");

        List<CandidateInfo> result = recordServerServices.getEntriesByShipAndMap(map, ship);

        RankingEntry response = new RankingEntry();

        for (CandidateInfo toTransform : result) {
            response.add(TRANSFORM_FUNCTION.apply(toTransform));
        }

        return new ResponseEntity<RankingEntry>(response, HttpStatus.OK);
    }

    private static final Function<CandidateInfo, RankingEntryInner> TRANSFORM_FUNCTION
            = new Function<CandidateInfo, RankingEntryInner>() {
        @Override
        public RankingEntryInner apply(final CandidateInfo f) {
            return new RankingEntryInner()
                    .position(f.getPosition())
                    .time(f.getTime())
                    .id(f.getId())
                    .username(f.getUserName());
        }
    };


}

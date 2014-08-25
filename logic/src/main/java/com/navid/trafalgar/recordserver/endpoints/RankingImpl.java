/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navid.trafalgar.recordserver.endpoints;

import com.google.common.base.Function;
import com.navid.lazylogin.context.RequestContext;
import com.navid.recordserver.v1.AddRecordRequest;
import com.navid.recordserver.v1.AddRecordResponse;
import com.navid.recordserver.v1.GetMapRecordsResponse;
import com.navid.recordserver.v1.GetRecordResponse;
import com.navid.recordserver.v1.RankingResource;
import com.navid.trafalgar.recordserver.persistence.CandidateRecordUnmarshalled;
import com.navid.trafalgar.recordserver.persistence.Persistence;
import com.navid.trafalgar.recordserver.services.Deserialization;
import java.util.List;
import javax.annotation.Resource;

/**
 *
 * @author anf
 */
public class RankingImpl implements RankingResource {

    @Resource
    private Deserialization service;

    @Resource
    private Persistence persistence;

    private ThreadLocal<RequestContext> request = new ThreadLocal<>();

    @Override
    public AddRecordResponse post(final AddRecordRequest addrecordrequest) {

        CandidateRecordUnmarshalled candidateInfo = service.addCandidate(addrecordrequest.getPayload());

        final CandidateRecordUnmarshalled uploadedCandidate = persistence.addCandidate(candidateInfo);

        return new AddRecordResponse() {
            {
                setId(uploadedCandidate.getId());
                setPosition(1);
                setStatus("OK");
                setVerified(true);
            }
        };
    }

    @Override
    public GetMapRecordsResponse getMapsmap(String map) {
        List<CandidateRecordUnmarshalled> result = persistence.getByMap(map);
        GetMapRecordsResponse response = new GetMapRecordsResponse();

        for (CandidateRecordUnmarshalled toTransform : result) {
            response.getRankingEntry().add(TRANSFORM_FUNCTION.apply(toTransform));
        }

        return response;
    }

    @Override
    public GetRecordResponse getIdid(String id) {
        final CandidateRecordUnmarshalled result = persistence.getById(id);
        
        return new GetRecordResponse(){{
            setId(id);
            setPayload(result.getPayload());
        }};
    }

    @Override
    public GetMapRecordsResponse getUseruser(String user) {
        List<CandidateRecordUnmarshalled> result = persistence.getByUser(user);
        GetMapRecordsResponse response = new GetMapRecordsResponse();

        for (CandidateRecordUnmarshalled toTransform : result) {
            response.getRankingEntry().add(TRANSFORM_FUNCTION.apply(toTransform));
        }

        return response;
    }

    private static Function<CandidateRecordUnmarshalled, GetMapRecordsResponse.RankingEntry> TRANSFORM_FUNCTION = new Function<CandidateRecordUnmarshalled, GetMapRecordsResponse.RankingEntry>() {
        @Override
        public GetMapRecordsResponse.RankingEntry apply(final CandidateRecordUnmarshalled f) {
            return new GetMapRecordsResponse.RankingEntry() {
                {
                    setPosition(f.getPosition());
                    setTime(f.getTime());
                    setId(f.getId());
                }
            };
        }
    };

}

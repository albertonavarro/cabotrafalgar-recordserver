/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navid.trafalgar.recordserver.endpoints;

import com.google.common.base.Function;
import com.navid.recordserver.v1.AddRecordRequest;
import com.navid.recordserver.v1.AddRecordResponse;
import com.navid.recordserver.v1.GetMapRecordsResponse;
import com.navid.recordserver.v1.GetRecordResponse;
import com.navid.recordserver.v1.RankingResource;
import com.navid.trafalgar.recordserver.persistence.CandidateRecordUnmarshalled;
import com.navid.trafalgar.recordserver.persistence.Persistence;
import com.navid.trafalgar.recordserver.services.Deserialization;
import com.navid.trafalgar.recordserver.services.RequestContext;
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

        final CandidateRecordUnmarshalled candidateInfo = service.addCandidate(addrecordrequest.getPayload());
        
        persistence.addCandidate(candidateInfo);
        
        return new AddRecordResponse() {
            {
                setId(candidateInfo.getId());
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
        persistence.getById(id);
        return null;
    }

    @Override
    public GetMapRecordsResponse getUseruser(String user) {
        persistence.getByUser(user);
        return null;
    }
    
    
    private static Function<CandidateRecordUnmarshalled, GetMapRecordsResponse.RankingEntry> TRANSFORM_FUNCTION = new Function<CandidateRecordUnmarshalled, GetMapRecordsResponse.RankingEntry>() {     
        @Override
        public GetMapRecordsResponse.RankingEntry apply(final CandidateRecordUnmarshalled f) {
            return new GetMapRecordsResponse.RankingEntry(){{
                setPosition(f.getPosition());
                setTime(f.getTime());
            }};
        }
    };

}

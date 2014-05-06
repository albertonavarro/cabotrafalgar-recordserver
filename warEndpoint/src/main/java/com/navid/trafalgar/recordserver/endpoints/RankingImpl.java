/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.navid.trafalgar.recordserver.endpoints;

import com.navid.recordserver.v1.AddRecordRequest;
import com.navid.recordserver.v1.AddRecordResponse;
import com.navid.recordserver.v1.GetMapRecordsResponse;
import com.navid.recordserver.v1.GetRecordResponse;
import com.navid.recordserver.v1.RankingResource;
import com.navid.trafalgar.persistence.CandidateInfo;
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

    @Override
    public AddRecordResponse post(final AddRecordRequest addrecordrequest) {
        final CandidateInfo candidateInfo = service.addCandidate(addrecordrequest.getPayload());
        return new AddRecordResponse() {
            {
                setId(addrecordrequest.getId());
                setPosition(candidateInfo.getPosition());
                setStatus("OK");
                setVerified(true);
            }
        };
    }

    @Override
    public GetRecordResponse getMapsmappos(String map, int pos) {
        List<CandidateRecordUnmarshalled> result = persistence.getByMap(map);
        return null;
    }

    @Override
    public GetMapRecordsResponse getMapsmap(String map) {
        List<CandidateRecordUnmarshalled> result = persistence.getByMap(map);
        return null;
    }

}

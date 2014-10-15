package com.navid.trafalgar.recordserver.endpoints;

import com.google.common.base.Function;
import com.navid.lazylogin.context.RequestContext;
import com.navid.lazylogin.context.RequestContextContainer;
import com.navid.recordserver.v1.AddRecordRequest;
import com.navid.recordserver.v1.AddRecordResponse;
import com.navid.recordserver.v1.GetMapRecordsResponse;
import com.navid.recordserver.v1.GetRecordResponse;
import com.navid.recordserver.v1.RankingResource;
import com.navid.trafalgar.recordserver.persistence.CandidateInfo;
import com.navid.trafalgar.recordserver.persistence.CandidateRecord;
import com.navid.trafalgar.recordserver.persistence.Persistence;
import com.navid.trafalgar.recordserver.services.Deserialization;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author anf
 */
public class RankingImpl implements RankingResource {
    
    private static final Logger LOG = LoggerFactory.getLogger(RankingImpl.class);

    @Resource
    private Deserialization service;

    @Resource
    private Persistence persistence;

    @Resource
    private RequestContextContainer requestContextContainer;
    
    @Override
    public AddRecordResponse post(final AddRecordRequest addrecordrequest) {
        
        LOG.info("post requested for user context {}", requestContextContainer.get());

        CandidateRecord candidateInfo = service.addCandidate(addrecordrequest.getPayload());
        
        candidateInfo.setUserName(requestContextContainer.get().getUserName());
        
        final CandidateInfo uploadedCandidate = persistence.addCandidate(candidateInfo);

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
        LOG.info("getMapsmap requested for user context {}", requestContextContainer.get());

        List<CandidateInfo> result = persistence.getByMap(map);
        GetMapRecordsResponse response = new GetMapRecordsResponse();

        for (CandidateInfo toTransform : result) {
            response.getRankingEntry().add(TRANSFORM_FUNCTION.apply(toTransform));
        }

        return response;
    }

    @Override
    public GetRecordResponse getIdid(String id) {
        LOG.info("getIdid requested for user context {}", requestContextContainer.get());

        final CandidateRecord result = persistence.getById(id);
        
        return new GetRecordResponse(){{
            setId(id);
            setPayload(result.getPayload());
        }};
    }

    @Override
    public GetMapRecordsResponse getUseruser(String user) {
        LOG.info("getUseruser requested for user context {}", requestContextContainer.get());

        List<CandidateInfo> result = persistence.getByUser(user);
        GetMapRecordsResponse response = new GetMapRecordsResponse();

        for (CandidateInfo toTransform : result) {
            response.getRankingEntry().add(TRANSFORM_FUNCTION.apply(toTransform));
        }

        return response;
    }

    private static Function<CandidateInfo, GetMapRecordsResponse.RankingEntry> TRANSFORM_FUNCTION = new Function<CandidateInfo, GetMapRecordsResponse.RankingEntry>() {
        @Override
        public GetMapRecordsResponse.RankingEntry apply(final CandidateInfo f) {
            return new GetMapRecordsResponse.RankingEntry() {
                {
                    setPosition(f.getPosition());
                    setTime(f.getTime());
                    setId(f.getId());
                    setUsername(f.getUserName());
                }
            };
        }
    };

    /**
     * @param requestContextContainer the requestContextContainer to set
     */
    public void setRequestContextContainer(RequestContextContainer requestContextContainer) {
        this.requestContextContainer = requestContextContainer;
    }
    
}

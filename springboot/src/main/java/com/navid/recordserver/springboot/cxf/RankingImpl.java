package com.navid.recordserver.springboot.cxf;

import com.google.common.base.Function;
import com.navid.recordserver.v1.AddRecordRequest;
import com.navid.recordserver.v1.AddRecordResponse;
import com.navid.recordserver.v1.GetMapRecordsResponse;
import com.navid.recordserver.v1.GetRecordResponse;
import com.navid.recordserver.v1.RankingResource;
import com.navid.trafalgar.recordserver.persistence.CandidateInfo;
import com.navid.trafalgar.recordserver.persistence.CandidateRecord;
import com.navid.trafalgar.recordserver.services.RecordServerServices;
import java.util.List;
import javax.annotation.Resource;
import javax.jws.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author anf
 */
public class RankingImpl implements RankingResource {
    
    private static final Logger LOG = LoggerFactory.getLogger(RankingImpl.class);

    @Resource
    private RecordServerServices recordServerServices;

    
    @Override
    public AddRecordResponse post(final AddRecordRequest addrecordrequest) {
        LOG.debug("postRanking invoked");

        final CandidateInfo uploadedCandidate = recordServerServices.addEntry(addrecordrequest.getPayload());

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
        LOG.debug("getRankingshipshipmapsmap invoked");

        List<CandidateInfo> result = recordServerServices.getEntriesByMap(map);

        GetMapRecordsResponse response = new GetMapRecordsResponse();

        for (CandidateInfo toTransform : result) {
            response.getRankingEntry().add(TRANSFORM_FUNCTION.apply(toTransform));
        }

        return response;
    }

    @Override
    public GetRecordResponse getIdid(String id) {
        LOG.debug("getRankingidid invoked");

        final CandidateRecord result = recordServerServices.getEntryById(id);
        return new GetRecordResponse() {
            {
                setId(id);
                setPayload(result.getPayload());
            }
        };
    }

    @Override
    public GetMapRecordsResponse getUseruser(String user) {
        LOG.info("getUseruser invoked");

        List<CandidateInfo> result = recordServerServices.getEntriesByUser(user);
        GetMapRecordsResponse response = new GetMapRecordsResponse();

        for (CandidateInfo toTransform : result) {
            response.getRankingEntry().add(TRANSFORM_FUNCTION.apply(toTransform));
        }

        return response;
    }

    private static final Function<CandidateInfo, GetMapRecordsResponse.RankingEntry> TRANSFORM_FUNCTION 
            = new Function<CandidateInfo, GetMapRecordsResponse.RankingEntry>() {
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
    
}

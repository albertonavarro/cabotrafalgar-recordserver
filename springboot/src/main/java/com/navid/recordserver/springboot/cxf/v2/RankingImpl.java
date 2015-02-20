package com.navid.recordserver.springboot.cxf.v2;

import com.google.common.base.Function;
import com.navid.recordserver.v2.AddRecordRequest;
import com.navid.recordserver.v2.AddRecordResponse;
import com.navid.recordserver.v2.GetMapRecordsResponse;
import com.navid.recordserver.v2.GetRecordResponse;
import com.navid.recordserver.v2.V2Resource;
import com.navid.trafalgar.recordserver.persistence.CandidateInfo;
import com.navid.trafalgar.recordserver.persistence.CandidateRecord;
import com.navid.trafalgar.recordserver.services.RecordServerServiceImpl;
import java.util.List;
import javax.annotation.Resource;
import javax.jws.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author anf
 */
public class RankingImpl implements V2Resource {

    private static final Logger LOG = LoggerFactory.getLogger(RankingImpl.class);

    @Resource
    private RecordServerServiceImpl recordServerServices;

    @Override
    public AddRecordResponse postRanking(final AddRecordRequest addrecordrequest) {

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
    public GetMapRecordsResponse getRankingshipshipmapsmap(String map, String ship) {
        LOG.debug("getRankingshipshipmapsmap invoked");

        List<CandidateInfo> result = recordServerServices.getEntriesByShipAndMap(map, ship);

        GetMapRecordsResponse response = new GetMapRecordsResponse();

        for (CandidateInfo toTransform : result) {
            response.getRankingEntry().add(TRANSFORM_FUNCTION.apply(toTransform));
        }

        return response;
    }

    @Override
    public GetRecordResponse getRankingidid(String id) {
        LOG.debug("getRankingidid invoked");

        final CandidateRecord result = recordServerServices.getEntryById(id);
        return new GetRecordResponse() {
            {
                setId(id);
                setPayload(result.getPayload());
            }
        };
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

package com.navid.trafalgar.recordserver.endpoints;

import com.google.common.base.Function;
import com.lazylogin.client.system.v0.GetUserInfoError_Exception;
import com.lazylogin.client.system.v0.SystemCommands;
import com.lazylogin.client.system.v0.UserInfo;
import com.navid.lazylogin.context.RequestContextContainer;
import com.navid.recordserver.v1.AddRecordRequest;
import com.navid.recordserver.v1.AddRecordResponse;
import com.navid.recordserver.v1.GetMapRecordsResponse;
import com.navid.recordserver.v1.GetRecordResponse;
import com.navid.recordserver.v1.RankingResource;
import com.navid.trafalgar.recordserver.persistence.CandidateInfo;
import com.navid.trafalgar.recordserver.persistence.CandidateRecord;
import com.navid.trafalgar.recordserver.persistence.ItemNotFoundException;
import com.navid.trafalgar.recordserver.persistence.Persistence;
import com.navid.trafalgar.recordserver.services.Deserialization;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.Resource;
import javax.ws.rs.NotFoundException;
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
    
    @Resource(name = "client")
    private SystemCommands systemCommands;
    
    @Override
    public AddRecordResponse post(final AddRecordRequest addrecordrequest) {
        
        LOG.info("post requested for user context {}", requestContextContainer.get());

        CandidateRecord candidateInfo = service.addCandidate(addrecordrequest.getPayload());
        
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
            if(toTransform.getUserSession() ==null ){
                LOG.info("Removing entry {} due to lack of userSession", toTransform.getId());
                persistence.remove(toTransform);
                continue;
            }
            
            /*if(!toTransform.getLoginVerified() && toTransform.getUserName() != null){
                LOG.info("Enhancing entry {} with verified login", toTransform.getId());
                toTransform.setLoginVerified(true);
                persistence.update(toTransform);
            }*/
            
            if(!toTransform.getLoginVerified()) {
                try {
                   UserInfo userInfo = systemCommands.getUserInfo(toTransform.getUserSession());
                   if(userInfo.isVerified()){
                       toTransform.setUserName(userInfo.getUsername());
                       toTransform.setGameVerified(true);
                       persistence.update(toTransform);
                   } else if (toTransform.getUserSession().equals(requestContextContainer.get().getSessionId())) {
                       toTransform.setUserName("--yourself--");
                   } else {
                       continue;
                   }
                } catch (GetUserInfoError_Exception ex) {
                    LOG.error("Error retrieving user info from record {} and user {}", toTransform.getId(), toTransform.getUserSession());
                    continue;
                }
            }
            
            response.getRankingEntry().add(TRANSFORM_FUNCTION.apply(toTransform));
        }

        return response;
    }

    @Override
    public GetRecordResponse getIdid(String id) {
        LOG.info("getIdid requested for user context {}", requestContextContainer.get());

        try{         
            final CandidateRecord result = persistence.getById(id);

            return new GetRecordResponse(){{
                setId(id);
                setPayload(result.getPayload());
            }};
        } catch(ItemNotFoundException e) {
            throw new NotFoundException(id);
        }
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

    /**
     * @param SystemCommands the SystemCommands to set
     */
    public void setSystemCommands(SystemCommands SystemCommands) {
        this.systemCommands = SystemCommands;
    }
    
    
}

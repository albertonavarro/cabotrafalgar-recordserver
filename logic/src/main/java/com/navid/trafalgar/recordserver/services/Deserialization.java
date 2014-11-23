package com.navid.trafalgar.recordserver.services;

import com.google.gson.Gson;
import com.navid.lazylogin.context.RequestContext;
import com.navid.lazylogin.context.RequestContextContainer;
import com.navid.trafalgar.model.AShipModelTwo;
import com.navid.trafalgar.persistence.StepRecord;
import com.navid.trafalgar.recordserver.persistence.CandidateRecord;
import static java.lang.Boolean.FALSE;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author anf
 */
@Service
public class Deserialization {
    
    private final Gson gson = new Gson();
    
    @Resource
    private RequestContextContainer requestContextContainer;
    

    public CandidateRecord addCandidate(String candidateRecord) {
        
        RequestContext requestContext = requestContextContainer.get();
        
        com.navid.trafalgar.persistence.CandidateRecord<? extends StepRecord> record = gson.fromJson(candidateRecord, AShipModelTwo.ShipCandidateRecord.class);
        
        CandidateRecord cdu = new CandidateRecord();
        cdu.setMapName(record.getHeader().getMap());
        cdu.setPayload(candidateRecord);
        cdu.setTimestamp(new Date());
        cdu.setUserName(requestContextContainer.get().getUserName());
        cdu.setGameVerified(FALSE);
        cdu.setLoginVerified(FALSE);
        cdu.setTime(record.getTime());
        cdu.setUserSession(requestContextContainer.get().getSessionId());
        cdu.setShipName(record.getHeader().getShipModel());
        return cdu;
    }

}

package com.navid.trafalgar.recordserver.services;

import com.google.gson.Gson;
import com.navid.lazylogin.context.RequestContext;
import com.navid.lazylogin.context.RequestContextContainer;
import com.navid.trafalgar.model.AShipModelTwo;
import com.navid.trafalgar.recordserver.persistence.CandidateRecordUnmarshalled;
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
    

    public CandidateRecordUnmarshalled addCandidate(String candidateRecord) {
        
        RequestContext requestContext = requestContextContainer.get();
        
        AShipModelTwo.ShipCandidateRecord record = gson.fromJson(candidateRecord, AShipModelTwo.ShipCandidateRecord.class);
        
        CandidateRecordUnmarshalled cdu = new CandidateRecordUnmarshalled();
        cdu.setMapName(record.getHeader().getMap());
        cdu.setPayload(candidateRecord);
        cdu.setTimestamp(new Date());
        cdu.setUserId(requestContextContainer.get().getUserName());
        cdu.setGameVerified(FALSE);
        cdu.setLoginVerified(FALSE);
        cdu.setTime(record.getTime());
        cdu.setUserToken("token");
        
        return cdu;
    }

}

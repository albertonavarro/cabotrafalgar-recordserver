package com.navid.trafalgar.recordserver.services;

import com.google.gson.Gson;
import com.navid.trafalgar.model.AShipModelTwo;
import com.navid.trafalgar.persistence.CandidateInfo;
import com.navid.trafalgar.persistence.CandidateRecord;
import com.navid.trafalgar.persistence.CompetitorInfo;
import com.navid.trafalgar.recordserver.persistence.CandidateRecordUnmarshalled;
import com.navid.trafalgar.recordserver.persistence.Persistence;
import static java.lang.Boolean.FALSE;
import java.util.Date;
import java.util.List;
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
    private Persistence persistence;

    public CandidateInfo addCandidate(String candidateRecord) {
        
        AShipModelTwo.ShipCandidateRecord record = gson.fromJson(candidateRecord, AShipModelTwo.ShipCandidateRecord.class);
        
        CandidateRecordUnmarshalled cdu = new CandidateRecordUnmarshalled();
        cdu.setMapName(record.getHeader().getMap());
        cdu.setPayload(candidateRecord);
        cdu.setTimestamp(new Date());
        cdu.setUserId("user");
        cdu.setGameVerified(FALSE);
        cdu.setLoginVerified(FALSE);
        cdu.setTime(record.getTime());
        cdu.setUserToken("token");
        
        persistence.addCandidate(cdu);
        
        CandidateInfo candidateInfo = new CandidateInfo();
        candidateInfo.setAccepted(true);
        candidateInfo.setPosition(1);
        
        return candidateInfo;
    }

    public List<CompetitorInfo> getTopCompetitors(int number, String map) {
        return null;
    }

    public CandidateRecord getGhost(int number, String map) {
        return null;
    }

}

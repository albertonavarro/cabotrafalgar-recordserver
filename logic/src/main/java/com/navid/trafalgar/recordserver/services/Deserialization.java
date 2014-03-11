package com.navid.trafalgar.recordserver.services;

import com.google.gson.Gson;
import com.navid.trafalgar.model.AShipModelTwo;
import com.navid.trafalgar.persistence.CandidateInfo;
import com.navid.trafalgar.persistence.CandidateRecord;
import com.navid.trafalgar.persistence.CompetitorInfo;
import com.navid.trafalgar.recordserver.persistence.Persistence;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author anf
 */
@Service
public class Deserialization {
    
    Gson gson = new Gson();
    
    @Resource
    private Persistence persistence;

    public CandidateInfo addCandidate(String candidateRecord) {
        
        AShipModelTwo.ShipCandidateRecord record = gson.fromJson(candidateRecord, AShipModelTwo.ShipCandidateRecord.class);
        
        persistence.addCandidate(record);
        
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

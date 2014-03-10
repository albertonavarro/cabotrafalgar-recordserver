/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.navid.trafalgar.recordserver.services;

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
    
    @Resource
    private Persistence persistence;
    
    public CandidateInfo addCandidate(String candidateRecord) {
      
    return null;
    }

    public List<CompetitorInfo> getTopCompetitors(int number, String map) {
        return null;
    }

    public CandidateRecord getGhost(int number, String map) {
        return null;
    }
    
}

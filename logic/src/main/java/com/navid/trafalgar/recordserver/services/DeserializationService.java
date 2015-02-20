package com.navid.trafalgar.recordserver.services;

import com.navid.trafalgar.recordserver.persistence.CandidateRecord;


public interface DeserializationService {

    CandidateRecord addCandidate(String candidateRecord);
    
}

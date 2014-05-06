package com.navid.trafalgar.recordserver.persistence;

import com.navid.trafalgar.persistence.CandidateRecord;

/**
 *
 * @author alberto
 */
public interface Persistence {

    void addCandidate(CandidateRecord candidateRecord);

}

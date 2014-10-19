package com.navid.trafalgar.recordserver.persistence;

import java.util.List;

/**
 *
 * @author alberto
 */
public interface Persistence {

    CandidateInfo addCandidate(CandidateRecord candidateRecord);

    List<CandidateInfo>  getByMap(String map);

    List<CandidateInfo> getByUser(String user);

    CandidateRecord getById(String id);

    void remove(CandidateInfo toRemove);
    
    void update(CandidateInfo toUpdate);

}

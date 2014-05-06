package com.navid.trafalgar.recordserver.persistence;

import java.util.List;

/**
 *
 * @author alberto
 */
public interface Persistence {

    void addCandidate(CandidateRecordUnmarshalled candidateRecord);

    List<CandidateRecordUnmarshalled>  getByMap(String map);

}

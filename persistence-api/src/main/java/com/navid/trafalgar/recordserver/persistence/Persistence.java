package com.navid.trafalgar.recordserver.persistence;

import java.util.List;

/**
 *
 * @author alberto
 */
public interface Persistence {

    CandidateRecordUnmarshalled addCandidate(CandidateRecordUnmarshalled candidateRecord);

    List<CandidateRecordUnmarshalled>  getByMap(String map);

    List<CandidateRecordUnmarshalled> getByUser(String user);

    CandidateRecordUnmarshalled getById(String id);

}

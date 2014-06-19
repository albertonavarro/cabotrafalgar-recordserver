package com.navid.trafalgar.recordserver.persistence;

import java.util.List;

/**
 *
 * @author alberto
 */
public interface Persistence {

    void addCandidate(CandidateRecordUnmarshalled candidateRecord);

    List<CandidateRecordUnmarshalled>  getByMap(String map);

    public void getByUser(String user);

    public void getById(String id);

}

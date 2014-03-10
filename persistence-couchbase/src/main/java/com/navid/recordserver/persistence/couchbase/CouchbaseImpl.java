package com.navid.recordserver.persistence.couchbase;

import com.navid.trafalgar.persistence.CandidateRecord;
import com.navid.trafalgar.recordserver.persistence.Persistence;
import org.springframework.stereotype.Repository;

/**
 *
 * @author anf
 */
@Repository
public class CouchbaseImpl implements Persistence {

    @Override
    public void addCandidate(CandidateRecord candidateRecord) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

package com.navid.trafalgar.recordserver.persistence.couchbase;

import com.navid.trafalgar.persistence.CandidateRecord;
import com.navid.trafalgar.recordserver.persistence.Persistence;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author anf
 */
@Repository
public class CouchbaseImpl implements Persistence {
    
    @Resource
    private CDBCandidateRecordRepository repository;

    @Override
    public void addCandidate(CandidateRecord candidateRecord) {
        CDBCandidateRecord cdb = new CDBCandidateRecord();
        repository.add(cdb);
    }
    
}

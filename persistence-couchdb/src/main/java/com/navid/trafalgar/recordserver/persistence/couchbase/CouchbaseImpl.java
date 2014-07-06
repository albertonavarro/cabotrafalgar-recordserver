package com.navid.trafalgar.recordserver.persistence.couchbase;

import com.navid.trafalgar.recordserver.persistence.CandidateRecordUnmarshalled;
import com.navid.trafalgar.recordserver.persistence.Persistence;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class CouchbaseImpl implements Persistence {
    
    CandidateRecordUnmarshalledMapper mapper = CandidateRecordUnmarshalledMapper.INSTANCE;
    
    @Resource
    private CDBCandidateRecordRepository repository;

    @Override
    public void addCandidate(CandidateRecordUnmarshalled candidateRecord) {
        CDBCandidateRecord cdb = mapper.toDto(candidateRecord);
        
        repository.add(cdb);
    }

    @Override
    public List<CandidateRecordUnmarshalled> getByMap(String map) {
        List<CDBCandidateRecord> result = repository.findByMapName(map);
        
        return mapper.fromDto(result);
    }

    public void setRepository(CDBCandidateRecordRepository dbRepresentation) {
        this.repository = dbRepresentation;
    }

    @Override
    public List<CandidateRecordUnmarshalled> getByUser(String user) {
        List<CDBCandidateRecord> result = repository.findByUser(user);
        
        return mapper.fromDto(result);  
    }

    @Override
    public List<CandidateRecordUnmarshalled> getById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

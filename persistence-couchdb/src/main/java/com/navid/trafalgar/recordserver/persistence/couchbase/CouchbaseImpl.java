package com.navid.trafalgar.recordserver.persistence.couchbase;

import com.navid.trafalgar.recordserver.persistence.CandidateRecordUnmarshalled;
import com.navid.trafalgar.recordserver.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.jdto.DTOBinder;
import org.jdto.DTOBinderFactory;
import org.springframework.stereotype.Repository;

@Repository
public class CouchbaseImpl implements Persistence {
    
    DTOBinder binder = DTOBinderFactory.getBinder();

    @Resource
    private CDBCandidateRecordRepository repository;

    @Override
    public void addCandidate(CandidateRecordUnmarshalled candidateRecord) {
        CDBCandidateRecord cdb = binder.bindFromBusinessObject(CDBCandidateRecord.class, candidateRecord);
        
        repository.add(cdb);
    }

    @Override
    public List<CandidateRecordUnmarshalled> getByMap(String map) {
        List<CDBCandidateRecord> result = repository.findByMapName(map);
        
        List dtos = binder.bindFromBusinessObjectCollection(CandidateRecordUnmarshalled.class, result);
        return dtos;
    }

}

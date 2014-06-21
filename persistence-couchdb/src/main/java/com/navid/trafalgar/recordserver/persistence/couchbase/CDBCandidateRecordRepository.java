/*
 */
package com.navid.trafalgar.recordserver.persistence.couchbase;

import java.util.List;
import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component(value = "recordserver.cdbrepository")
public class CDBCandidateRecordRepository extends CouchDbRepositorySupport<CDBCandidateRecord> {

    @Autowired
    public CDBCandidateRecordRepository(@Qualifier("recordServerConnection") CouchDbConnector db) {
        super(CDBCandidateRecord.class, db);
        initStandardDesignDocument();
    }

    @Override
    @View(name = "all", map = "function(doc) { if(doc.timestamp) {emit(null, doc._id)} }")
    public List<CDBCandidateRecord> getAll() {
        return queryView("all");
    }

    @View(name = "find_better_for_map", map = "function(doc) { if(doc.time && doc.mapName) {emit([doc.mapName, doc.time], doc._id)} }")
    public List<CDBCandidateRecord> findByMapName(String map) {
        ComplexKey start = ComplexKey.of(map);
        ComplexKey end = ComplexKey.of(map, ComplexKey.emptyObject());
        
        ViewQuery q = createQuery("find_better_for_map").startKey(start).endKey(end).limit(5).includeDocs(true);
        List<CDBCandidateRecord> results = db.queryView(q , CDBCandidateRecord.class);
        
        addPositions(results);
        
        return results;
    }
    
    @View(name = "find_by_user", map = "function(doc) { if(doc.userId && doc.mapName) {emit([doc.userId, doc.mapName], doc._id)} }")
    public List<CDBCandidateRecord> findByUser(String userName) {
        ComplexKey start = ComplexKey.of(userName);
        ComplexKey end = ComplexKey.of(userName, ComplexKey.emptyObject());
        
        ViewQuery q = createQuery("find_by_user").startKey(start).endKey(end).limit(5).includeDocs(true);
        List<CDBCandidateRecord> results = db.queryView(q , CDBCandidateRecord.class);
                
        return results;
    }

    private void addPositions(List<CDBCandidateRecord> results) {
        int index = 1;
        for(CDBCandidateRecord currentRecord : results) {
            currentRecord.setPosition(index++);
        }
    }

}

/*
 */
package com.navid.trafalgar.recordserver.persistence.couchbase;

import java.util.List;
import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.ektorp.support.Views;
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
        return db.queryView(q , CDBCandidateRecord.class);
    }

}

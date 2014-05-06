/*
 */
package com.navid.trafalgar.recordserver.persistence.couchbase;

import java.util.List;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CDBCandidateRecordRepository extends CouchDbRepositorySupport<CDBCandidateRecord> {

    @Autowired
    public CDBCandidateRecordRepository(@Qualifier("recordServerConnection") CouchDbConnector db) {
        super(CDBCandidateRecord.class, db);
        initStandardDesignDocument();
    }

    @GenerateView
    @Override
    public List<CDBCandidateRecord> getAll() {
        ViewQuery q = createQuery("all").descending(true);
        return db.queryView(q, CDBCandidateRecord.class);
    }

    @GenerateView
    public List<CDBCandidateRecord> findByMapName(String map) {
        return queryView("by_mapName", map);
    }

}

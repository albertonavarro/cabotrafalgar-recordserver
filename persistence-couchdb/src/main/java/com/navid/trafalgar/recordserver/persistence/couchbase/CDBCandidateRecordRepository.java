/*
 */
package com.navid.trafalgar.recordserver.persistence.couchbase;

import com.navid.trafalgar.recordserver.persistence.UsersReport;
import org.apache.commons.io.IOUtils;
import org.ektorp.*;
import org.ektorp.ViewResult.Row;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.UpdateHandler;
import org.ektorp.support.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component(value = "recordserver.cdbrepository")
public class CDBCandidateRecordRepository extends CouchDbRepositorySupport<CDBCandidateRecord> {
    
    private final static Logger LOG = LoggerFactory.getLogger(CDBCandidateRecordRepository.class);
    
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
        List<CDBCandidateRecord> results = db.queryView(q, CDBCandidateRecord.class);
        
        addPositions(results);
        
        return results;
    }
    
    @View(name = "find_better_for_ship_and_map", map = "function(doc) { if(doc.time && doc.mapName && doc.shipName) {emit([doc.shipName, doc.mapName, doc.time], doc._id)} }")
    public List<CDBCandidateRecord> findByShipAndMapName(String map, String ship) {
        ComplexKey start = ComplexKey.of(ship, map);
        ComplexKey end = ComplexKey.of(ship, map, ComplexKey.emptyObject());
        
        ViewQuery q = createQuery("find_better_for_ship_and_map").startKey(start).endKey(end).limit(5).includeDocs(true);
        List<CDBCandidateRecord> results = db.queryView(q, CDBCandidateRecord.class);
        
        addPositions(results);
        
        return results;
    }
    
    @View(name = "find_by_user", map = "function(doc) { if(doc.userId && doc.mapName) {emit([doc.userId, doc.mapName], doc._id)} }")
    public List<CDBCandidateRecord> findByUser(String userName) {
        ComplexKey start = ComplexKey.of(userName);
        ComplexKey end = ComplexKey.of(userName, ComplexKey.emptyObject());
        
        ViewQuery q = createQuery("find_by_user").startKey(start).endKey(end).limit(5).includeDocs(true);
        List<CDBCandidateRecord> results = db.queryView(q, CDBCandidateRecord.class);
        
        return results;
    }
    
    @View(name="find_unique_users", map = "function(doc) {emit(doc.userName, 1);}", reduce = "function (key, values, rereduce) {return sum(values);}")
    public List<UsersReport> getUsersReport() {
        
        LOG.debug("Invoking getUsersReport");
        List<UsersReport> report = new ArrayList<>();
        ViewQuery query = createQuery("find_unique_users").group(true).limit(5);
        LOG.debug("Query: {}", query);
        ViewResult viewResult = db.queryView(query);
        LOG.debug("Query result: {}", viewResult);
        for (Row row : viewResult.getRows()){
            LOG.debug("Row: {}", row);
            UsersReport usersReport = new UsersReport();
            usersReport.setGames(row.getValueAsInt());
            usersReport.setUserName(row.getKey());
        }
        return report;
    }
    
    @UpdateHandler(name = "set_login_verified", function = "function(doc, req) { doc.loginVerified = true; return [doc, 'true']; }")
    public void updateLoginVerified(String id) {
        LOG.info("updating id {} to set login verified", id);
        String result = db.callUpdateHandler(stdDesignDocumentId, "set_login_verified", id);
        LOG.info("result of updating id {} is {}", id, result);
    }
    
    private void addPositions(List<CDBCandidateRecord> results) {
        int index = 1;
        for (CDBCandidateRecord currentRecord : results) {
            currentRecord.setPosition(index++);
        }
    }
    
    public void addAttachment(String id, String rev, String data, String attName) {
        AttachmentInputStream a = new AttachmentInputStream(attName,
                new ByteArrayInputStream(data.getBytes()),
                "text/json");
        
        db.createAttachment(id, rev, a);
    }

    String getAttachment(String id, String attName, String revision) {
        try(AttachmentInputStream attachment = db.getAttachment(id, attName, revision)){
            return IOUtils.toString(attachment);
        }catch (IOException ex) {
            LOG.error("Error getting attachment", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    
}

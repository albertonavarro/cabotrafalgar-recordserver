/*
 */

package com.navid.trafalgar.recordserver.persistence.couchbase;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class CDBCandidateRecordRepository extends CouchDbRepositorySupport<CDBCandidateRecord> {
    
    public @Value("#{couchdbProperties['host']?:'localhost'}")                              String host;
        public @Value("#{couchdbProperties['port']?:5984}")                                             int port;
        public @Value("#{couchdbProperties['maxConnections']?:20}")                     int maxConnections;
        public @Value("#{couchdbProperties['connectionTimeout']?:1000}")                int connectionTimeout;
        public @Value("#{couchdbProperties['socketTimeout']?:10000}")                   int socketTimeout;
        public @Value("#{couchdbProperties['autoUpdateViewOnChange']?:false}")  boolean autoUpdateViewOnChange;
        public @Value("#{couchdbProperties['username']}")                                               String username;
        public @Value("#{couchdbProperties['password']}")                                               String password;
        public @Value("#{couchdbProperties['testConnectionAtStartup']?:false}") boolean testConnectionAtStartup;
        public @Value("#{couchdbProperties['cleanupIdleConnections']?:true}")   boolean cleanupIdleConnections;
        public @Value("#{couchdbProperties['enableSSL']?:false}")                               boolean enableSSL;
        public @Value("#{couchdbProperties['relaxedSSLSettings']?:false}")              boolean relaxedSSLSettings;

    
    @Autowired
    public CDBCandidateRecordRepository (@Qualifier("recordServerConnection") CouchDbConnector db) {
                super(CDBCandidateRecord.class, db);
                initStandardDesignDocument();
    }

}

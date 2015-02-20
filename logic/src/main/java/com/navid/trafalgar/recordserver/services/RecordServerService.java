package com.navid.trafalgar.recordserver.services;

import com.navid.trafalgar.recordserver.persistence.CandidateInfo;
import com.navid.trafalgar.recordserver.persistence.CandidateRecord;
import com.navid.trafalgar.recordserver.persistence.UsersReport;
import java.util.List;


public interface RecordServerService {

    CandidateInfo addEntry(final String payload);

    @Deprecated
    List<CandidateInfo> getEntriesByMap(String map);

    List<CandidateInfo> getEntriesByShipAndMap(String map, String ship);

    @Deprecated
    List<CandidateInfo> getEntriesByUser(String user);

    CandidateRecord getEntryById(String id);

    List<UsersReport> getUsersReport();
    
}

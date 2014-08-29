package com.navid.trafalgar.recordserver.persistence.couchbase;

import com.navid.trafalgar.recordserver.persistence.CandidateRecord;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author casa
 */
@Mapper
public interface CandidateRecordMapper {

    CandidateRecordMapper INSTANCE = Mappers.getMapper(CandidateRecordMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "attachment", ignore = true),
            @Mapping(target = "revisions", ignore = true),
            @Mapping(target = "new", ignore = true)})
    CDBCandidateRecord toDto(CandidateRecord business);
    
    @Mappings({
        @Mapping(target = "id", source = "id")
    })
    CandidateRecord fromDto(CDBCandidateRecord dto);
    
    @Mappings({
        @Mapping(target = "id", source = "id")
    })
    List<CandidateRecord> fromDto(List<CDBCandidateRecord> dto);
}

package com.navid.trafalgar.recordserver.persistence.couchbase;

import com.navid.trafalgar.recordserver.persistence.CandidateRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 *
 * @author casa
 */
@Mapper
public interface CandidateRecordMapper {

    CandidateRecordMapper INSTANCE = Mappers.getMapper(CandidateRecordMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "attachments", ignore = true),
            @Mapping(target = "revision", ignore = true)})
    CDBCandidateRecord toDto(CandidateRecord business);
    
    @Mappings({
    })
    CandidateRecord fromDto(CDBCandidateRecord dto);
    
    @Mappings({
    })
    List<CandidateRecord> fromDto(List<CDBCandidateRecord> dto);
}

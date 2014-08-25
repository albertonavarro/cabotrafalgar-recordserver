package com.navid.trafalgar.recordserver.persistence.couchbase;

import com.navid.trafalgar.recordserver.persistence.CandidateRecordUnmarshalled;
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
public interface CandidateRecordUnmarshalledMapper {

    CandidateRecordUnmarshalledMapper INSTANCE = Mappers.getMapper(CandidateRecordUnmarshalledMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "attachment", ignore = true),
            @Mapping(target = "revisions", ignore = true),
            @Mapping(target = "new", ignore = true)})
    CDBCandidateRecord toDto(CandidateRecordUnmarshalled business);
    
    @Mappings({
        @Mapping(target = "id", source = "id")
    })
    CandidateRecordUnmarshalled fromDto(CDBCandidateRecord dto);
    
    @Mappings({
        @Mapping(target = "id", source = "id")
    })
    List<CandidateRecordUnmarshalled> fromDto(List<CDBCandidateRecord> dto);
}

package com.navid.trafalgar.recordserver.persistence.couchbase;

import com.navid.trafalgar.recordserver.persistence.CandidateInfo;
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
public interface CandidateInfoMapper {

    CandidateInfoMapper INSTANCE = Mappers.getMapper(CandidateInfoMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "attachment", ignore = true),
            @Mapping(target = "revisions", ignore = true),
            @Mapping(target = "new", ignore = true)})
    CDBCandidateRecord toDtoNoId(CandidateInfo business);
    
    @Mappings({
            @Mapping(target = "attachment", ignore = true),
            @Mapping(target = "new", ignore = true)})
    CDBCandidateRecord toDtoWithId(CandidateInfo business);
    
    @Mappings({
        @Mapping(target = "id", source = "id")

    })
    CandidateInfo fromDto(CDBCandidateRecord dto);
    
    @Mappings({
        @Mapping(target = "id", source = "id")
    })
    List<CandidateInfo> fromDto(List<CDBCandidateRecord> dto);
}

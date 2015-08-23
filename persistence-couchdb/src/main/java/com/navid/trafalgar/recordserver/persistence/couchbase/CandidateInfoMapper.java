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
            @Mapping(target = "attachments", ignore = true),
            @Mapping(target = "revision", ignore = true)})
    CDBCandidateRecord toDtoNoId(CandidateInfo business);
    
    @Mappings({
            @Mapping(target = "attachments", ignore = true)})
    CDBCandidateRecord toDtoWithId(CandidateInfo business);
    
    @Mappings({
    })
    CandidateInfo fromDto(CDBCandidateRecord dto);
    
    @Mappings({
    })
    List<CandidateInfo> fromDto(List<CDBCandidateRecord> dto);
}

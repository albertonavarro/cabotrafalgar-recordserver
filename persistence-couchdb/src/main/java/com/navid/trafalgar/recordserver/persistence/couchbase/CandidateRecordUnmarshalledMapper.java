/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    @Mapping(source = "id", ignore = false)
    CandidateRecordUnmarshalled fromDto(CDBCandidateRecord dto);
    List<CandidateRecordUnmarshalled> fromDto(List<CDBCandidateRecord> dto);
}

package com.navid.trafalgar.recordserver.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.navid.lazylogin.context.RequestContext;
import com.navid.lazylogin.context.RequestContextContainer;
import com.navid.trafalgar.maploader.v3.EntryDefinition;
import com.navid.trafalgar.model.ModelBuilder;
import com.navid.trafalgar.model.Role;
import com.navid.trafalgar.recordserver.persistence.CandidateRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import static java.lang.Boolean.FALSE;

/**
 *
 * @author anf
 */
@Service
public class DeserializationServiceImpl implements DeserializationService {

    private static final Logger LOG = LoggerFactory.getLogger(DeserializationServiceImpl.class);

    private final Gson gson = new Gson();

    @Resource
    private RequestContextContainer requestContextContainer;

    @Resource
    private ModelBuilder builder;

    @Override
    public CandidateRecord addCandidate(String candidateRecord) {

            ObjectMapper om = new ObjectMapper();

            RequestContext requestContext = requestContextContainer.get();

            final HeaderCandidateRecord header = gson.fromJson(candidateRecord, HeaderCandidateRecord.class);

            EntryDefinition entry = new EntryDefinition();
            entry.setType(header.getHeader().getShipModel());
            entry.setValues(new HashMap(){{
                    put("role", "CandidateRecord");
                }});

            Collection<? extends com.navid.trafalgar.model.CandidateRecord> classy = builder.build(entry, Role.candidateRecord);

            com.navid.trafalgar.model.CandidateRecord record = gson.fromJson(candidateRecord, Iterables.getFirst(classy, null).getClass());

            CandidateRecord cdu = new CandidateRecord();
            cdu.setMapName(record.getHeader().getMap());
            cdu.setPayload(candidateRecord);
            cdu.setTimestamp(new Date());
            cdu.setUserName(requestContext.getUserName());
            cdu.setGameVerified(FALSE);
            cdu.setLoginVerified(FALSE);
            cdu.setTime(record.getTime());
            cdu.setUserSession(requestContext.getSessionId());
            cdu.setTokenHash(requestContext.getTokenHash());
            cdu.setShipName(record.getHeader().getShipModel());
            return cdu;
    }

    public static class HeaderCandidateRecord extends com.navid.trafalgar.model.CandidateRecord<HeaderStepRecord> {

    }

    public static class HeaderStepRecord extends com.navid.trafalgar.model.StepRecord {

    }

}

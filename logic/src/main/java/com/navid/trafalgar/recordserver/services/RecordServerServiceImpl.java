package com.navid.trafalgar.recordserver.services;

import static com.google.common.collect.Lists.newArrayList;
import com.lazylogin.client.system.v0.GetUserInfoError_Exception;
import com.lazylogin.client.system.v0.SystemCommands;
import com.lazylogin.client.system.v0.UserInfo;
import com.navid.lazylogin.context.RequestContextContainer;
import com.navid.trafalgar.recordserver.persistence.CandidateInfo;
import com.navid.trafalgar.recordserver.persistence.CandidateRecord;
import com.navid.trafalgar.recordserver.persistence.ItemNotFoundException;
import com.navid.trafalgar.recordserver.persistence.Persistence;
import com.navid.trafalgar.recordserver.persistence.UsersReport;
import java.util.List;
import javax.annotation.Resource;
import javax.ws.rs.NotFoundException;
import javax.xml.ws.soap.SOAPFaultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public final class RecordServerServiceImpl implements RecordServerService {

    private static final Logger LOG = LoggerFactory.getLogger(RecordServerServiceImpl.class);

    @Resource
    private DeserializationService deserializator;

    @Resource
    private Persistence persistence;

    @Resource
    private RequestContextContainer requestContextContainer;

    @Resource(name = "client")
    private SystemCommands systemCommands;

    @Override
    public CandidateInfo addEntry(final String payload) {

        LOG.info("post requested for user context {}", requestContextContainer.get());

        CandidateRecord candidateInfo;
        try {
            candidateInfo = deserializator.addCandidate(payload);
        } catch (Exception e) {
            LOG.error("Error deserializing payload: ", e);
            throw e;
        }

        return persistence.addCandidate(candidateInfo);
    }

    @Override
    public List<CandidateInfo> getEntriesByShipAndMap(String map, String ship) {
        LOG.info("getEntryByShipAndMap requested for map {} and ship {}", map, ship);

        List<CandidateInfo> preResult = persistence.getByMapAndShip(map, ship);
        List<CandidateInfo> result = newArrayList();

        for (CandidateInfo toTransform : preResult) {
            if (toTransform.getUserSession() == null) {
                LOG.info("Removing entry {} due to lack of userSession", toTransform.getId());
                persistence.remove(toTransform);
                continue;
            }

            if (!toTransform.getLoginVerified() && toTransform.getUserName() != null) {
                LOG.info("Enhancing entry {} with verified login", toTransform.getId());
                toTransform.setLoginVerified(true);
                persistence.update(toTransform);
            }

            if (!toTransform.getLoginVerified()) {
                try {
                    UserInfo userInfo = systemCommands.getUserInfo(toTransform.getUserSession());
                    if (userInfo.isVerified()) {
                        toTransform.setUserName(userInfo.getUsername());
                        toTransform.setGameVerified(true);
                        persistence.update(toTransform);
                    } else if (toTransform.getUserSession().equals(requestContextContainer.get().getSessionId())) {
                        toTransform.setUserName("--yourself--");
                    } else {
                        continue;
                    }
                } catch (GetUserInfoError_Exception | SOAPFaultException ex) {
                    LOG.error("Error retrieving user info from record {} and user {}", toTransform.getId(), toTransform.getUserSession());
                    continue;
                }
            }
            result.add(toTransform);
        }

        return result;
    }

    @Override
    public CandidateRecord getEntryById(String id) {
        LOG.info("getIdid requested for id {}", id);

        try {
            return persistence.getById(id);

        } catch (ItemNotFoundException e) {
            throw new NotFoundException(id);
        }
    }

    @Deprecated
    @Override
    public List<CandidateInfo> getEntriesByMap(String map) {
        LOG.info("getEntryByShip requested for map {}", map);

        List<CandidateInfo> preResult = persistence.getByMap(map);
        List<CandidateInfo> result = newArrayList();

        for (CandidateInfo toTransform : preResult) {
            if (toTransform.getUserSession() == null) {
                LOG.info("Removing entry {} due to lack of userSession", toTransform.getId());
                persistence.remove(toTransform);
                continue;
            }

            if (!toTransform.getLoginVerified() && toTransform.getUserName() != null) {
                LOG.info("Enhancing entry {} with verified login", toTransform.getId());
                toTransform.setLoginVerified(true);
                persistence.update(toTransform);
            }

            if (!toTransform.getLoginVerified()) {
                try {
                    UserInfo userInfo = systemCommands.getUserInfo(toTransform.getUserSession());
                    if (userInfo.isVerified()) {
                        toTransform.setUserName(userInfo.getUsername());
                        toTransform.setGameVerified(true);
                        persistence.update(toTransform);
                    } else if (toTransform.getUserSession().equals(requestContextContainer.get().getSessionId())) {
                        toTransform.setUserName("--yourself--");
                    } else {
                        continue;
                    }
                } catch (GetUserInfoError_Exception ex) {
                    LOG.error("Error retrieving user info from record {} and user {}", toTransform.getId(), toTransform.getUserSession());
                    continue;
                }
            }
            result.add(toTransform);
        }

        return result;
    }

    @Deprecated
    @Override
    public List<CandidateInfo> getEntriesByUser(String user) {
        LOG.info("getEntryByUser requested for user {}", user);

        try {
            return persistence.getByUser(user);

        } catch (ItemNotFoundException e) {
            throw new NotFoundException(user);
        }
    }
    
    @Override
    public List<UsersReport> getUsersReport() {
        LOG.info("getUsersReport invoked");
        
        return persistence.getUsersReport();
    }

    /**
     * @param requestContextContainer the requestContextContainer to set
     */
    public void setRequestContextContainer(RequestContextContainer requestContextContainer) {
        this.requestContextContainer = requestContextContainer;
    }

    /**
     * @param systemCommands the SystemCommands to set
     */
    public void setSystemCommands(SystemCommands systemCommands) {
        this.systemCommands = systemCommands;
    }

}

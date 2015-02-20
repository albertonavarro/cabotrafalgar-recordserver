package com.navid.trafalgar.recordserver.persistence.couchbase;

import com.navid.trafalgar.recordserver.persistence.CandidateInfo;
import com.navid.trafalgar.recordserver.persistence.CandidateRecord;
import com.navid.trafalgar.recordserver.persistence.ItemNotFoundException;
import com.navid.trafalgar.recordserver.persistence.Persistence;
import com.navid.trafalgar.recordserver.persistence.UsersReport;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.annotation.Resource;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class CouchbaseImpl implements Persistence {

    private static Logger LOG = LoggerFactory.getLogger(CouchbaseImpl.class);

    private static final CandidateRecordMapper mapperRecord = CandidateRecordMapper.INSTANCE;
    private static final CandidateInfoMapper mapperInfo = CandidateInfoMapper.INSTANCE;

    @Resource
    private CDBCandidateRecordRepository repository;

    @Override
    public CandidateInfo addCandidate(CandidateRecord candidateRecord) {
        CDBCandidateRecord cdb = mapperInfo.toDtoNoId(candidateRecord);
        repository.add(cdb);
        repository.addAttachment(cdb.getId(), cdb.getRevision(), encode(candidateRecord.getPayload()), "raw");
        return mapperRecord.fromDto(cdb);
    }

    @Override
    public List<CandidateInfo> getByMap(String map) {
        List<CDBCandidateRecord> result = repository.findByMapName(map);

        return mapperInfo.fromDto(result);
    }
    
    @Override
    public List<CandidateInfo> getByMapAndShip(String map, String ship) {
        List<CDBCandidateRecord> result = repository.findByShipAndMapName(map, ship);

        return mapperInfo.fromDto(result);
    }

    @Override
    public List<CandidateInfo> getByUser(String user) {
        List<CDBCandidateRecord> result = repository.findByUser(user);

        return mapperInfo.fromDto(result);
    }

    @Override
    public CandidateRecord getById(String id) throws ItemNotFoundException {
        LOG.info("Getting by Id {}", id);
        try {
            CDBCandidateRecord result = repository.get(id);
            CandidateRecord result2 = mapperRecord.fromDto(result);
            String rawAttachment;
            try {
                rawAttachment = repository.getAttachment(id, "raw", result.getRevision());
            } catch (Exception e) {
                LOG.error("Inconsistent status for record id {}, attachment not found", id);
                repository.remove(result);
                throw new ItemNotFoundException(id);
            }
            result2.setPayload(decode(rawAttachment));
            return result2;

        } catch (Exception e) {
            throw new ItemNotFoundException(id);
        }
    }

    public static String encode(String raw) {
        try {
            return compressString(raw);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String decode(String encoded) {
        try {
            return uncompressString(encoded);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String compressString(String srcTxt)
            throws IOException {
        ByteArrayOutputStream rstBao = new ByteArrayOutputStream();
        GZIPOutputStream zos = new GZIPOutputStream(rstBao);
        zos.write(srcTxt.getBytes());
        IOUtils.closeQuietly(zos);
        String encoded = Base64.encodeBase64String(rstBao.toByteArray());
        return encoded;
    }

    public static String uncompressString(String zippedBase64Str)
            throws IOException {
        String result = null;
        GZIPInputStream zi = null;
        try {
            zi = new GZIPInputStream(new ByteArrayInputStream(Base64.decodeBase64(zippedBase64Str)));
            result = IOUtils.toString(zi);
        } finally {
            IOUtils.closeQuietly(zi);
        }
        return result;
    }

    public void setRepository(CDBCandidateRecordRepository dbRepresentation) {
        this.repository = dbRepresentation;
    }

    @Override
    public void remove(CandidateInfo toRemove) {
        CDBCandidateRecord cdb = mapperInfo.toDtoWithId(toRemove);
        repository.remove(cdb);
    }

    @Override
    public void update(CandidateInfo toUpdate) {
        repository.updateLoginVerified(toUpdate.getId());
    }

    @Override
    public List<UsersReport> getUsersReport() {
        return repository.getUsersReport();
    }

}

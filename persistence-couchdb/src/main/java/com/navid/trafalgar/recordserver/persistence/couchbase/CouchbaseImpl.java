package com.navid.trafalgar.recordserver.persistence.couchbase;

import com.navid.trafalgar.recordserver.persistence.CandidateInfo;
import com.navid.trafalgar.recordserver.persistence.CandidateRecord;
import com.navid.trafalgar.recordserver.persistence.ItemNotFoundException;
import com.navid.trafalgar.recordserver.persistence.Persistence;
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

    CandidateRecordMapper mapper = CandidateRecordMapper.INSTANCE;
    CandidateInfoMapper mapperInfo = CandidateInfoMapper.INSTANCE;

    @Resource
    private CDBCandidateRecordRepository repository;

    @Override
    public CandidateInfo addCandidate(CandidateRecord candidateRecord) {
        CDBCandidateRecord cdb = mapperInfo.toDtoNoId(candidateRecord);
        repository.add(cdb);
        repository.addAttachment(cdb.getId(), cdb.getRevision(), encode(candidateRecord.getPayload()), "raw");
        return mapper.fromDto(cdb);
    }

    @Override
    public List<CandidateInfo> getByMap(String map) {
        List<CDBCandidateRecord> result = repository.findByMapName(map);

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
            CandidateRecord result2 = mapper.fromDto(result);
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

        byte[] bytes = rstBao.toByteArray();
        // In my solr project, I use org.apache.solr.co mmon.util.Base64.
        // return = org.apache.solr.common.util.Base64.byteArrayToBase64(bytes, 0,
        // bytes.length);
        String encoded = Base64.encodeBase64String(bytes);
        return encoded;
    }

    /**
     * When client receives the zipped base64 string, it first decode base64
     * String to byte array, then use ZipInputStream to revert the byte array to
     * a string.
     */
    public static String uncompressString(String zippedBase64Str)
            throws IOException {
        String result = null;

        // In my solr project, I use org.apache.solr.common.util.Base64.
        // byte[] bytes =
        // org.apache.solr.common.util.Base64.base64ToByteArray(zippedBase64Str);
        byte[] bytes = Base64.decodeBase64(zippedBase64Str);
        GZIPInputStream zi = null;
        try {
            zi = new GZIPInputStream(new ByteArrayInputStream(bytes));
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
        CDBCandidateRecord cdb = mapperInfo.toDtoWithId(toUpdate);
        repository.update(cdb);
    }

}

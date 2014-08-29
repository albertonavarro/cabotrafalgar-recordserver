package com.navid.trafalgar.recordserver.persistence.couchbase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author casa
 */
@Test
public class CouchbaseImplTest {

    public void shouldCompressAndDecompress() {
        String source = "Test String";
        
        String compressed = CouchbaseImpl.encode(source);
        
        String target = CouchbaseImpl.decode(compressed);
        
        Assert.assertEquals(source, target);
    }
    
    
}

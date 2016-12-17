package com.navid.trafalgar.recordserver.persistence.couchbase;

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

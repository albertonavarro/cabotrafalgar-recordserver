package com.navid.trafalgar.recordserverpersistence.hibernate;

import com.navid.trafalgar.recordserverpersistence.Persistence;
import javax.annotation.Resource;
import org.jdto.DTOBinder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author alberto
 */
@Repository
public class PersistenceImpl implements Persistence {
    

    @Resource
    private StringRepository stringRepo;

    @Resource(name = "record-server.persistence-hibernate.converter")
    private DTOBinder binder;

    

}

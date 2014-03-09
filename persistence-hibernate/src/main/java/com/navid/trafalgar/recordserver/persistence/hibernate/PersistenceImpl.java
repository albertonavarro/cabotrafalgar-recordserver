package com.navid.trafalgar.recordserver.persistence.hibernate;

import com.navid.trafalgar.recordserver.persistence.Persistence;
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

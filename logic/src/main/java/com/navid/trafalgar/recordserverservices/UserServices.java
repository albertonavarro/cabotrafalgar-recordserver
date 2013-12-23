package com.navid.trafalgar.recordserverservices;

import com.navid.trafalgar.recordserverpersistence.Persistence;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author alberto
 */
@Service
@Transactional
public class UserServices {

    @Resource
    private Persistence persistence;

    

}


package com.navid.trafalgar.recordserver.persistence;

/**
 *
 * @author casa
 */
public class ItemNotFoundException extends Exception {

    public ItemNotFoundException(String id) {
        super("Record not found with id " + id);
    }
    
}

/*
 */

package com.navid.trafalgar.recordserver.persistence;

public class CandidateRecord extends CandidateInfo {
    
    private String payload;

    /**
     * @return the payload
     */
    public String getPayload() {
        return payload;
    }

    /**
     * @param payload the payload to set
     */
    public void setPayload(String payload) {
        this.payload = payload;
    }
    
}

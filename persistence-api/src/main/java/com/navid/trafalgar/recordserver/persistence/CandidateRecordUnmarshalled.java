/*
 */

package com.navid.trafalgar.recordserver.persistence;

import java.util.Date;


public class CandidateRecordUnmarshalled {
    
    private String id;
    
    private Date timestamp;
        
    private String userId;
    
    private String mapName;
    
    private String payload;
    
    private Float time;
    
    private Boolean loginVerified;
    
    private Boolean gameVerified;
    
    private String userToken;
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the mapName
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * @param mapName the mapName to set
     */
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

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

    /**
     * @return the time
     */
    public Float getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Float time) {
        this.time = time;
    }

    

    /**
     * @return the userToken
     */
    public String getUserToken() {
        return userToken;
    }

    /**
     * @param userToken the userToken to set
     */
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    /**
     * @return the loginVerified
     */
    public Boolean getLoginVerified() {
        return loginVerified;
    }

    /**
     * @param loginVerified the loginVerified to set
     */
    public void setLoginVerified(Boolean loginVerified) {
        this.loginVerified = loginVerified;
    }

    /**
     * @return the gameVerified
     */
    public Boolean getGameVerified() {
        return gameVerified;
    }

    /**
     * @param gameVerified the gameVerified to set
     */
    public void setGameVerified(Boolean gameVerified) {
        this.gameVerified = gameVerified;
    }

    
    
}

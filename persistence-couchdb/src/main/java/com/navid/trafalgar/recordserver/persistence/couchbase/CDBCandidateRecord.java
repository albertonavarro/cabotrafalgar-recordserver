package com.navid.trafalgar.recordserver.persistence.couchbase;

import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

import java.util.Date;


public class CDBCandidateRecord extends CouchDbDocument {
    
    @TypeDiscriminator
    private Date timestamp;
        
    private String userName;
    
    private String mapName;
    
    private String shipModel;
    
    private Float time;
    
    private Boolean loginVerified;
    
    private Boolean gameVerified;
    
    private String userSession;
    
    private transient Integer position;
    
    private String userToken;
    
    private String shipName;
    

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
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userId to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
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

    /**
     * @return the position
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    /**
     * @return the shipModel
     */
    public String getShipModel() {
        return shipModel;
    }

    /**
     * @param shipModel the shipModel to set
     */
    public void setShipModel(String shipModel) {
        this.shipModel = shipModel;
    }

    /**
     * @return the userSession
     */
    public String getUserSession() {
        return userSession;
    }

    /**
     * @param userSession the userSession to set
     */
    public void setUserSession(String userSession) {
        this.userSession = userSession;
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
     * @return the shipName
     */
    public String getShipName() {
        return shipName;
    }

    /**
     * @param shipName the shipName to set
     */
    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

}

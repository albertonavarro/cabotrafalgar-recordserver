/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.navid.trafalgar.recordserver.endpoints;

/**
 *
 * @author alberto
 */
public class StoreRequest {
    
    private String sessionId;
    
    private String gameinfo;

    /**
     * @return the sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId the sessionId to set
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @return the gameinfo
     */
    public String getGameinfo() {
        return gameinfo;
    }

    /**
     * @param gameinfo the gameinfo to set
     */
    public void setGameinfo(String gameinfo) {
        this.gameinfo = gameinfo;
    }

    
    
}

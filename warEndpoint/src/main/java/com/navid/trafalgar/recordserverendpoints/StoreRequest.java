/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.navid.trafalgar.recordserverendpoints;

/**
 *
 * @author alberto
 */
public class StoreRequest {
    
    private String game;
    
    private String user;

    /**
     * @return the game
     */
    public String getGame() {
        return game;
    }

    /**
     * @param game the game to set
     */
    public void setGame(String game) {
        this.game = game;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }
    
}

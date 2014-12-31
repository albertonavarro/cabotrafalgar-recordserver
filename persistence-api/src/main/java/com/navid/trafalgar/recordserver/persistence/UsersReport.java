package com.navid.trafalgar.recordserver.persistence;


public final class UsersReport {

    private String userName;
    private int games;

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the games
     */
    public int getGames() {
        return games;
    }

    /**
     * @param games the games to set
     */
    public void setGames(int games) {
        this.games = games;
    }

}

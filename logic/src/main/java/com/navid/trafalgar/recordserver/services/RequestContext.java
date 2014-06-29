/*
 */
package com.navid.trafalgar.recordserver.services;

import com.google.common.base.Objects;

public class RequestContext {

    private String userId;

    private String userName;

    private String requestId;

    RequestContext() {

    }

    /**
     * @return the requestId
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * @param requestId the requestId to set
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
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
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("userName", userName)
                .add("userId", userId)
                .add("requestId", requestId)
                .toString();
    }

}

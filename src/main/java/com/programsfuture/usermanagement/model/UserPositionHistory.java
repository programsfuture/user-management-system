package com.programsfuture.usermanagement.model;

public class UserPositionHistory {

    private int id;
    private int userId;
    private Integer oldPositionId;
    private Integer newPositionId;
    private int changedByUserId;
    private String changedDateShamsi;

    public UserPositionHistory() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getOldPositionId() {
        return oldPositionId;
    }

    public void setOldPositionId(Integer oldPositionId) {
        this.oldPositionId = oldPositionId;
    }

    public Integer getNewPositionId() {
        return newPositionId;
    }

    public void setNewPositionId(Integer newPositionId) {
        this.newPositionId = newPositionId;
    }

    public int getChangedByUserId() {
        return changedByUserId;
    }

    public void setChangedByUserId(int changedByUserId) {
        this.changedByUserId = changedByUserId;
    }

    public String getChangedDateShamsi() {
        return changedDateShamsi;
    }

    public void setChangedDateShamsi(String changedDateShamsi) {
        this.changedDateShamsi = changedDateShamsi;
    }
}

package com.programsfuture.usermanagement.model;

public class Position {

    private int positionId;
    private String positionName;
    private String description;
    private boolean active;

    public Position() {
    }

    public Position(int positionId, String positionName, String description, boolean active) {
        this.positionId = positionId;
        this.positionName = positionName;
        this.description = description;
        this.active = active;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

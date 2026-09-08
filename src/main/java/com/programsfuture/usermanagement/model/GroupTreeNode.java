package com.programsfuture.usermanagement.model;

public class GroupTreeNode {

    private final int id;
    private final String displayName;

    public GroupTreeNode(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

package com.example.wgjuh.magistrateuiexamplefirsttest;

/**
 * Created by wGJUH on 20.01.2017.
 */

public class Group {
    int groupId;
    String administrator;
    String groupName;
    public Group(int groupId, String groupName, String administrator){
        this.groupId = groupId;
        this.administrator = administrator;
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getAdministrator() {
        return administrator;
    }

    public void setAdministrator(String administrator) {
        this.administrator = administrator;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}

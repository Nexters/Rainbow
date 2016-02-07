package com.nexters.rainbow.rainbowcouple.login;

import com.nexters.rainbow.rainbowcouple.common.utils.ObjectUtils;
import com.nexters.rainbow.rainbowcouple.group.Group;

public class UserDto {
    private String rs;
    private String userId;
    private String userName;
    private Group group;

    public String getRs() {
        return rs;
    }

    public void setRs(String rs) {
        this.rs = rs;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("userId : ")
                .append(userId)
                .append("\nuserName : ")
                .append(userName);

        if (ObjectUtils.isNotEmpty(group)) {
            builder.append("\ngroup")
                    .append(group.toString());

        }

        return builder.toString();
    }
}
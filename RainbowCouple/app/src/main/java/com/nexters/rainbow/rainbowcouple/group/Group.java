package com.nexters.rainbow.rainbowcouple.group;

import java.util.List;

public class Group {

    private int sn;
    private List<String> member;
    private String inviteCode;
    private int active;

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public List<String> getMember() {
        return member;
    }

    public void setMember(List<String> member) {
        this.member = member;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Group serialNumber(int serialNumber) {
        this.setSn(serialNumber);
        return this;
    }

    public Group member(List<String> member) {
        this.setMember(member);
        return this;
    }

    public Group inviteCode(String inviteCode) {
        this.setInviteCode(inviteCode);
        return this;
    }

    public Group active(int active) {
        this.setActive(active);
        return this;
    }

    public static Group builder() {
        return new Group();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("sn : ")
                .append(sn)
                .append("\nmember : ")
                .append(member)
                .append("\ninviteCode : ")
                .append(inviteCode)
                .append("\nactive : ")
                .append(active);
        return builder.toString();
    }
}

package com.nexters.rainbow.rainbowcouple.bill;

import java.util.Date;

public class Bill {

    private Long billId;
    private Long budget;
    private String comment;
    private Date createdAt;
    private boolean isMyBill;

    public Bill(Long budget, String comment, Date createdAt, boolean isMyBill) {
        this.budget = budget;
        this.comment = comment;
        this.createdAt = createdAt;
        this.isMyBill = isMyBill;
    }

    public Bill(Long billId, Long budget, String comment, Date createdAt, boolean isMyBill) {
        this.billId = billId;
        this.budget = budget;
        this.comment = comment;
        this.createdAt = createdAt;
        this.isMyBill = isMyBill;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isMyBill() {
        return isMyBill;
    }

    public void setIsMyBill(boolean isMyBill) {
        this.isMyBill = isMyBill;
    }
}

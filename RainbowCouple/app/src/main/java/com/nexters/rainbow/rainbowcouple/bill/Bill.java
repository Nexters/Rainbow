package com.nexters.rainbow.rainbowcouple.bill;

import java.util.Date;

public class Bill {

    private Long billId;
    private Long budget;
    private String category;
    private String comment;
    private Date createdAt;

    public Bill(Long budget, String cateogry, String comment, Date createdAt) {
        this.budget = budget;
        this.category = cateogry;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public Bill(Long billId, Long budget, String category, String comment, Date createdAt) {
        this.billId = billId;
        this.budget = budget;
        this.category = category;
        this.comment = comment;
        this.createdAt = createdAt;
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

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

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

}

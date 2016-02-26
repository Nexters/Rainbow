package com.nexters.rainbow.rainbowcouple.bill;

import lombok.Getter;

public enum BillOwner {
    ALL("ALL"),
    MINE("MINE"),
    PARTNER("PARTNER");

    @Getter
    private String description;

    BillOwner(String description) {
        this.description = description;
    }

    public String getName() {
        return this.name();
    }
}

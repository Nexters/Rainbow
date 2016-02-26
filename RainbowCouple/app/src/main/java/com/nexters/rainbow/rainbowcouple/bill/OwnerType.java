package com.nexters.rainbow.rainbowcouple.bill;

import lombok.Getter;

public enum OwnerType {
    ALL("ALL"),
    MINE("MINE"),
    PARTNER("PARTNER");

    @Getter
    private String description;

    OwnerType(String description) {
        this.description = description;
    }

    public String getName() {
        return this.name();
    }
}

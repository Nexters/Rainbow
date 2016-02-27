package com.nexters.rainbow.rainbowcouple.bill;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Bill {
    private int year;
    private int month;
    private int day;
    private String userSN;
    private String userName;
    private String category;
    private int amount;
    private String comment;
    private OwnerType ownerType;
}

package com.nexters.rainbow.rainbowcouple.bill.add;

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
public class BillAddForm {
    private int year;
    private int month;
    private int day;
    private int amount;
    private String category;
    private String hashtags;
    private String comment;
}

package com.nexters.rainbow.rainbowcouple.graph;

import com.nexters.rainbow.rainbowcouple.bill.OwnerType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by soyoon on 2016. 4. 9..
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class BillStatics {
    private String category;
    private int amount;
    private int percentage;
}

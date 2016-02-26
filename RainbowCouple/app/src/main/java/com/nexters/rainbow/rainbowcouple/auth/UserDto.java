package com.nexters.rainbow.rainbowcouple.auth;

import com.nexters.rainbow.rainbowcouple.group.Group;

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
public class UserDto {
    private String token;
    private String userId;
    private String userName;
    private String password;
    private Group group;
}
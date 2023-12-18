package com.hotel.mvp.hotelbooking.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleKey implements Serializable {

    private String username;
    private String authority;

    // Constructors, equals, hashCode, and other methods

}
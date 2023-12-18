package com.hotel.mvp.hotelbooking.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_role")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserRoleKey.class)
public class UserRole {
    @Id
    @Column(name = "username", length = 255, nullable = false)
    private String username;

    @Id
    @Column(name = "authority", length = 50, nullable = false)
    private String authority;
}

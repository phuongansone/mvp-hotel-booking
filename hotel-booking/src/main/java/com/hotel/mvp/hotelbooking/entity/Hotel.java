package com.hotel.mvp.hotelbooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "hotel")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "hotel_id", updatable = false, nullable = false)
    private UUID hotelId;

    @Column(name = "hotel_name", nullable = false)
    private String hotelName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "room_type", nullable = false)
    private String roomType;

    @Column(name = "price", nullable = false)
    private Double price;
}

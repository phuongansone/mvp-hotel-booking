package com.hotel.mvp.hotelbooking.dto;

import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.UUID;

@Data
public class CreateBookingRequest {
    @NotNull(message = "Hotel ID cannot be null")
    private UUID hotelId;

    @NotBlank(message = "Check-in date cannot be blank")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String checkinDate;

    @NotBlank(message = "Check-out date cannot be blank")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String checkoutDate;
}

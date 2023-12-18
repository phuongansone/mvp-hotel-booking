package com.hotel.mvp.hotelbooking.dto;

import com.hotel.mvp.hotelbooking.enums.BookingStatus;
import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.UUID;

@Data
@Builder
public class UpdateBookingRequest {
    @NotNull(message = "Booking ID cannot be null")
    private UUID bookingId;

    @NotBlank(message = "Check-in date cannot be blank")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String checkinDate;

    @NotBlank(message = "Check-out date cannot be blank")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String checkoutDate;

    @NotNull(message = "Status cannot be null")
    private BookingStatus status;
}


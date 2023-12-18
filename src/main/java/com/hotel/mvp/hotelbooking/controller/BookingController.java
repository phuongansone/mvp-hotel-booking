package com.hotel.mvp.hotelbooking.controller;

import com.hotel.mvp.hotelbooking.dto.CreateBookingRequest;
import com.hotel.mvp.hotelbooking.dto.UpdateBookingRequest;
import com.hotel.mvp.hotelbooking.entity.Booking;
import com.hotel.mvp.hotelbooking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/bookings")
    public ResponseEntity<Booking> createBooking(@RequestBody @Valid CreateBookingRequest booking) {
        // Retrieve the username from the authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Booking createdBooking = bookingService.createBooking(booking, username);

        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

    @PutMapping("/bookings")
    public ResponseEntity<Booking> updateBooking(@RequestBody @Valid UpdateBookingRequest request) {
        // Retrieve the username from the authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Booking updatedBooking = bookingService.updateBooking(request, username);

        return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getBookingList() {
        // Retrieve the username from the authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<Booking> bookingList = bookingService.getBookingList(username);
        return new ResponseEntity<>(bookingList, HttpStatus.OK);
    }

    @GetMapping("/bookings/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable UUID bookingId) {
        // Retrieve the username from the authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Booking booking = bookingService.getBookingById(bookingId, username);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @PutMapping("/bookings/cancellation/{bookingId}")
    public ResponseEntity<Booking> cancelBooking(@PathVariable UUID bookingId) {
        // Retrieve the username from the authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Booking updatedBooking = bookingService.cancelBooking(bookingId, username);

        return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
    }
}


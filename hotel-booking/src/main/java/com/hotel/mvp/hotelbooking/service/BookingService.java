package com.hotel.mvp.hotelbooking.service;

import com.hotel.mvp.hotelbooking.dto.CreateBookingRequest;
import com.hotel.mvp.hotelbooking.dto.UpdateBookingRequest;
import com.hotel.mvp.hotelbooking.entity.Booking;

import java.util.List;
import java.util.UUID;

public interface BookingService {
    /**
     * Create new hotel booking
     * @param booking request object
     * @param username username
     * @return created booking
     */
    Booking createBooking(CreateBookingRequest booking, String username);

    /**
     * Update a booking
     * @param booking request object
     * @param username username
     * @return updated booking
     */
    Booking updateBooking(UpdateBookingRequest booking, String username);

    /**
     * Get booking list
     * @param username username whose bookings will be returned
     * @return list of bookings
     */
    List<Booking> getBookingList(String username);

    /**
     * Get a booking by its id
     * @param bookingId booking id
     * @param username username whose booking will be returned
     * @return the booking
     */
    Booking getBookingById(UUID bookingId, String username);

    /**
     * Cancel a booking by its id
     * @param bookingId booking id
     * @param username username whose booking will be canceled
     * @return the canceled booking
     */
    Booking cancelBooking(UUID bookingId, String username);
}

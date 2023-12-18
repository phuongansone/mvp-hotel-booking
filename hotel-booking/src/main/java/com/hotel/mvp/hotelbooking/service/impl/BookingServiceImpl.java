package com.hotel.mvp.hotelbooking.service.impl;

import com.hotel.mvp.hotelbooking.dto.CreateBookingRequest;
import com.hotel.mvp.hotelbooking.dto.UpdateBookingRequest;
import com.hotel.mvp.hotelbooking.entity.Booking;
import com.hotel.mvp.hotelbooking.entity.Hotel;
import com.hotel.mvp.hotelbooking.entity.User;
import com.hotel.mvp.hotelbooking.enums.BookingStatus;
import com.hotel.mvp.hotelbooking.exception.BadRequestException;
import com.hotel.mvp.hotelbooking.exception.NotFoundException;
import com.hotel.mvp.hotelbooking.mapper.BookingMapper;
import com.hotel.mvp.hotelbooking.repository.BookingRepository;
import com.hotel.mvp.hotelbooking.repository.HotelRepository;
import com.hotel.mvp.hotelbooking.repository.UserRepository;
import com.hotel.mvp.hotelbooking.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;
    private final HotelRepository hotelRepository;

    @Override
    @Transactional
    public Booking createBooking(CreateBookingRequest request, String username) {
        log.info("Enter createBooking with request: {}, username: {}", request, username);
        // Map the CreateBookingRequest to Booking entity
        Booking booking = bookingMapper.mapCreateBookingRequestToBooking(request);

        // Get user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("User not found"));
        booking.setUser(user);

        // Get hotel
        Hotel hotel = hotelRepository.findById(request.getHotelId())
                .orElseThrow(() -> new BadRequestException("Hotel not found"));
        booking.setHotel(hotel);

        log.info("Exit createBooking");

        // Save the booking to the database
        return bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public Booking updateBooking(UpdateBookingRequest booking, String username) {
        log.info("Enter updateBooking with booking: {}, username: {}", booking, username);
        Booking updatedBooking = bookingMapper.mapUpdateBookingRequestToBooking(booking);

        // Retrieve the existing booking from the database
        Booking existingBooking = bookingRepository.findByBookingIdAndUsername(booking.getBookingId(), username)
                .orElseThrow(() -> new NotFoundException("Booking not found"));

        // Update the fields based on the request
        existingBooking.setCheckinDate(updatedBooking.getCheckinDate());
        existingBooking.setCheckoutDate(updatedBooking.getCheckoutDate());
        existingBooking.setStatus(booking.getStatus().name());

        log.info("Exit updateBooking");

        // Save the updated booking to the database
        return bookingRepository.save(existingBooking);
    }

    @Override
    public List<Booking> getBookingList(String username) {
        return bookingRepository.findByUsername(username);
    }

    @Override
    public Booking getBookingById(UUID bookingId, String username) {
        return bookingRepository.findByBookingIdAndUsername(bookingId, username)
                .orElseThrow(() -> new NotFoundException("Booking not found"));
    }

    @Override
    @Transactional
    public Booking cancelBooking(UUID bookingId, String username) {
        log.info("Enter cancelBooking with bookingId: {}, username: {}", bookingId, username);

        Booking bookingToCancel = bookingRepository.findByBookingIdAndUsername(bookingId, username)
                .orElseThrow(() -> new NotFoundException("Booking not found"));

        // TODO: Check if the booking is cancellable (based on business rules)

        // Update the status to CANCELLED
        bookingToCancel.setStatus(BookingStatus.CANCELLED_BY_CUSTOMER.name());

        log.info("Exit cancelBooking");

        // Save the updated booking to the database
        return bookingRepository.save(bookingToCancel);
    }
}


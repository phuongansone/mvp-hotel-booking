package com.hotel.mvp.hotelbooking.service.impl;

import com.hotel.mvp.hotelbooking.dto.CreateBookingRequest;
import com.hotel.mvp.hotelbooking.dto.UpdateBookingRequest;
import com.hotel.mvp.hotelbooking.entity.Booking;
import com.hotel.mvp.hotelbooking.entity.Hotel;
import com.hotel.mvp.hotelbooking.entity.User;
import com.hotel.mvp.hotelbooking.enums.BookingStatus;
import com.hotel.mvp.hotelbooking.exception.NotFoundException;
import com.hotel.mvp.hotelbooking.mapper.BookingMapper;
import com.hotel.mvp.hotelbooking.repository.BookingRepository;
import com.hotel.mvp.hotelbooking.repository.HotelRepository;
import com.hotel.mvp.hotelbooking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {
    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookingMapper bookingMapper;

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void BookingServiceImpl_createBooking() {
        // Arrange
        CreateBookingRequest createBookingRequest = new CreateBookingRequest();
        String username = "username";

        User user = User.builder()
                .userId(UUID.randomUUID())
                .username("username")
                .password("password")
                .build();

        Hotel hotel = Hotel.builder()
                .hotelId(UUID.randomUUID())
                .address("Address")
                .hotelName("Name")
                .price(400.0)
                .roomType("STANDARD")
                .build();

        Booking booking = Booking.builder()
                .bookingId(UUID.randomUUID())
                .user(user)
                .hotel(hotel)
                .checkinDate("2023-12-01")
                .checkoutDate("2023-12-05")
                .status("PENDING")
                .build();

        when(bookingMapper.mapCreateBookingRequestToBooking(any(CreateBookingRequest.class))).thenReturn(booking);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(hotelRepository.findById(any())).thenReturn(Optional.of(hotel));
        when(bookingRepository.save(booking)).thenReturn(booking);

        // Act
        Booking result = bookingService.createBooking(createBookingRequest, username);

        // Assert
        verify(bookingMapper).mapCreateBookingRequestToBooking(createBookingRequest);
        verify(userRepository).findByUsername(username);
        verify(hotelRepository).findById(any());
        verify(bookingRepository).save(booking);

        // Additional assertions based on your business logic
        // For example, you can check if the user and hotel are set on the booking entity.
        assertNotNull(result.getUser());
        assertNotNull(result.getHotel());
    }

    @Test
    void BookingServiceImpl_updateBooking() {
        // Arrange
        UpdateBookingRequest updateBookingRequest = UpdateBookingRequest.builder()
                .bookingId(UUID.randomUUID())
                .checkinDate("2023-12-01")
                .checkoutDate("2023-12-05")
                .status(BookingStatus.CONFIRMED)
                .build();

        Booking existingBooking = new Booking(); // create an existing booking
        String username = "username";

        when(bookingRepository.findByBookingIdAndUsername(updateBookingRequest.getBookingId(), username))
                .thenReturn(Optional.of(existingBooking));
        when(bookingRepository.save(existingBooking)).thenReturn(existingBooking);

        // Act
        Booking result = bookingService.updateBooking(updateBookingRequest, username);

        // Assert
        verify(bookingRepository).findByBookingIdAndUsername(updateBookingRequest.getBookingId(), username);
        verify(bookingRepository).save(existingBooking);

        // Additional assertions based on your business logic
        assertEquals(updateBookingRequest.getCheckinDate(), result.getCheckinDate());
        assertEquals(updateBookingRequest.getCheckoutDate(), result.getCheckoutDate());
        assertEquals(updateBookingRequest.getStatus().name(), result.getStatus());
    }

    @Test
    void BookingServiceImpl_updateBooking_notFound() {
        // Arrange
        UpdateBookingRequest updateBookingRequest = UpdateBookingRequest.builder()
                .bookingId(UUID.randomUUID())
                .checkinDate("2023-12-01")
                .checkoutDate("2023-12-05")
                .status(BookingStatus.CONFIRMED)
                .build();

        String username = "username";

        when(bookingRepository.findByBookingIdAndUsername(updateBookingRequest.getBookingId(), username))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> bookingService.updateBooking(updateBookingRequest, username));

        // Verify that the save method was not called in case of NotFoundException
        verify(bookingRepository, never()).save(any());
    }

}
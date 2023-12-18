package com.hotel.mvp.hotelbooking.repository;

import com.hotel.mvp.hotelbooking.entity.Booking;
import com.hotel.mvp.hotelbooking.entity.Hotel;
import com.hotel.mvp.hotelbooking.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class BookingRepositoryTest {
    @Autowired
    private BookingRepository bookingRepository;

    @Test
    public void BookingRepository_findByUsername_returnListBookings() {
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

        Booking booking1 = Booking.builder()
                .user(user)
                .hotel(hotel)
                .bookingId(UUID.randomUUID())
                .checkinDate(Date.valueOf("2023-12-01"))
                .checkoutDate(Date.valueOf("2023-12-05"))
                .status("PENDING")
                .build();

        Booking booking2 = Booking.builder()
                .user(user)
                .hotel(hotel)
                .bookingId(UUID.randomUUID())
                .checkinDate(Date.valueOf("2023-12-01"))
                .checkoutDate(Date.valueOf("2023-12-05"))
                .status("PENDING")
                .build();

        bookingRepository.save(booking1);
        bookingRepository.save(booking2);

        List<Booking> bookings = bookingRepository.findByUsername(user.getUsername());

        Assertions.assertThat(bookings).size().isEqualTo(2);
    }

    @Test
    public void BookingRepository_findByBookingIdAndUsername_returnBooking() {
        String username = "username";

        User user = User.builder()
                .userId(UUID.randomUUID())
                .username(username)
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
                .checkinDate(Date.valueOf("2023-12-01"))
                .checkoutDate(Date.valueOf("2023-12-05"))
                .status("PENDING")
                .build();

        bookingRepository.save(booking);

        Optional<Booking> returnedBooking = bookingRepository.findByBookingIdAndUsername(booking.getBookingId(), username);

        Assertions.assertThat(returnedBooking.isPresent()).isTrue();
    }
}
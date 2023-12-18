package com.hotel.mvp.hotelbooking.repository;

import com.hotel.mvp.hotelbooking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    @Query("SELECT b FROM Booking b " +
            "JOIN FETCH b.user u " +
            "WHERE b.bookingId = :bookingId AND u.username = :username")
    Optional<Booking> findByBookingIdAndUsername(@Param("bookingId") UUID bookingId,
                                                 @Param("username") String username);

    @Query("SELECT b FROM Booking b " +
            "JOIN b.user u " +
            "WHERE u.username = :username")
    List<Booking> findByUsername(@Param("username") String username);
}


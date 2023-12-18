package com.hotel.mvp.hotelbooking.mapper;

import com.hotel.mvp.hotelbooking.dto.CreateBookingRequest;
import com.hotel.mvp.hotelbooking.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookingMapper {

    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    @Mapping(target = "bookingId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    Booking mapCreateBookingRequestToBooking(CreateBookingRequest request);
}

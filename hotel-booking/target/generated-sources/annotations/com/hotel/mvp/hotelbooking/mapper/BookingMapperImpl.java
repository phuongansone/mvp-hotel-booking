package com.hotel.mvp.hotelbooking.mapper;

import com.hotel.mvp.hotelbooking.dto.CreateBookingRequest;
import com.hotel.mvp.hotelbooking.entity.Booking;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-18T22:23:50+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (Oracle Corporation)"
)
public class BookingMapperImpl implements BookingMapper {

    @Override
    public Booking mapCreateBookingRequestToBooking(CreateBookingRequest request) {
        if ( request == null ) {
            return null;
        }

        Booking.BookingBuilder booking = Booking.builder();

        booking.checkinDate( request.getCheckinDate() );
        booking.checkoutDate( request.getCheckoutDate() );

        booking.status( "PENDING" );

        return booking.build();
    }
}

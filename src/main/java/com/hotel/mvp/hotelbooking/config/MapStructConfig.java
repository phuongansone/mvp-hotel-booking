package com.hotel.mvp.hotelbooking.config;

import com.hotel.mvp.hotelbooking.mapper.BookingMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapStructConfig {
   @Bean
   public BookingMapper bookingMapper() {
       return Mappers.getMapper(BookingMapper.class);
   }
}

package com.project.booking.services;

import com.project.booking.models.HotelImage;
import com.project.booking.repositories.HotelImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelImageService {
    private final HotelImageRepository hotelImageRepository;

    public HotelImage updateImage(HotelImage hotelImage){
        return hotelImageRepository.save(hotelImage);
    }
}

package com.project.booking.services;

import com.project.booking.dtos.FacilityDTO;
import com.project.booking.dtos.FacilityDetailDTO;
import com.project.booking.dtos.HotelDTO;
import com.project.booking.exceptions.DataNotFoundException;
import com.project.booking.models.*;
import com.project.booking.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelFacilityDetailRepository hotelFacilityDetailRepository;
    private final HotelFacilityRepository hotelFacilityRepository;
    private final HotelImageRepository hotelImageRepository;

    public Hotel addPlace(HotelDTO hotelDTO) {
        Hotel newHotel = Hotel.builder()
                .name(hotelDTO.getName())
                .type(hotelDTO.getType())
                .description(hotelDTO.getDescription())
                .latitude(hotelDTO.getLatitude())
                .longitude(hotelDTO.getLongitude())
                .rating(hotelDTO.getRating())
                .reviews(hotelDTO.getReviews())
                .price(hotelDTO.getPrice())
                .stars(hotelDTO.getStars())
                .image(hotelDTO.getImage())
                .images(hotelDTO.getImages())
                .build();
        Address address = new Address();
        address.setFull(hotelDTO.getAddressDTO().getFull());
        address.setStreet(hotelDTO.getAddressDTO().getStreet());
        address.setCountry(hotelDTO.getAddressDTO().getCountry());
        address.setRegion(hotelDTO.getAddressDTO().getRegion());
        address.setPostalCode(hotelDTO.getAddressDTO().getPostalCode());
        newHotel.setAddress(address);

        hotelRepository.save(newHotel);

        for (FacilityDTO facilityDTO : hotelDTO.getFacilityDTO()) {
            HotelFacility hotelFacility = HotelFacility.builder()
                    .name(facilityDTO.getName())
                    .overview(facilityDTO.getOverview())
                    .hotel(newHotel)
                    .build();
            hotelFacilityRepository.save(hotelFacility);

            for (FacilityDetailDTO facilityDetailDTO : facilityDTO.getFacilityDetails()) {
                HotelFacilityDetail hotelFacilityDetail = HotelFacilityDetail.builder()
                        .hotelFacility(hotelFacility)
                        .name(facilityDetailDTO.getName())
                        .additionalInfo(facilityDetailDTO.getAdditionalInfo())
                        .build();
                hotelFacilityDetailRepository.save(hotelFacilityDetail);
            }
        }
        return newHotel;
    }

    public Page<Hotel> getAllPlace(PageRequest pageRequest){
        return hotelRepository.findAll(pageRequest);
    }
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }
    public List<Hotel> searchHotels(String type, String region, String country) {
        return hotelRepository.searchHotels(type, region, country);
    }
    public Hotel getHotelById(Long hotelId){
        Hotel hotel =  hotelRepository.findById(hotelId)
                .orElseThrow(()-> new DataNotFoundException("Cannot find the hotel you need"));
        //List<HotelFacility> hotelFacilityList = hotelFacilityRepository.findHotelById(hotelId);
                //.orElseThrow(()->new DataNotFoundException("Cannot find hotel"));
        return hotel;
    }

    public Hotel updateThumbnail(Long hotelId, HotelDTO hotelDTO){
        Hotel existingHotel = getHotelById(hotelId);
        existingHotel.setImage(hotelDTO.getImage());
        return hotelRepository.save(existingHotel);
    }

    public void deleteHotel(Long hotelId){
        Hotel hotel = getHotelById(hotelId);
        hotelRepository.deleteById(hotel.getId());
    }

    public List<String> getHotelImage(Long hotelId){
        return hotelImageRepository.getHotelImage(hotelId);
    }

    public List<HotelFacility> getHotelFacility(Long hotelId){
        return hotelFacilityRepository.findHotelById(hotelId);
    }
}

package com.project.booking.services;

import com.project.booking.dtos.FacilityDTO;
import com.project.booking.dtos.FacilityDetailDTO;
import com.project.booking.dtos.HotelDTO;
import com.project.booking.exceptions.DataNotFoundException;
import com.project.booking.models.*;
import com.project.booking.repositories.*;
import com.project.booking.response.RoomDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelFacilityDetailRepository hotelFacilityDetailRepository;
    private final HotelFacilityRepository hotelFacilityRepository;
    private final HotelImageRepository hotelImageRepository;
    private final RoomDetailRepository roomDetailRepository;

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

    public Hotel getHotelById(Long hotelId){
        //List<HotelFacility> hotelFacilityList = hotelFacilityRepository.findHotelById(hotelId);
                //.orElseThrow(()->new DataNotFoundException("Cannot find hotel"));
        return hotelRepository.findById(hotelId)
                .orElseThrow(()-> new DataNotFoundException("Cannot find the hotel you need"));
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

    public List<RoomDetailResponse> getRoomDetailResponse(Long hotelId){
        List<RoomDetail> roomDetails = roomDetailRepository.findRoomByHotelId(hotelId);
        List<RoomDetailResponse> roomDetailResponseList = new ArrayList<>();
        for(RoomDetail roomDetail : roomDetails){
            RoomDetailResponse roomDetailResponse = RoomDetailResponse.fromRoomDetail(roomDetail);
            roomDetailResponseList.add(roomDetailResponse);
        }
        return roomDetailResponseList;
    }

    public List<RoomDetailResponse> getRoomAvailable(Long hotelId, String checkInStr, String checkOutStr) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date checkIn = formatter.parse(checkInStr);
        Date checkOut = formatter.parse(checkOutStr);

        List<RoomDetail> roomDetails = roomDetailRepository.findAvailableRooms(hotelId, checkIn, checkOut);
        List<RoomDetailResponse> roomDetailResponseList = new ArrayList<>();

        for (RoomDetail roomDetail : roomDetails) {
                RoomDetailResponse roomDetailResponse = RoomDetailResponse.fromRoomDetail(roomDetail);
                roomDetailResponseList.add(roomDetailResponse);

        }
        return roomDetailResponseList;
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public List<Hotel> searchHotels(String type, String region, String hotelName) {
        return hotelRepository.searchHotels(type, region, hotelName);
    }
}

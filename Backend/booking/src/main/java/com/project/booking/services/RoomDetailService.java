package com.project.booking.services;

import com.project.booking.dtos.*;
import com.project.booking.exceptions.DataNotFoundException;
import com.project.booking.models.*;
import com.project.booking.repositories.HotelRepository;
import com.project.booking.repositories.RoomDetailRepository;
import com.project.booking.repositories.RoomFacilityDetailRepository;
import com.project.booking.repositories.RoomFacilityRepository;
import com.project.booking.response.ListRoomDetailResponse;
import com.project.booking.response.RoomDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomDetailService {
    private final HotelRepository hotelRepository;
    private final RoomDetailRepository roomDetailRepository;
    private final RoomFacilityRepository roomFacilityRepository;
    private final RoomFacilityDetailRepository roomFacilityDetailRepository;

    public RoomDetail createRoom(RoomDetailDTO roomDetailDTO){
        Hotel existingHotel = hotelRepository.findById(roomDetailDTO.getHotelId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find room with id: " + roomDetailDTO.getHotelId()));
        RoomDetail newRoom = RoomDetail.builder()
                .available(roomDetailDTO.getAvailable())
                .url(roomDetailDTO.getUrl())
                .roomType(roomDetailDTO.getRoomType())
                .persons(roomDetailDTO.getPersons())
                .checkIn(roomDetailDTO.getCheckIn())
                .checkOut(roomDetailDTO.getCheckOut())
                .pricePerNight(roomDetailDTO.getPricePerNight())
                .hotel(existingHotel)
                .build();
         roomDetailRepository.save(newRoom);

        for (RoomFacilityDTO facilityDTO : roomDetailDTO.getFacilityDTO()) {
            RoomFacilities roomFacilities = RoomFacilities.builder()
                    .name(facilityDTO.getName())
                    .overview(facilityDTO.getOverview())
                    .room(newRoom)
                    .build();
            roomFacilityRepository.save(roomFacilities);

            for (RoomFacilityDetailDTO facilityDetailDTO : facilityDTO.getRoomFacilityDetailDTOS()) {
                RoomFacilityDetail roomFacilityDetail = RoomFacilityDetail.builder()
                        .roomFacility(roomFacilities)
                        .name(facilityDetailDTO.getName())
                        .additionalInfo(facilityDetailDTO.getAdditionalInfo())
                        .build();
                roomFacilityDetailRepository.save(roomFacilityDetail);
            }
        }
        return newRoom;
    }

    public ListRoomDetailResponse getAllRoomOfPlace(Long hotelId){
        List<RoomDetailResponse> roomDetailResponses = roomDetailRepository.findAllRoomInPlace(hotelId).stream()
                .map(RoomDetailResponse::fromRoomDetail).toList();
                //.orElseThrow(()->new DataNotFoundException("Cannot find place"));
        return ListRoomDetailResponse.builder()
                .roomDetailResponseList(roomDetailResponses)
                .build();

    }

    public List<RoomDetail> getAvailableRooms(Long hotelId, Date checkInDate, Date checkOutDate) {
        return roomDetailRepository.findAvailableRooms(hotelId, checkInDate, checkOutDate);
    }

    public RoomDetail getRoomById(Long roomId){
        RoomDetail roomDetail =  roomDetailRepository.findById(roomId)
                .orElseThrow(()-> new DataNotFoundException("Cannot find the hotel you need"));
        return roomDetail;
    }
}

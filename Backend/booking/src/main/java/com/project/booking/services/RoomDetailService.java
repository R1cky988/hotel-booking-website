package com.project.booking.services;

import com.project.booking.dtos.RoomDetailDTO;
import com.project.booking.exceptions.DataNotFoundException;
import com.project.booking.models.Hotel;
import com.project.booking.models.RoomDetail;
import com.project.booking.repositories.HotelRepository;
import com.project.booking.repositories.RoomDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomDetailService {
    private final HotelRepository hotelRepository;
    private final RoomDetailRepository roomDetailRepository;

//    public RoomDetail createRoom(RoomDetailDTO roomDetailDTO){
//        Hotel existingHotel = hotelRepository.findById(roomDetailDTO.getPlaceId())
//                .orElseThrow(() -> new DataNotFoundException(
//                        "Cannot find room with id: " + roomDetailDTO.getPlaceId()));
//        RoomDetail newRoom = RoomDetail.builder()
//                .address(roomDetailDTO.getAddress())
//                .perks(roomDetailDTO.getPerks())
//                .photo(roomDetailDTO.getPhoto())
//                .title(roomDetailDTO.getTitle())
//                .price(roomDetailDTO.getPrice())
//                .maxGuests(roomDetailDTO.getMaxGuests())
//                .checkIn(roomDetailDTO.getCheckIn())
//                .checkOut(roomDetailDTO.getCheckOut())
//                .description(roomDetailDTO.getDescription())
//                .address(roomDetailDTO.getAddress())
//                .place(existingHotel)
//                .extraInfo(roomDetailDTO.getExtraInfo())
//                .build();
//        return roomDetailRepository.save(newRoom);
//    }

    public List<RoomDetail> getAllRoomOfPlace(Long placeId){
        Hotel existingHotel = hotelRepository.findById(placeId)
                .orElseThrow(()->new DataNotFoundException("Cannot find place"));

        return roomDetailRepository.findAllRoomInPlace(placeId);
    }
}

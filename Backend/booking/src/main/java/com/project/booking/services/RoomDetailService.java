package com.project.booking.services;

import com.project.booking.dtos.RoomDetailDTO;
import com.project.booking.exceptions.DataNotFoundException;
import com.project.booking.models.Place;
import com.project.booking.models.RoomDetail;
import com.project.booking.repositories.PlaceRepository;
import com.project.booking.repositories.RoomDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomDetailService {
    private final PlaceRepository placeRepository;
    private final RoomDetailRepository roomDetailRepository;

    public RoomDetail createRoom(RoomDetailDTO roomDetailDTO){
        Place existingPlace = placeRepository.findById(roomDetailDTO.getPlaceId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find room with id: " + roomDetailDTO.getPlaceId()));
        RoomDetail newRoom = RoomDetail.builder()
                .address(roomDetailDTO.getAddress())
                .perks(roomDetailDTO.getPerks())
                .photo(roomDetailDTO.getPhoto())
                .title(roomDetailDTO.getTitle())
                .price(roomDetailDTO.getPrice())
                .maxGuests(roomDetailDTO.getMaxGuests())
                .checkIn(roomDetailDTO.getCheckIn())
                .checkOut(roomDetailDTO.getCheckOut())
                .description(roomDetailDTO.getDescription())
                .address(roomDetailDTO.getAddress())
                .place(existingPlace)
                .extraInfo(roomDetailDTO.getExtraInfo())
                .build();
        return roomDetailRepository.save(newRoom);
    }

    public List<RoomDetail> getAllRoomOfPlace(Long placeId){
        Place existingPlace = placeRepository.findById(placeId)
                .orElseThrow(()->new DataNotFoundException("Cannot find place"));

        return roomDetailRepository.findAllRoomInPlace(placeId);
    }
}

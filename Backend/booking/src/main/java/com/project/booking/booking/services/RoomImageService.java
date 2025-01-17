package com.project.booking.services;

import com.project.booking.models.RoomDetail;
import com.project.booking.models.RoomImage;
import com.project.booking.repositories.RoomImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomImageService {
    private final RoomImageRepository roomImageRepository;

    public RoomImage uploadRoomImage(RoomImage roomImage){
        return roomImageRepository.save(roomImage);
    }
}

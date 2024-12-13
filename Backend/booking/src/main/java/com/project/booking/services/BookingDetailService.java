package com.project.booking.services;

import com.project.booking.dtos.BookingDetailDTO;
import com.project.booking.exceptions.DataNotFoundException;
import com.project.booking.models.BookingDetail;
import com.project.booking.models.RoomDetail;
import com.project.booking.models.Users;
import com.project.booking.repositories.BookingDetailRepository;
import com.project.booking.repositories.RoomDetailRepository;
import com.project.booking.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingDetailService {
    private final BookingDetailRepository bookingDetailRepository;
    private final UsersRepository usersRepository;
    private final RoomDetailRepository roomDetailRepository;

    public BookingDetail createBookingForm(BookingDetailDTO bookingDetailDTO){
        Users existingUser = usersRepository.findById(bookingDetailDTO.getUserId())
                .orElseThrow(()->new DataNotFoundException(
                        "Cannot find user with id: " + bookingDetailDTO.getUserId()));
        RoomDetail existingRoom = roomDetailRepository.findById(bookingDetailDTO.getRoomId())
                .orElseThrow(() -> new DataNotFoundException("Room not exist"));
        BookingDetail newForm = BookingDetail.builder()
                .userId(existingUser)
                .roomId(existingRoom)
                .checkIn(bookingDetailDTO.getCheckIn())
                .checkOut(bookingDetailDTO.getCheckOut())
                .name(bookingDetailDTO.getName())
                .phone(bookingDetailDTO.getPhone())
                .email(bookingDetailDTO.getEmail())
                .specialRequest(bookingDetailDTO.getSpecialRequest())
                .build();
        return bookingDetailRepository.save(newForm);
    }
}

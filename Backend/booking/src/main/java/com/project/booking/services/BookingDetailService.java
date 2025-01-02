package com.project.booking.services;

import com.project.booking.dtos.BookingDetailDTO;
import com.project.booking.exceptions.DataNotFoundException;
import com.project.booking.models.BookingDetail;
import com.project.booking.models.RoomDetail;
import com.project.booking.models.Users;
import com.project.booking.repositories.BookingDetailRepository;
import com.project.booking.repositories.RoomDetailRepository;
import com.project.booking.repositories.UsersRepository;
import com.project.booking.response.BookingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingDetailService {
    private final BookingDetailRepository bookingDetailRepository;
    private final UsersRepository usersRepository;
    private final RoomDetailRepository roomDetailRepository;

    public BookingDetail createBookingForm(BookingDetailDTO bookingDetailDTO) throws Exception {
        Users existingUser = usersRepository.findById(bookingDetailDTO.getUserId())
                .orElseThrow(()->new DataNotFoundException(
                        "Cannot find user with id: " + bookingDetailDTO.getUserId()));
        RoomDetail existingRoom = roomDetailRepository.findById(bookingDetailDTO.getRoomId())
                .orElseThrow(() -> new DataNotFoundException("Room not exist"));
        if(existingRoom.getAvailable() <= 0){
            throw new DataNotFoundException("Room is not available");
        }
        if(bookingDetailDTO.getCheckOut().isBefore(bookingDetailDTO.getCheckIn())){
            throw new Exception("Ngày nhận phòng phải trước ngày trả phòng");
        }
        BookingDetail newForm = BookingDetail.builder()
                .userId(existingUser)
                .roomId(existingRoom)
                .checkIn(bookingDetailDTO.getCheckIn())
                .checkOut(bookingDetailDTO.getCheckOut())
                .name(existingUser.getFirstName() + " " + existingUser.getLastName())
                .phone(bookingDetailDTO.getPhone())
                .email(existingUser.getEmail())
                .specialRequest(bookingDetailDTO.getSpecialRequest())
                .roomName(existingRoom.getRoomName())
                .numberOfRoom(bookingDetailDTO.getRequireRoom())
                .build();
        existingRoom.setAvailable(existingRoom.getAvailable() - newForm.getNumberOfRoom());
        return bookingDetailRepository.save(newForm);
    }

    public BookingResponse getBookingForm(Long id){
        BookingDetail bookingDetail = bookingDetailRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Cannot find form"));
        Long totalDate = bookingDetailRepository.getDateDiffById(id);
        Long totalPrice = totalDate * bookingDetail.getRoomId().getPricePerNight() * bookingDetail.getNumberOfRoom();
        return BookingResponse.fromBooking(bookingDetail, totalPrice);
    }

    public void deleteBookingForm(Long formId){
        BookingDetail bookingDetail = bookingDetailRepository.findById(formId)
                .orElseThrow(()-> new DataNotFoundException("Cannot find form"));
        bookingDetail.getRoomId().setAvailable(bookingDetail.getRoomId().getAvailable() + bookingDetail.getNumberOfRoom());
        bookingDetailRepository.deleteById(formId);
    }


}

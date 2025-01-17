package com.project.booking.controllers;

import com.project.booking.dtos.BookingDetailDTO;
import com.project.booking.models.BookingDetail;
import com.project.booking.models.RoomDetail;
import com.project.booking.models.Users;
import com.project.booking.repositories.BookingDetailRepository;
import com.project.booking.repositories.RoomDetailRepository;
import com.project.booking.response.BookingResponse;
import com.project.booking.services.BookingDetailService;
import com.project.booking.services.IUserService;
import com.project.booking.services.RoomDetailService;
import com.project.booking.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/booking")
@Controller
public class BookingDetailController {
    private final BookingDetailService bookingDetailService;
    private final RoomDetailService roomDetailService;
    private final UserService userService;

    @PostMapping("/room/{roomId}")
    public String createForm(
            @PathVariable("roomId") Long roomId,
            @Valid @ModelAttribute BookingDetailDTO bookingDetailDTO,
            @RequestParam("numberOfRoom") Long numberOfRoom,
            BindingResult result,
            Model model,
            HttpSession httpSession
    ){
        if(httpSession.getAttribute("user") == null){
            model.addAttribute("error", "Bạn phải đăng nhập trước khi đặt phòng");
            return "redirect:/users/login";
        }

        Users loggedInUser = (Users) httpSession.getAttribute("user");
        bookingDetailDTO.setUserId(loggedInUser.getId());
        bookingDetailDTO.setRoomId(roomId);
        bookingDetailDTO.setRequireRoom(numberOfRoom);

        RoomDetail roomDetail = roomDetailService.getRoomById(roomId);
        bookingDetailDTO.setRoomName(roomDetail.getRoomName());

        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError:: getDefaultMessage).toList();
                model.addAttribute("errors", errorMessages);
                return "FormBooking";
            }
            //System.out.println("BookingDetailDTO: " + bookingDetailDTO);

            BookingDetail bookingDetail = bookingDetailService.createBookingForm(bookingDetailDTO);
            return "redirect:/booking/" + bookingDetail.getUserId().getId();
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "FormBooking";
        }
    }

    @GetMapping("/room/{roomId}")
    public String Booking(
            @PathVariable("roomId") Long roomId,
            @RequestParam("numberOfRoom") Long numberOfRoom,
            Model model,
            HttpSession httpSession){
        if(httpSession.getAttribute("user") == null){
            model.addAttribute("error", "Bạn phải đăng nhập trước khi đặt phòng");
            return "redirect:/users/login";
        }

        Users loggedInUser = (Users) httpSession.getAttribute("user");
        BookingDetailDTO bookingDetailDTO = new BookingDetailDTO();
        bookingDetailDTO.setUserId(loggedInUser.getId());
        bookingDetailDTO.setRoomId(roomId);
        bookingDetailDTO.setRequireRoom(numberOfRoom);

        RoomDetail roomDetail = roomDetailService.getRoomById(roomId);
        bookingDetailDTO.setRoomName(roomDetail.getRoomName());
        //System.out.println("BookingDetailDTO: " + bookingDetailDTO);

        model.addAttribute("bookingDetailDTO", bookingDetailDTO);
        return "FormBooking";
    }

    @GetMapping("/{userId}")
    public String getForm(@PathVariable("userId") Long userId, Model model, HttpSession httpSession){
        if(httpSession.getAttribute("user") == null){
            model.addAttribute("error", "Bạn phải đăng nhập trước khi đặt phòng");
            return "redirect:/users/login";
        }

        try{

            Users users = userService.getUserById(userId);
            List<BookingResponse>  bookingResponse = bookingDetailService.getAllBookingOfUser(userId);

            model.addAttribute("users", users);
            model.addAttribute("bookingResponse", bookingResponse);
            //return ResponseEntity.ok(bookingResponse);
            return "ThongKeBooking";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "ThongKeBooking";
            //return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{formId}")
    public ResponseEntity<?> deleteForm(@PathVariable("formId") Long formId){
        try{
            bookingDetailService.deleteBookingForm(formId);
            return ResponseEntity.ok().body("{\"message\": \"Xóa phòng thành công!\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllBookingOfUser(@PathVariable("userId") Long userId){
        try{
            List<BookingResponse> bookingResponseList = bookingDetailService.getAllBookingOfUser(userId);
            return ResponseEntity.ok(bookingResponseList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

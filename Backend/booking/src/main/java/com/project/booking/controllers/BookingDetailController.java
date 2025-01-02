package com.project.booking.controllers;

import com.project.booking.dtos.BookingDetailDTO;
import com.project.booking.models.BookingDetail;
import com.project.booking.models.RoomDetail;
import com.project.booking.models.Users;
import com.project.booking.repositories.BookingDetailRepository;
import com.project.booking.repositories.RoomDetailRepository;
import com.project.booking.response.BookingResponse;
import com.project.booking.services.BookingDetailService;
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

    @PostMapping("")
    public String createForm(
            @Valid @ModelAttribute BookingDetailDTO bookingDetailDTO,
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
        System.out.println("Logged in user ID: " + bookingDetailDTO.getUserId());

        //Test only
        bookingDetailDTO.setRoomId(8L);
        bookingDetailDTO.setRequireRoom(1L);

        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError:: getDefaultMessage).toList();
                model.addAttribute("errors", errorMessages);
                return "FormBooking";
            }
            BookingDetail bookingDetail = bookingDetailService.createBookingForm(bookingDetailDTO);
           return "redirect:/booking/" + bookingDetail.getId();
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "FormBooking";
        }
    }

    @GetMapping("")
    public String Booking(Model model, HttpSession httpSession){
        if(httpSession.getAttribute("user") == null){
            model.addAttribute("error", "Bạn phải đăng nhập trước khi đặt phòng");
            return "redirect:/users/login";
        }

        Users loggedInUser = (Users) httpSession.getAttribute("user");
        BookingDetailDTO bookingDetailDTO = new BookingDetailDTO();
        bookingDetailDTO.setUserId(loggedInUser.getId());
        System.out.println("Logged in user ID: " + bookingDetailDTO.getUserId());

        //Test only
        bookingDetailDTO.setRoomId(8L);

        model.addAttribute("bookingDetailDTO", bookingDetailDTO);
        return "FormBooking";
    }

    @GetMapping("/{formId}")
    public String getForm(@PathVariable("formId") Long formId, Model model, HttpSession httpSession){
        if(httpSession.getAttribute("user") == null){
            model.addAttribute("error", "Bạn phải đăng nhập trước khi đặt phòng");
            return "redirect:/users/login";
        }

        try{

            BookingResponse  bookingResponse = bookingDetailService.getBookingForm(formId);
            if (bookingResponse.getId() == null) {
                throw new IllegalArgumentException("ID is missing in the BookingResponse object.");
            }

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
            return ResponseEntity.ok("Delete successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

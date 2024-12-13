package com.project.booking.controllers;

import com.project.booking.dtos.PlacesDTO;
import com.project.booking.models.Place;
import com.project.booking.models.RoomDetail;
import com.project.booking.services.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
public class PlaceController {
    private final PlaceService placeService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadPlace(
            @RequestBody PlacesDTO placesDTO,
            BindingResult result
    ){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError:: getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Place newPlace = placeService.addPlace(placesDTO);
            return ResponseEntity.ok(newPlace);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("") //http://localhost:8088/api/v1/pictures
    public ResponseEntity<List<Place>> getAllPlace(
            @RequestParam("Page")int page, //number of Page
            @RequestParam("Limit") int limit //number of place in page
    ){
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("ranking").ascending());
        Page<Place> placePage = placeService.getAllPlace(pageRequest);
        int totalPages = placePage.getTotalPages();
        List<Place> places = placePage.getContent();
        return ResponseEntity.ok(places);
    }
}

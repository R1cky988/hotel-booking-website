package com.project.booking.services;

import com.project.booking.dtos.PlacesDTO;
import com.project.booking.models.Place;
import com.project.booking.repositories.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public Place addPlace(PlacesDTO placesDTO){
        Place newPlace = Place.builder()
                .type(placesDTO.getType())
                .name(placesDTO.getName())
                .ranking(placesDTO.getRanking())
                .ownerName(placesDTO.getOwnerName())
                .ownerPhone(placesDTO.getOwnerPhone())
                .description(placesDTO.getDescription())
                .build();
        return placeRepository.save(newPlace);
    }

    public Page<Place> getAllPlace(PageRequest pageRequest){
        return placeRepository.findAll(pageRequest);
    }
}

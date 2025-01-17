package com.project.booking.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Address {
    @Column(name = "full_address")
    private String full;

    @Column(name = "postal_code")
    private String postalCode;

    private String street;

    private String country;

    private String region;
}

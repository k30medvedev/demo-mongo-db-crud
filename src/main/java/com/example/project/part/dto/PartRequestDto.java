package com.example.project.part.dto;

import lombok.Builder;

@Builder
public record PartRequestDto(String partName,
                             String partNumber,
                             String carMake,
                             String carModel,
                             int year,
                             double price,
                             int stock,
                             String description,
                             String category) {
}

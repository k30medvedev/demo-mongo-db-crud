package com.example.project.user.dto;

import lombok.Builder;

@Builder
public record PartResponseDto(String uuid,
                              String partName,
                              String partNumber,
                              String carMake,
                              String carModel,
                              int year,
                              double price,
                              int stock,
                              String description,
                              String category) {
}

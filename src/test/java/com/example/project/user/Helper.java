package com.example.project.user;

import com.example.project.user.dto.PartRequestDto;
import com.example.project.user.dto.PartResponseDto;
import com.example.project.user.entity.Part;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Helper {

    public static String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    public static PartRequestDto requestDto = PartRequestDto.builder()
            .partName("Test Part")
            .partNumber("ABC-123")
            .carMake("Honda")
            .carModel("Civic")
            .year(2023)
            .price(25.99)
            .stock(10)
            .description("A high-quality replacement part")
            .category("Engine Parts")
            .build();

    public static PartRequestDto invalidRequestDto = PartRequestDto.builder()
            .partName("")
            .build();

    public static Part part = new Part(
            "Test Part",
            "ABC-123",
            "Honda",
            "Civic",
            2023,
            25.99,
            10,
            "A high-quality replacement part",
            "Engine Parts"
    );

    public static PartResponseDto responseDto = new PartResponseDto(
            "test-id",
            "Test Part",
            "ABC-123",
            "Honda",
            "Civic",
            2023,
            25.99,
            10,
            "A high-quality replacement part",
            "Engine Parts"
    );

    public static Part savedPart = new Part(
            "Test Part",
            "ABC-123",
            "Honda",
            "Civic",
            2023,
            25.99,
            10,
            "A high-quality replacement part",
            "Engine Parts"
    );
}

package com.example.project.user.service;

import com.example.project.user.dto.PartRequestDto;
import com.example.project.user.dto.PartResponseDto;
import com.example.project.user.entity.Part;
import com.example.project.user.mapper.PartMapper;
import com.example.project.user.repository.PartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class PartServiceImplTest {

    private PartRepository partRepository;
    private PartMapper mapper;
    private MongoTemplate mongoTemplate;
    private PartServiceImpl partService;

    @BeforeEach
    void setUp() {
        partRepository = Mockito.mock(PartRepository.class);
        mapper = Mockito.mock(PartMapper.class);
        mongoTemplate = Mockito.mock(MongoTemplate.class);
        partService = new PartServiceImpl(partRepository, mongoTemplate, mapper);
    }

    @Test
    public void testSavePart() {
        PartRequestDto requestDto = PartRequestDto.builder()
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

        Part part = new Part(
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

        PartResponseDto responseDto = new PartResponseDto(
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

        Part savedPart = new Part(
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


        when(mapper.toPart(requestDto)).thenReturn(part);
        when(partRepository.save(part)).thenReturn(savedPart);
        when(mapper.toPartResponseDto(savedPart)).thenReturn(responseDto);

        PartResponseDto response = partService.savePart(requestDto);

        assertNotNull(response);
        assertEquals(response, responseDto);
    }
}
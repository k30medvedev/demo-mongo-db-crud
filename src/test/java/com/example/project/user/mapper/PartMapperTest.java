package com.example.project.user.mapper;

import com.example.project.user.dto.PartRequestDto;
import com.example.project.user.dto.PartResponseDto;
import com.example.project.user.entity.Part;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PartMapperTest {

    private PartMapper partMapper;

    @BeforeEach
    void setUp() {
        partMapper = new PartMapper();
    }

    @Test
    void testToPart() {
        PartRequestDto dto = new PartRequestDto("partName", "partNumber", "carMake", "carModel", 2020, 100.500, 10, "description", "category");

        Part part = partMapper.toPart(dto);

        assertNotNull(part);
        assertEquals(dto.partName(), part.getPartName());
        assertEquals(dto.partNumber(), part.getPartNumber());
        assertEquals(dto.carMake(), part.getCarMake());
        assertEquals(dto.carModel(), part.getCarModel());
        assertEquals(dto.year(), part.getYear());
        assertEquals(dto.price(), part.getPrice());
        assertEquals(dto.stock(), part.getStock());
        assertEquals(dto.description(), part.getDescription());
        assertEquals(dto.category(), part.getCategory());
    }

    @Test
    void testToPartResponseDto() {
        Part part = new Part("partName", "partNumber", "carMake", "carModel", 2020, 100.500, 10, "description", "category");
        part.setUUID("1"); // assuming setId is a method to set the ID for testing purposes

        PartResponseDto dto = partMapper.toPartResponseDto(part);

        assertNotNull(dto);
        assertEquals(part.getUUID(), dto.uuid());
        assertEquals(part.getPartName(), dto.partName());
        assertEquals(part.getPartNumber(), dto.partNumber());
        assertEquals(part.getCarMake(), dto.carMake());
        assertEquals(part.getCarModel(), dto.carModel());
        assertEquals(part.getYear(), dto.year());
        assertEquals(part.getPrice(), dto.price());
        assertEquals(part.getStock(), dto.stock());
        assertEquals(part.getDescription(), dto.description());
        assertEquals(part.getCategory(), dto.category());
    }

    @Test
    void testToPartResponseDtos() {
        Part part1 = new Part("partName1", "partNumber1", "carMake1", "carModel1", 2020, 100.500, 10, "description1", "category1");
        Part part2 = new Part("partName2", "partNumber2", "carMake2", "carModel2", 2021, 100.500, 5, "description2", "category2");
        part1.setUUID("1");
        part2.setUUID("2");

        List<Part> parts = Arrays.asList(part1, part2);

        List<PartResponseDto> dtos = partMapper.toPartResponseDtos(parts);

        assertNotNull(dtos);
        assertEquals(2, dtos.size());

        assertEquals(part1.getUUID(), dtos.get(0).uuid());
        assertEquals(part1.getPartName(), dtos.get(0).partName());
        assertEquals(part1.getPartNumber(), dtos.get(0).partNumber());
        assertEquals(part1.getCarMake(), dtos.get(0).carMake());
        assertEquals(part1.getCarModel(), dtos.get(0).carModel());
        assertEquals(part1.getYear(), dtos.get(0).year());
        assertEquals(part1.getPrice(), dtos.get(0).price());
        assertEquals(part1.getStock(), dtos.get(0).stock());
        assertEquals(part1.getDescription(), dtos.get(0).description());
        assertEquals(part1.getCategory(), dtos.get(0).category());

        assertEquals(part2.getUUID(), dtos.get(1).uuid());
        assertEquals(part2.getPartName(), dtos.get(1).partName());
        assertEquals(part2.getPartNumber(), dtos.get(1).partNumber());
        assertEquals(part2.getCarMake(), dtos.get(1).carMake());
        assertEquals(part2.getCarModel(), dtos.get(1).carModel());
        assertEquals(part2.getYear(), dtos.get(1).year());
        assertEquals(part2.getPrice(), dtos.get(1).price());
        assertEquals(part2.getStock(), dtos.get(1).stock());
        assertEquals(part2.getDescription(), dtos.get(1).description());
        assertEquals(part2.getCategory(), dtos.get(1).category());
    }

    @Test
    void testPartToList() {
        PartRequestDto dto1 = new PartRequestDto("partName1", "partNumber1", "carMake1", "carModel1", 2020, 100.500, 10, "description1", "category1");
        PartRequestDto dto2 = new PartRequestDto("partName2", "partNumber2", "carMake2", "carModel2", 2021, 100.500, 5, "description2", "category2");

        List<PartRequestDto> dtos = Arrays.asList(dto1, dto2);

        List<Part> parts = partMapper.partToList(dtos);

        assertNotNull(parts);
        assertEquals(2, parts.size());

        assertEquals(dto1.partName(), parts.get(0).getPartName());
        assertEquals(dto1.partNumber(), parts.get(0).getPartNumber());
        assertEquals(dto1.carMake(), parts.get(0).getCarMake());
        assertEquals(dto1.carModel(), parts.get(0).getCarModel());
        assertEquals(dto1.year(), parts.get(0).getYear());
        assertEquals(dto1.price(), parts.get(0).getPrice());
        assertEquals(dto1.stock(), parts.get(0).getStock());
        assertEquals(dto1.description(), parts.get(0).getDescription());
        assertEquals(dto1.category(), parts.get(0).getCategory());

        assertEquals(dto2.partName(), parts.get(1).getPartName());
        assertEquals(dto2.partNumber(), parts.get(1).getPartNumber());
        assertEquals(dto2.carMake(), parts.get(1).getCarMake());
        assertEquals(dto2.carModel(), parts.get(1).getCarModel());
        assertEquals(dto2.year(), parts.get(1).getYear());
        assertEquals(dto2.price(), parts.get(1).getPrice());
        assertEquals(dto2.stock(), parts.get(1).getStock());
        assertEquals(dto2.description(), parts.get(1).getDescription());
        assertEquals(dto2.category(), parts.get(1).getCategory());
    }

    @Test
    void testToPartResponseDtoList() {
        Part part1 = new Part("partName1", "partNumber1", "carMake1", "carModel1", 2020, 100.500, 10, "description1", "category1");
        Part part2 = new Part("partName2", "partNumber2", "carMake2", "carModel2", 2021, 100.500, 5, "description2", "category2");
        part1.setUUID("1");
        part2.setUUID("2");

        List<Part> parts = Arrays.asList(part1, part2);

        List<PartResponseDto> dtos = partMapper.toPartResponseDtoList(parts);

        assertNotNull(dtos);
        assertEquals(2, dtos.size());

        assertEquals(part1.getUUID(), dtos.get(0).uuid());
        assertEquals(part1.getPartName(), dtos.get(0).partName());
        assertEquals(part1.getPartNumber(), dtos.get(0).partNumber());
        assertEquals(part1.getCarMake(), dtos.get(0).carMake());
        assertEquals(part1.getCarModel(), dtos.get(0).carModel());
        assertEquals(part1.getYear(), dtos.get(0).year());
        assertEquals(part1.getPrice(), dtos.get(0).price());
        assertEquals(part1.getStock(), dtos.get(0).stock());
        assertEquals(part1.getDescription(), dtos.get(0).description());
        assertEquals(part1.getCategory(), dtos.get(0).category());

        assertEquals(part2.getUUID(), dtos.get(1).uuid());
        assertEquals(part2.getPartName(), dtos.get(1).partName());
        assertEquals(part2.getPartNumber(), dtos.get(1).partNumber());
        assertEquals(part2.getCarMake(), dtos.get(1).carMake());
        assertEquals(part2.getCarModel(), dtos.get(1).carModel());
        assertEquals(part2.getYear(), dtos.get(1).year());
        assertEquals(part2.getPrice(), dtos.get(1).price());
        assertEquals(part2.getStock(), dtos.get(1).stock());
        assertEquals(part2.getDescription(), dtos.get(1).description());
        assertEquals(part2.getCategory(), dtos.get(1).category());
    }
}

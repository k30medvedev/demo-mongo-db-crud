package com.example.project.user.mapper;

import com.example.project.user.dto.PartRequestDto;
import com.example.project.user.dto.PartResponseDto;
import com.example.project.user.entity.Part;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PartMapper {
    public Part toPart(PartRequestDto partRequestDto) {
        return new Part(
                partRequestDto.partName(),
                partRequestDto.partNumber(),
                partRequestDto.carMake(),
                partRequestDto.carModel(),
                partRequestDto.year(),
                partRequestDto.price(),
                partRequestDto.stock(),
                partRequestDto.description(),
                partRequestDto.category()
        );
    }

    public PartResponseDto toPartResponseDto(@NotNull Part part) {
        return PartResponseDto.builder()
                .uuid(part.getUUID())
                .partName(part.getPartName())
                .partNumber(part.getPartNumber())
                .carMake(part.getCarMake())
                .carModel(part.getCarModel())
                .year(part.getYear())
                .price(part.getPrice())
                .stock(part.getStock())
                .description(part.getDescription())
                .category(part.getCategory())
                .build();
    }

    public List<PartResponseDto> toPartResponseDtos(@NotNull List<Part> parts) {
        return parts.stream()
                .map(this::toPartResponseDto)
                .collect(Collectors.toList());
    }


    public List<Part> partToList(@NotNull List<PartRequestDto> parts) {
        return parts.stream()
                .map(this::toPart)
                .collect(Collectors.toList());
    }

    public List<PartResponseDto> toPartResponseDtoList(@NotNull List<Part> savedList) {
        return savedList.stream()
                .map(this::toPartResponseDto)
                .collect(Collectors.toList());
    }


}

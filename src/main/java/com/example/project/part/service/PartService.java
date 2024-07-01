package com.example.project.part.service;

import com.example.project.part.dto.PartRequestDto;
import com.example.project.part.dto.PartResponseDto;
import com.example.project.part.dto.UpdateRequestDto;

import java.util.List;
import java.util.Map;

public interface PartService {

    List<PartResponseDto> getAllParts();

    PartResponseDto savePart(PartRequestDto part);

    List<PartResponseDto> saveAll(List<PartRequestDto> parts);

    List<PartResponseDto> findAllByIds(List<String> ids);

    PartResponseDto findById(String id);

    List<PartResponseDto> searchPartsByFilters(Map<String, String> filters);

    void decreaseStock(String id, int quantity);

    void updatePartsByFilter(List<UpdateRequestDto> updateRequest);

    Long getCount();

    void deleteParts(List<String> ids);

}

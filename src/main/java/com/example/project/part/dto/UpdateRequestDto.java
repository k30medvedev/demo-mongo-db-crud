package com.example.project.part.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Builder
public record UpdateRequestDto(@NotNull String uuid,
                               @NotBlank Map<String, String> values) {
}

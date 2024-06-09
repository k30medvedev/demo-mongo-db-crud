package com.example.project.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Builder
public record UpdateRequestDto(@NotNull String id, @NotBlank Map<String, String> values) {
}

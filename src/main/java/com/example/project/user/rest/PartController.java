package com.example.project.user.rest;

import com.example.project.user.dto.PartRequestDto;
import com.example.project.user.dto.PartResponseDto;
import com.example.project.user.dto.UpdateRequestDto;
import com.example.project.user.service.PartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/parts")
@Validated
@Tag(name = "Demo", description = "Demo APIs for MongoDb project")
public class PartController {

    private final PartService partService;

    public PartController(PartService partService) {
        this.partService = partService;
    }

    @Operation(summary = "Create a new part")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PartResponseDto.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    public PartResponseDto createPart(@RequestBody PartRequestDto part) {
        return partService.savePart(part);
    }

    @Operation(summary = "Get all parts")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PartResponseDto.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    public List<PartResponseDto> getAllParts() {
        return partService.getAllParts();
    }

    @Operation(summary = "Save multiple parts")
    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PartResponseDto.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    public List<PartResponseDto> saveAll(@RequestBody @NotNull List<PartRequestDto> parts) {
        return partService.saveAll(parts);
    }

    @Operation(summary = "Get part by ID")
    @GetMapping("/search/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PartResponseDto.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    public PartResponseDto getPartById(@PathVariable("id") @NotNull String id) {
        return partService.findById(id);
    }

    @Operation(summary = "Get parts by list of IDs")
    @GetMapping("/{ids}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PartResponseDto.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    public List<PartResponseDto> getPartByIds(@PathVariable("ids") @NotNull List<String> ids) {
        return partService.findAllByIds(ids);
    }

    @Operation(summary = "Search parts by filters")
    @GetMapping("/search")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PartResponseDto.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    public List<PartResponseDto> searchParts(@RequestBody @NotBlank Map<String, String> filters) {
        return partService.searchPartsByFilters(filters);
    }

    @Operation(summary = "Decrease stock of a part")
    @PatchMapping("/{id}/decreaseStock")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PartResponseDto.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    public void decreaseStock(@PathVariable String id, @RequestParam int quantity) {
        partService.decreaseStock(id, quantity);
    }

    @Operation(summary = "Update part by filters")
    @PutMapping
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PartResponseDto.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    public void updateByFilter(@RequestBody @NotNull List<UpdateRequestDto> filters) {
        partService.updatePartsByFilter(filters);
    }


    @GetMapping("/count")
    @Operation(summary = "Count all documents")
    @PutMapping
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PartResponseDto.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    public Long getCount() {
        return partService.getCount();
    }

    @DeleteMapping
    @Operation(summary = "Delete all documents")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = PartResponseDto.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    public void deleteAllById(@RequestBody @NotBlank List<String> ids) {
        partService.deleteParts(ids);
    }

}
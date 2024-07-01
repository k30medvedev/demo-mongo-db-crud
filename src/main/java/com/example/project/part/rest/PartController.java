package com.example.project.part.rest;

import com.example.project.part.dto.PartRequestDto;
import com.example.project.part.dto.PartResponseDto;
import com.example.project.part.dto.UpdateRequestDto;
import com.example.project.part.filter.RequestIdCache;
import com.example.project.part.rabbitmq.RabbitMQSender;
import com.example.project.part.service.PartService;
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
    private final RequestIdCache requestIdCache;
    private final RabbitMQSender rabbitMQSender;


    public PartController(PartService partService, RequestIdCache requestIdCache, RabbitMQSender rabbitMQSender) {
        this.partService = partService;
        this.requestIdCache = requestIdCache;
        this.rabbitMQSender = rabbitMQSender;
    }

    @Operation(summary = "Create a new part")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PartResponseDto.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    public PartResponseDto createPart(@RequestBody PartRequestDto part, @RequestHeader("RequestID") String requestId) {
        if (requestIdCache.isDuplicate(requestId)) {
            return requestIdCache.getCachedResponse(requestId);
        }

        PartResponseDto response = partService.savePart(part);
        requestIdCache.cacheResponse(requestId, response);
        rabbitMQSender.send(response);
        return response;
    }

    @Operation(summary = "Get all parts")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PartResponseDto.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    public List<PartResponseDto> getAllParts() {
        var response = partService.getAllParts();
        rabbitMQSender.send(response);
        return response;
    }

    @Operation(summary = "Save multiple parts")
    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PartResponseDto.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    public List<PartResponseDto> saveAll(@RequestBody @NotNull List<PartRequestDto> parts) {
        var response = partService.saveAll(parts);
        rabbitMQSender.send(response);
        return response;
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
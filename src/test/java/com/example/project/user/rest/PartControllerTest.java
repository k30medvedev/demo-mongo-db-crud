package com.example.project.user.rest;

import com.example.project.user.Helper;
import com.example.project.user.service.PartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PartControllerTest {
    private MockMvc mockMvc;

    @Mock
    private PartService partService;

    @InjectMocks
    private PartController partController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(partController).build();
    }

//    @Test
//    void testCreatePart() throws Exception {
//        PartRequestDto requestDto = Helper.requestDto;
//        PartResponseDto responseDto = Helper.responseDto;
//
//        when(partService.savePart(requestDto)).thenReturn(responseDto);
//
//        MvcResult result = mockMvc.perform(post("/api/v1/parts")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(requestDto)))
//                .andReturn();
//
//        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
//        assertThat(result.getResponse().getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
//        assertThat(jsonPath("$.uuid", result.getResponse().getContentAsString()).value("test-id"));
//        assertThat(jsonPath("$.partName", result.getResponse().getContentAsString()).value("Test Part"));
//
//    }

//    @Test
//    public void testGetAllPartsEmptyList() throws Exception {
//        var partList = List.of(Helper.responseDto);
//        when(partService.getAllParts()).thenReturn(partList);
//
//        MvcResult result = mockMvc.perform(get("/api/v1/parts"))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
//    }
//
//    @Test
//    public void testGetPartByIdNotFound() throws Exception {
//        String nonExistentId = "12345";
//        when(partService.findById(nonExistentId)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
//
//        mockMvc.perform(get("/api/v1/parts/search/{id}", nonExistentId))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void testSaveAllValidationError() throws Exception {
//
//        mockMvc.perform(post("/api/v1/parts/bulk")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(""))
//                .andExpect(status().isBadRequest());
//    }

}
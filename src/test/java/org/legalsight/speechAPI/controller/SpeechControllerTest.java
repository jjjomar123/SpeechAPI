package org.legalsight.speechAPI.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.legalsight.speechAPI.dto.SearchFilterDTO;
import org.legalsight.speechAPI.dto.SpeechDTO;
import org.legalsight.speechAPI.service.SpeechService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class SpeechControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SpeechService speechService;

    @InjectMocks
    private SpeechController speechController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(speechController).build();
    }

    @Test
    void testCreateSpeech() throws Exception {
        SpeechDTO requestDTO = new SpeechDTO();
        requestDTO.setAuthor("John Doe");
        requestDTO.setSubject("Law and Order");
        requestDTO.setSpeechText("This is a test speech.");
        requestDTO.setSpeechDate(LocalDate.now());

        when(speechService.save(any(SpeechDTO.class))).thenReturn(requestDTO);

        mockMvc.perform(post("/api/v1/speeches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "author": "John Doe",
                                        "subject": "Law and Order",
                                        "speechText": "This is a test speech.",
                                        "speechDate": "2024-01-31"
                                    }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("John Doe"))
                .andExpect(jsonPath("$.subject").value("Law and Order"));

        verify(speechService, times(1)).save(any(SpeechDTO.class));
    }

    @Test
    void testListSpeeches() throws Exception {
        SpeechDTO speechDTO = new SpeechDTO();
        speechDTO.setAuthor("Jane Doe");
        speechDTO.setSubject("Legal Ethics");

        when(speechService.search(any(SearchFilterDTO.class))).thenReturn(
                new PageImpl<>(List.of(speechDTO), PageRequest.of(0, 10), 1));

        mockMvc.perform(get("/api/v1/speeches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].author").value("Jane Doe"))
                .andExpect(jsonPath("$.content[0].subject").value("Legal Ethics"));

        verify(speechService, times(1)).search(any(SearchFilterDTO.class));
    }

    @Test
    void testGetSpeechById() throws Exception {
        SpeechDTO speechDTO = new SpeechDTO();
        speechDTO.setAuthor("Jane Doe");
        speechDTO.setSubject("Legal Ethics");
        String id = UUID.randomUUID().toString();
        when(speechService.findSpeechById(id)).thenReturn(speechDTO);

        mockMvc.perform(get("/api/v1/speeches/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("Jane Doe"))
                .andExpect(jsonPath("$.subject").value("Legal Ethics"));

        verify(speechService, times(1)).findSpeechById(id);
    }

    @Test
    void testUpdateSpeech() throws Exception {
        SpeechDTO requestDTO = new SpeechDTO();
        requestDTO.setAuthor("Alice Updated");
        requestDTO.setSubject("Updated Corporate Law");
        requestDTO.setSpeechText("Updated speech text.");
        requestDTO.setSpeechDate(LocalDate.now());
        String id = UUID.randomUUID().toString();
        when(speechService.update(any(SpeechDTO.class), eq(id))).thenReturn(requestDTO);

        mockMvc.perform(put("/api/v1/speeches/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "author": "Alice Updated",
                                        "subject": "Updated Corporate Law",
                                        "speechText": "Updated speech text.",
                                        "speechDate": "2024-01-31"
                                    }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("Alice Updated"))
                .andExpect(jsonPath("$.subject").value("Updated Corporate Law"));

        verify(speechService, times(1)).update(any(SpeechDTO.class), eq(id));
    }

    @Test
    void testDeleteSpeech() throws Exception {
        String id = UUID.randomUUID().toString();
        when(speechService.delete(id)).thenReturn("Speech deleted");

        mockMvc.perform(delete("/api/v1/speeches/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string("Speech deleted"));

        verify(speechService, times(1)).delete(id);
    }

    @Test
    void testSearchSpeeches() throws Exception {
        SpeechDTO speechDTO = new SpeechDTO();
        speechDTO.setAuthor("John Doe");
        speechDTO.setSubject("Law and Order");

        when(speechService.search(any(SearchFilterDTO.class))).thenReturn(
                new PageImpl<>(List.of(speechDTO), PageRequest.of(0, 10), 1));

        mockMvc.perform(get("/api/v1/speeches/search")
                        .param("author", "John Doe")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].author").value("John Doe"))
                .andExpect(jsonPath("$.content[0].subject").value("Law and Order"));

        verify(speechService, times(1)).search(any(SearchFilterDTO.class));
    }
}

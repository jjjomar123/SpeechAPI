package org.legalsight.speechAPI.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.legalsight.speechAPI.dto.SearchFilterDTO;
import org.legalsight.speechAPI.dto.SpeechDTO;
import org.legalsight.speechAPI.model.Speech;
import org.legalsight.speechAPI.service.SpeechService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/speeches")
@Tag(name = "Speech", description = "Speech management APIs")
@AllArgsConstructor
public class SpeechController {

    private final SpeechService speechService;

    @PostMapping
    @Operation(summary = "Create a new speech", description = "Adds a new speech to the system")
    @ApiResponse(responseCode = "201", description = "Speech created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input provided")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SpeechDTO> createSpeech(
            @Valid @RequestBody SpeechDTO speechDTO) {
        return ResponseEntity.ok(speechService.save(speechDTO));
    }

    @GetMapping
    @Operation(summary = "List all speeches", description = "Retrieves a paginated list of all speeches")
    @ApiResponse(responseCode = "200", description = "Speeches retrieved successfully")
    @ApiResponse(responseCode = "404", description = "No speeches found")
    public ResponseEntity<Page<SpeechDTO>> listSpeeches(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        Speech speech = new Speech();
        SearchFilterDTO searchFilterDTO = new SearchFilterDTO(speech);
        searchFilterDTO.setPage(page);
        searchFilterDTO.setSize(size);
        return ResponseEntity.ok(speechService.search(searchFilterDTO));
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get speech by ID", description = "Retrieves a specific speech by its ID")
    @ApiResponse(responseCode = "200", description = "Speech retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Speech not found")
    public ResponseEntity<SpeechDTO> getSpeechById(@PathVariable String id) {
        return ResponseEntity.ok(speechService.findSpeechById(id));
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Update a speech", description = "Updates an existing speech by its ID")
    @ApiResponse(responseCode = "200", description = "Speech updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input provided")
    @ApiResponse(responseCode = "404", description = "Speech not found")
    public ResponseEntity<SpeechDTO> updateSpeech(
            @PathVariable String id, @Valid @RequestBody SpeechDTO speechDTO) {
        return ResponseEntity.ok(speechService.update(speechDTO, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a speech", description = "Deletes a specific speech by its ID")
    @ApiResponse(responseCode = "200", description = "Speech deleted successfully")
    @ApiResponse(responseCode = "404", description = "Speech not found")
    public ResponseEntity<String> deleteSpeech(@PathVariable String id) {
        return ResponseEntity.ok(speechService.delete(id));
    }

    @GetMapping(value = "/search")
    @Operation(summary = "Search speeches", description = "Searches speeches based on filters")
    @ApiResponse(responseCode = "200", description = "Speeches retrieved successfully")
    @ApiResponse(responseCode = "404", description = "No speeches found")
    public ResponseEntity<Page<SpeechDTO>> searchSpeeches(
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "subject", required = false) String subject,
            @RequestParam(value = "dateFrom", required = false) LocalDate dateFrom,
            @RequestParam(value = "dateTo", required = false) LocalDate dateTo,
            @RequestParam(value = "speechText", required = false) String speechText,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        Speech speech = new Speech();
        speech.setAuthor(author);
        speech.setSubject(subject);
        speech.setSpeechText(speechText);
        SearchFilterDTO searchFilterDTO = new SearchFilterDTO(speech);
        searchFilterDTO.setPage(page);
        searchFilterDTO.setSize(size);
        searchFilterDTO.setDateFrom(dateFrom);
        searchFilterDTO.setDateTo(dateTo);
        return ResponseEntity.ok(speechService.search(searchFilterDTO));
    }
}
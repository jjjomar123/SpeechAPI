package org.legalsight.speechAPI.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpeechDTO extends CoreDTO {
    @NotEmpty
    @Schema(description = "Author", requiredMode = Schema.RequiredMode.REQUIRED)
    private String author;

    @NotEmpty
    @Schema(description = "Subject", requiredMode = Schema.RequiredMode.REQUIRED)
    private String subject;

    @Schema(description = "Speech Date", requiredMode = Schema.RequiredMode.REQUIRED)
    @Valid
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate speechDate;

    @Schema(description = "Speech", requiredMode = Schema.RequiredMode.REQUIRED)
    private String speechText;
}
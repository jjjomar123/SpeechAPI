package org.legalsight.speechAPI.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.legalsight.speechAPI.model.Speech;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class SearchFilterDTO {

  private int page;
  private int size;
  private LocalDate dateFrom;
  private LocalDate dateTo;
  private Speech speech;

  public SearchFilterDTO(final Speech speech) {
    this.speech = speech;
  }

}

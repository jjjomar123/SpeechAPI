package org.legalsight.speechAPI.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Speech extends CoreEntity {

  private String author;
  private String subject;
  private LocalDate speechDate;
  private String speechText;

}
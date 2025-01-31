package org.legalsight.speechAPI.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpeechNotFoundException extends CoreException {

  public SpeechNotFoundException(String errorCode, int errorStatus) {
    super(errorCode, errorStatus);
  }
}

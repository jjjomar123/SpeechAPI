package org.legalsight.speechAPI.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CoreException extends RuntimeException {

  private final String errorCode;
  private final int errorStatus;
}

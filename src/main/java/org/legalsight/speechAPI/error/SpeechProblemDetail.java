package org.legalsight.speechAPI.error;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ProblemDetail;

@Getter
@Setter
public class SpeechProblemDetail extends ProblemDetail {

    private String errorCode;
}

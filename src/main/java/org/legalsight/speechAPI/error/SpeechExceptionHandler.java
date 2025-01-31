package org.legalsight.speechAPI.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
@Slf4j
public class SpeechExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<SpeechProblemDetail> handleBadRequest(MethodArgumentNotValidException ex) {
        log.error("Invalid arguments found : `{}`", ex.getMessage());
        SpeechProblemDetail problem = new SpeechProblemDetail();
        problem.setStatus(HttpStatus.BAD_REQUEST);
        problem.setErrorCode("VALIDATION_ERROR");
        return ResponseEntity.status(problem.getStatus()).body(problem);
    }

    @ExceptionHandler({CoreException.class})
    public ResponseEntity<SpeechProblemDetail> handleSpeechException(CoreException speechException,
                                                                     HttpServletRequest theRequest) {
        log.error("Error occurred : `{}`", speechException.getMessage());
        SpeechProblemDetail problem = new SpeechProblemDetail();
        problem.setStatus(speechException.getErrorStatus());
        problem.setErrorCode(speechException.getErrorCode());
        URI instance = URI.create(theRequest.getRequestURI());
        problem.setInstance(instance);
        return ResponseEntity.status(problem.getStatus()).body(problem);
    }

}

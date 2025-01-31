package org.legalsight.speechAPI.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.legalsight.speechAPI.util.SpeechError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class SpeechExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<SpeechProblemDetail> handleBadRequest(MethodArgumentNotValidException ex) {
        log.error("Invalid arguments found : `{}`", ex.getMessage());
        SpeechProblemDetail problem = new SpeechProblemDetail();
        Map<String, String> fieldWthError = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldWthError.put(fieldName, errorMessage);
        });
        problem.setStatus(HttpStatus.BAD_REQUEST);
        problem.setErrorCode(fieldWthError.toString());
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

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<SpeechProblemDetail> handleNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Data conversion error : `{}`", ex.getMessage());
        SpeechProblemDetail problem = new SpeechProblemDetail();
        problem.setStatus(HttpStatus.BAD_REQUEST);
        problem.setErrorCode(SpeechError.CONVERSION_ERROR + " " + ex.getMessage());
        return ResponseEntity.status(problem.getStatus()).body(problem);
    }
}

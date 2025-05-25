package org.example.bookapprest.exception;

import jakarta.validation.ConstraintDefinitionException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends DefaultErrorAttributes {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handle(BookNotFoundException ex,
                                                      WebRequest request) {
        log.error("BookNotFoundException occurred {}", ex.getMessage());
        return ofType(request, HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handle(AuthorNotFoundException ex,
                                                      WebRequest request) {
        log.error("AuthorNotFoundException occurred {}", ex.getMessage());
        return ofType(request, HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Map<String, Object>> handle(SQLException ex,
                                                      WebRequest request) {
        log.error("SQLException occurred {}", ex.getMessage());
        return ofType(request, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }


    @ExceptionHandler(BookCantBeSavedException.class)
    public ResponseEntity<Map<String, Object>> handle(BookCantBeSavedException ex,
                                                      WebRequest request) {
        log.error("BookCantBeSavedException occurred {}", ex.getMessage());
        return ofType(request, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(LackOfParameterException.class)
    public ResponseEntity<Map<String, Object>> handle(LackOfParameterException ex,
                                                      WebRequest request) {
        log.error("LackOfParameterException occurred {}", ex.getMessage());
        return ofType(request, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(AuthorCantBeAddedException.class)
    public ResponseEntity<Map<String, Object>> handle(AuthorCantBeAddedException ex,
                                                      WebRequest request) {
        log.error("AuthorCantBeAddedException occurred {}", ex.getMessage());
        return ofType(request, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(BookAlreadyExistException.class)
    public ResponseEntity<Map<String, Object>> handle(BookAlreadyExistException ex,
                                                      WebRequest request) {
        log.error("BookAlreadyExistException occurred {}", ex.getMessage());
        return ofType(request, HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(AuthorAlreadyJoinedException.class)
    public ResponseEntity<Map<String, Object>> handle(AuthorAlreadyJoinedException ex,
                                                      WebRequest request) {
        log.error("AuthorAlreadyJoinedException occurred {}", ex.getMessage());
        return ofType(request, HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handle(ConstraintViolationException ex,
                                                      WebRequest request) {
        log.error("ConstraintViolationException occurred {}", ex.getMessage());

        List<Object> errors = new ArrayList<>();
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
        }

        return ofType(request, HttpStatus.BAD_REQUEST, ex.getMessage(), errors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handle(MethodArgumentNotValidException ex,
                                                      WebRequest request) {
        log.error("MethodArgumentNotValidException occurred {}", ex.getMessage());

        Map<String, String> errorsMap = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldNameString = ((FieldError) error).getField();
            String messageString = error.getDefaultMessage();
            errorsMap.put(fieldNameString, messageString);
        });

        return ofType(request, HttpStatus.BAD_REQUEST, ex.getMessage(), List.of(errorsMap));
    }

    protected ResponseEntity<Map<String, Object>> ofType(WebRequest request, HttpStatus status, String message) {
        return ofType(request, status, message, Collections.emptyList());
    }


    private ResponseEntity<Map<String, Object>> ofType(WebRequest request, HttpStatus status, String message,
                                                       List<Object> validationErrors) {
        Map<String, Object> attributes = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        attributes.put("status", status.value());
        attributes.put("error", status.getReasonPhrase());
        attributes.put("message", message);
        attributes.put("errors", validationErrors);
        attributes.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());
        return new ResponseEntity<>(attributes, status);
    }
}


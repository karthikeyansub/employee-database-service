package com.bank.employee.db.operation.exception;

import com.bank.employee.db.operation.domain.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(final Exception exception) {
        log.error("Resource not found exception - {}, stack trace: {}", exception.getMessage(), getStackTrace(exception));
        return ResponseEntity.internalServerError().body(new ErrorResponse(HttpStatus.NOT_FOUND.name(), exception.getMessage(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.warn("Missing required field - {}", exception.getMessage());
        BindingResult result = exception.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return ResponseEntity.badRequest().body(processFieldErrors(fieldErrors));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleGenericException(final Exception exception) {
        log.error("Internal server error - {}, stack trace: {}", exception.getMessage(), getStackTrace(exception));
        return ResponseEntity.internalServerError().body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(), "Something went wrong", null));
    }

    private ErrorResponse processFieldErrors(List<FieldError> fieldErrors) {
        List<String> errors = new ArrayList<>();
        for (org.springframework.validation.FieldError fieldError: fieldErrors) {
            errors.add(fieldError.getDefaultMessage());
        }
        return new ErrorResponse(HttpStatus.BAD_REQUEST.name(), "Input validation error", errors);
    }
}
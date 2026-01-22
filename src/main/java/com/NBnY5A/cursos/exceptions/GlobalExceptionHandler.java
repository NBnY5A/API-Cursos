package com.NBnY5A.cursos.exceptions;

import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CourseNotFoundException.class)
    public ProblemDetail handlerCourseNotFound(CourseNotFoundException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(TeacherNotFoundException.class)
    public ProblemDetail handlerTeacherNotFound(TeacherNotFoundException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handlerInvalidArgument(MethodArgumentNotValidException exception) {
        Map<String, String> details = getErrorsDetails(exception);

        List<Map<String, String>> listDetails = List.of(details);

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Bad request");
        problemDetail.setInstance(exception.getBody().getInstance());

        problemDetail.setProperty("timestamp", Instant.now().toString());
        problemDetail.setProperty("errors", listDetails);

        return problemDetail;
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ProblemDetail handlerUserAlreadyExists(UserAlreadyExistsException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    private Map<String, String> getErrorsDetails(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                   fieldMessage -> fieldMessage.getDefaultMessage()
                ));
    }
}

package com.luv2code.forumoverflow.rest.advice;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.luv2code.forumoverflow.exception.EmailAlreadyExistsException;
import com.luv2code.forumoverflow.exception.EntityNotFoundException;
import com.luv2code.forumoverflow.exception.ExceptionResponse;
import com.luv2code.forumoverflow.exception.NameAlreadyExistsException;
import com.luv2code.forumoverflow.exception.UsernameAlreadyExistsException;

/**
 * Created by lzugaj on Saturday, April 2020
 */

@ControllerAdvice
public class ApiExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception exception, WebRequest request) {
        ExceptionResponse exceptionResponse = getExceptionResponse(exception, request);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleEntityNotFoundException(Exception exception, WebRequest request) {
        ExceptionResponse exceptionResponse = getExceptionResponse(exception, request);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = NameAlreadyExistsException.class)
    public final ResponseEntity<ExceptionResponse> handleNameAlreadyExistsException(Exception exception, WebRequest request) {
        ExceptionResponse exceptionResponse = getExceptionResponse(exception, request);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UsernameAlreadyExistsException.class)
    public final ResponseEntity<ExceptionResponse> handleUsernameAlreadyExistsException(Exception exception, WebRequest request) {
        ExceptionResponse exceptionResponse = getExceptionResponse(exception, request);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EmailAlreadyExistsException.class)
    public final ResponseEntity<ExceptionResponse> handleEmailAlreadyExistsException(Exception exception, WebRequest request) {
        ExceptionResponse exceptionResponse = getExceptionResponse(exception, request);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    private ExceptionResponse getExceptionResponse(Exception exception, WebRequest request) {
        return new ExceptionResponse(
                exception.getMessage(),
                request.getDescription(false),
                LocalDateTime.now());
    }
}

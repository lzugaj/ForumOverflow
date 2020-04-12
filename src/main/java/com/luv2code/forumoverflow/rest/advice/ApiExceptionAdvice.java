package com.luv2code.forumoverflow.rest.advice;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.luv2code.forumoverflow.exception.EntityNotFoundException;
import com.luv2code.forumoverflow.exception.ExceptionResponse;
import com.luv2code.forumoverflow.exception.NameAlreadyExistsException;

/**
 * Created by lzugaj on Saturday, April 2020
 */

@ControllerAdvice
public class ApiExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception e, WebRequest request, HttpServletResponse response) {
        ExceptionResponse exceptionResponse = getExceptionResponse(e, request, response);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleEntityNotFoundException(Exception e, WebRequest request, HttpServletResponse response) {
        ExceptionResponse exceptionResponse = getExceptionResponse(e, request, response);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = NameAlreadyExistsException.class)
    public final ResponseEntity<ExceptionResponse> handleNameAlreadyExistsException(Exception e, WebRequest request, HttpServletResponse response) {
        ExceptionResponse exceptionResponse = getExceptionResponse(e, request, response);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    private ExceptionResponse getExceptionResponse(Exception e, WebRequest request, HttpServletResponse response) {
        return new ExceptionResponse(
                e.getMessage(),
                request.getDescription(false),
                LocalDateTime.now());
    }
}

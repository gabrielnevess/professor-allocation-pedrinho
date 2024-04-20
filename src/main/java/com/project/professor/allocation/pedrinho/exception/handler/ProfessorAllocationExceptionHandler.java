package com.project.professor.allocation.pedrinho.exception.handler;

import com.project.professor.allocation.pedrinho.exception.NotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ProfessorAllocationExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handlerBadRequestException(BadRequestException badRequestException,
                                                        WebRequest webRequest) {
        ErrorResponseExceptionHandler errorResponseExceptionHandler = new ErrorResponseExceptionHandler(new Date(), badRequestException.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponseExceptionHandler, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handlerNotFoundException(NotFoundException notFoundException, WebRequest webRequest) {
        ErrorResponseExceptionHandler errorResponseExceptionHandler = new ErrorResponseExceptionHandler(new Date(), notFoundException.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponseExceptionHandler, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerException(Exception exception, WebRequest webRequest) {
        ErrorResponseExceptionHandler errorResponseExceptionHandler = new ErrorResponseExceptionHandler(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponseExceptionHandler, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handlerDataIntegrityViolationException(DataIntegrityViolationException exception, WebRequest webRequest) {
        ErrorResponseExceptionHandler errorResponseExceptionHandler = new ErrorResponseExceptionHandler(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorResponseExceptionHandler, HttpStatus.BAD_REQUEST);
    }
}

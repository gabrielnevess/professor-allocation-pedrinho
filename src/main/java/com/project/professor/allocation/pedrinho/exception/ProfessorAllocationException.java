package com.project.professor.allocation.pedrinho.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProfessorAllocationException extends Exception {
    private HttpStatus statusCode;
    private String message;

    public ProfessorAllocationException(HttpStatus statusCode, String message) {
        super(message);
    }
}

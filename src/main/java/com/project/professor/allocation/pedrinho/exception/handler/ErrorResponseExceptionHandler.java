package com.project.professor.allocation.pedrinho.exception.handler;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ErrorResponseExceptionHandler {

    private String field;
    private Date timestamp;
    private String message;
    private String details;

    public ErrorResponseExceptionHandler(Date timestamp, String message) {
        super();
        this.timestamp = timestamp;
        this.message = message;
    }

    public ErrorResponseExceptionHandler(String field, Date timestamp, String message, String details) {
        super();
        this.field = field;
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public ErrorResponseExceptionHandler(Date timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

}

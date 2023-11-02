package com.subaxpress.lib.shared;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    private int code;

    private String status;

    private String message;

    private String stackTrace;

    private Object data;

    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(HttpStatus status, String message) {
        this();
        this.code = status.value();
        this.status = status.name();
        this.message = message;
    }

    public ErrorResponse(HttpStatus status, String message, String stackTrace) {
        this(status, message);
        this.stackTrace = stackTrace;
    }

    public ErrorResponse(HttpStatus status, String message, String stackTrace, Object data) {
        this(status, message, stackTrace);
        this.data = data;
    }
}

package com.daily_expenses.web.advice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonPropertyOrder({"status", "type", "title", "detail", "instance", "timestamp"})
public class ErrorResponse {

    private int status;
    private String type;
    private String title;
    private String detail;
    private String instance;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    public ErrorResponse(int status, String type, String title, String detail, String instance) {
        this.status = status;
        this.type = type;
        this.title = title;
        this.detail = detail;
        this.instance = instance;
        this.timestamp = LocalDateTime.now();
    }
}
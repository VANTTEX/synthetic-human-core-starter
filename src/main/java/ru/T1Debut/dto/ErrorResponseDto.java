package ru.T1Debut.dto;

import lombok.Data;

@Data
public class ErrorResponseDto {
    private String message;
    private String timestamp;
    private int code;

    public ErrorResponseDto(String message, String timestamp, int code) {
        this.message = message;
        this.timestamp = timestamp;
        this.code = code;
    }
}

package ru.T1Debut.dto;

import lombok.Data;

@Data
public class ErrorResponseDto {
    private String message;
    private String timestamp;

    public ErrorResponseDto(String message, String timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}

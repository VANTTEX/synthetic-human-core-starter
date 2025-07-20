package ru.T1Debut.service;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.T1Debut.dto.ErrorResponseDto;
import ru.T1Debut.exception.QueueOverflowException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidationHandler {

    @ExceptionHandler(QueueOverflowException.class)
    public ResponseEntity<ErrorResponseDto> handleQueueOverflow(QueueOverflowException e) {
        ErrorResponseDto error = new ErrorResponseDto(
                e.getMessage(),
                LocalDateTime.now().toString(),
                HttpStatus.SERVICE_UNAVAILABLE.value()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleAllException(Exception ex) {
        ErrorResponseDto error = new ErrorResponseDto(
                "Внутренняя ошибка сервера: " + ex.getMessage(),
                LocalDateTime.now().toString(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
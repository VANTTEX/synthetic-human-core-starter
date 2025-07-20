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

    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException e){
        String errorMessage = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ErrorResponseDto error = new ErrorResponseDto(errorMessage, LocalDateTime.now().toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(QueueOverflowException.class)
    public ResponseEntity<ErrorResponseDto> handleQueueOverflow(QueueOverflowException e) {
        ErrorResponseDto error = new ErrorResponseDto(e.getMessage(), LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleAllUncaughtException() {
        ErrorResponseDto error = new ErrorResponseDto("Внутренняя ошибка сервера", LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}

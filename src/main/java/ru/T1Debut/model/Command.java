package ru.T1Debut.model;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class Command {
    @NotBlank(message = "Описание обязательно")
    @Size(max = 1000, message = "Длина описания не может привышать 1000 символов")
    private String description;

    @NotNull(message = "Приоритет обязателен")
    private Priority priority;

    @NotBlank(message = "Автор обязателен")
    @Size(max = 100, message = "Автор не может превышать 100 символов")
    private String author;

    @NotBlank(message = "Время обязательно")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z", message = "Время должно быть в формате ISO-8601")
    private String time;

    public enum Priority {
        COMMON,
        CRITICAL
    }
}

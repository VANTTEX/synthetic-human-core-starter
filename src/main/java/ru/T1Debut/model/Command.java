package ru.T1Debut.model;

import lombok.Data;

@Data
public class Command {
    private String description;
    private Priority priority;
    private String author;
    private String time;

    public enum Priority {
        COMMON,
        CRITICAL
    }
}

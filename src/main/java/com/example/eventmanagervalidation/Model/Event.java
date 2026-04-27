package com.example.eventmanagervalidation.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Event {
    @NotBlank(message = "ID cannot be null")
    @Size(min = 2, message = "ID must be at least 2 letters.")
    private String id;

    @NotBlank(message = "Description cannot be null")
    @Size(min = 15, message = "Description must be at least 15 letters.")
    private String description;

    @Min(value = 26, message = "Capacity must be more than 25")
    private int capacity;


    @NotNull(message = "Start date cannot be null")
    @FutureOrPresent(message = "Start date must be today or in the future")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null")
    @Future(message = "End date must be in the future")
    private LocalDate endDate;

}

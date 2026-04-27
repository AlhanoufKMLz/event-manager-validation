package com.example.eventmanagervalidation.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Event {
    @NotBlank(message = "ID cannot be null")
    @Size(min = 2, message = "ID should be 2 or more letters.")
    private String id;

    @NotBlank(message = "Description cannot be null")
    @Size(min = 15, message = "Description should be 15 or more letters.")
    private String description;

    @NotNull(message = "Capacity cannot be null")
    @Digits(integer = 26, fraction = 0, message = "Capacity should be an integer greater than 25")
    @Min(value = 26, message = "Capacity should be more than 25")
    private int capacity;


    private LocalDate startDate;

    private LocalDate endDate;

}

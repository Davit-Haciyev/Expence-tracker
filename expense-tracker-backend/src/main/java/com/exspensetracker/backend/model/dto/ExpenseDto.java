package com.exspensetracker.backend.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExpenseDto {
    @NotNull(message = "Amount can't be empty")
    private Double amount;
    @NotBlank(message = "Category can't be empty")
    private String category;
    @NotBlank(message = "Description can't be empty")
    private String description;
    private LocalDateTime date;
}

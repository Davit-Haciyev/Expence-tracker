package com.exspensetracker.backend.controller;

import com.exspensetracker.backend.model.dto.ExpenseDto;
import com.exspensetracker.backend.model.entity.Expense;
import com.exspensetracker.backend.model.entity.User;
import com.exspensetracker.backend.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<?> getAllExpenses(@AuthenticationPrincipal User user) {
        List<Expense> expenses = expenseService.getAllExpenses(user);
        return ResponseEntity.ok().body(new ApiResponse<>(true, expenses));
    }

    @PostMapping
    public ResponseEntity<?> addExpense(@AuthenticationPrincipal User user,
                                        @Valid @RequestBody ExpenseDto expenseDto) {
        Expense saved = expenseService.addExpense(user, expenseDto);
        return ResponseEntity.ok().body(new ApiResponse<>(true, saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@AuthenticationPrincipal User user,
                                           @PathVariable Long id) {
        expenseService.deleteExpense(user, id);
        return ResponseEntity.ok().body(new ApiResponse<>(true, null));
    }

    @Getter
    @AllArgsConstructor
    static class ApiResponse<T> {
        private boolean success;
        private T data;
    }
}

package com.exspensetracker.backend.service;

import com.exspensetracker.backend.model.dto.ExpenseDto;
import com.exspensetracker.backend.model.entity.Expense;
import com.exspensetracker.backend.model.entity.User;
import com.exspensetracker.backend.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;


    public Expense addExpense(User user, ExpenseDto expenseDto) {
        Expense expense = new Expense();
        expense.setUser(user);
        expense.setAmount(expenseDto.getAmount());
        expense.setCategory(expenseDto.getCategory());
        expense.setDescription(expenseDto.getDescription());
        expense.setDate(expenseDto.getDate());

        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses(User user) {
        return expenseRepository.findAllByUser(user);
    }

    public void deleteExpense(User user, Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (!expense.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("This expense does not belong to the current user");
        }

        expenseRepository.delete(expense);
    }
}

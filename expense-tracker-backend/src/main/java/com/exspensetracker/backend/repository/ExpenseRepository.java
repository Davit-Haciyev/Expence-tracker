package com.exspensetracker.backend.repository;


import com.exspensetracker.backend.model.entity.Expense;
import com.exspensetracker.backend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByUser(User user);
}

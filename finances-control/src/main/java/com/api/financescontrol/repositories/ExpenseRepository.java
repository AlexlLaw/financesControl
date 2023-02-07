package com.api.financescontrol.repositories;

import com.api.financescontrol.models.ExpenseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseModel, UUID> {
}

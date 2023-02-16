package com.api.financescontrol.repositories;

import com.api.financescontrol.models.ExpenseModel;
import com.api.financescontrol.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseModel, UUID> {

    List<ExpenseModel> findByUserAndMonthAndYear(UserModel user_id, int month, int year);
}

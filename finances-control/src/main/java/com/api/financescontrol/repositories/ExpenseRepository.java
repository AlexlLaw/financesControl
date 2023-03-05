package com.api.financescontrol.repositories;

import com.api.financescontrol.enums.TypeofExpense;
import com.api.financescontrol.models.ExpenseModel;
import com.api.financescontrol.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseModel, UUID>,JpaSpecificationExecutor<ExpenseModel> {

    List<ExpenseModel> findByUserAndMonthAndYearAndPaidOut(UserModel user_id, int month, int year, Boolean paidOut);
    List<ExpenseModel> findByUserAndMonthAndYear(UserModel user_id, int month, int year);

    List<ExpenseModel> findByUserAndMonthAndYearAndTypeOfExpense(UserModel user_id, int month, int year, TypeofExpense typeOfExpense);

    List<ExpenseModel> findByUserAndYear(UserModel user, Integer year);
}

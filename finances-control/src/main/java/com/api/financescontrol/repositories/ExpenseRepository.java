package com.api.financescontrol.repositories;

import com.api.financescontrol.models.ExpenseModel;
import com.api.financescontrol.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseModel, UUID> {

    List<ExpenseModel> findByUserAndMonthAndYearAndPaidOut(UserModel user_id, int month, int year, Boolean paidOut);
    List<ExpenseModel> findByUserAndMonthAndYear(UserModel user_id, int month, int year);

    List<ExpenseModel> findByUserAndYear(UserModel user, Integer year);

    default List<ExpenseModel> findAllByPaidOutAndMonthAndYearAndUserId(UserModel user_id, int month, int year, Boolean paidOut) {
        List<ExpenseModel> result = findByUserAndMonthAndYearAndPaidOut(user_id, month, year, paidOut);

        if (result.size() == 0) {
            return findByUserAndMonthAndYear(user_id, month, year);
        }

        return result;
    }
}

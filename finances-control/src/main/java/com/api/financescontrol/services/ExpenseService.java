package com.api.financescontrol.services;

import com.api.financescontrol.dtos.expense.ExpenseCreateDTO;
import com.api.financescontrol.dtos.user.UsersAllDTO;
import com.api.financescontrol.models.ExpenseModel;
import com.api.financescontrol.models.UserModel;
import com.api.financescontrol.repositories.ExpenseRepository;
import com.api.financescontrol.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private  final UserRepository userRepository;

    @Transactional
    public void save(ExpenseCreateDTO expenseCreateDTO, UUID id) {

        var expenseModel = new ExpenseModel();
        BeanUtils.copyProperties(expenseCreateDTO, expenseModel);
        expenseModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        expenseModel.setUser(userRepository.findById(id).get());

        saveExpense(expenseModel);
    }

    public List<ExpenseModel> findAllByUser(UUID user_id) {
        var userModel = userRepository.findById(user_id);
        return expenseRepository.findAllByUser(userModel.get());
    }

    private void saveExpense(ExpenseModel expense) {
        ExpenseModel firstExpense = new ExpenseModel();
        if (expense.getIsFixed() && expense.getTimeAccount() != null) {
            for (int i = 0; i < expense.getTimeAccount(); i++) {
                ExpenseModel newExpense = ExpenseModel.builder()
                        .year(expense.getYear())
                        .month(expense.getMonth() + i)
                        .nameExpense(expense.getNameExpense())
                        .descriptionExpense(expense.getDescriptionExpense())
                        .value(expense.getValue())
                        .isFixed(expense.getIsFixed())
                        .paidOut(expense.getPaidOut())
                        .typeOfExpense(expense.getTypeOfExpense())
                        .user(expense.getUser())
                        .registrationDate(LocalDateTime.now())
                        .build();

                if (i == 0) {
                    firstExpense = (ExpenseModel) expenseRepository.save(newExpense);
                } else {
                    newExpense.setParentExpenseId(firstExpense.getId());
                    expenseRepository.save(newExpense);
                }
            }
        } else {
            expense.setRegistrationDate(LocalDateTime.now());
            expenseRepository.save(expense);
        }
    }
}

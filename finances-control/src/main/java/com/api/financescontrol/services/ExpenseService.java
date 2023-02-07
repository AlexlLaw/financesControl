package com.api.financescontrol.services;

import com.api.financescontrol.dtos.expense.ExpenseCreateDTO;
import com.api.financescontrol.models.ExpenseModel;
import com.api.financescontrol.models.UserModel;
import com.api.financescontrol.repositories.ExpenseRepository;
import com.api.financescontrol.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private  final UserRepository userRepository;

    @Transactional
    public ExpenseModel save(ExpenseCreateDTO expenseCreateDTO, UUID id) {

        var expenseModel = new ExpenseModel();
        BeanUtils.copyProperties(expenseCreateDTO, expenseModel);
        expenseModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        expenseModel.setUser(userRepository.findById(id).get());

        if (expenseModel.getIsFixed()) {
            saveExpense(expenseModel);
        }

        return expenseRepository.save(expenseModel);
    }

    @Transactional
    public ExpenseModel save2(ExpenseModel expens) {
        return expenseRepository.save(expens);
    }

        public void saveExpense(ExpenseModel expense) {
       var firstExpense = new ExpenseModel();
        if (expense.getIsFixed() && expense.getTimeAccount() != null) {
            for (int i = 0; i < expense.getTimeAccount(); i++) {
                    ExpenseModel newExpense = ExpenseModel.builder()
                            .year(expense.getYear())
                            .month(expense.getMonth() + i - 1)
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
                              firstExpense = expenseRepository.save(newExpense);
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

//    public void saveExpense(ExpenseModel expense) {
//       var firstExpense = new ExpenseModel();
//        if (expense.getIsFixed() && expense.getTimeAccount() != null) {
//            int currentMonth = expense.getMonth();
//            for (int i = 0; i <= expense.getTimeAccount(); i++) {
//                if (currentMonth != expense.getMonth()) {
//                    ExpenseModel newExpense = ExpenseModel.builder()
//                            .year(expense.getYear())
//                            .month(expense.getMonth() + i - 1)
//                            .nameExpense(expense.getNameExpense())
//                            .descriptionExpense(expense.getDescriptionExpense())
//                            .value(expense.getValue())
//                            .isFixed(expense.getIsFixed())
//                            .paidOut(expense.getPaidOut())
//                            .typeOfExpense(expense.getTypeOfExpense())
//                            .user(expense.getUser())
//                            .registrationDate(LocalDateTime.now())
//                            .build();
//
//                            if (i == 1) {
//                              firstExpense = expenseRepository.save(newExpense);
//                            } else {
//                                newExpense.setId(firstExpense.getId());
//                                expenseRepository.save(newExpense);
//                            }
//                    expenseRepository.save(newExpense);
//                }
//                currentMonth = expense.getMonth() + i;
//            }
//        } else {
//            expense.setRegistrationDate(LocalDateTime.now());
//            expenseRepository.save(expense);
//        }
//    }

//    public void saveExpense(ExpenseModel expense) {
//        if (expense.getIsFixed() && expense.getTimeAccount() != null) {
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(Calendar.YEAR, expense.getYear());
//            calendar.set(Calendar.MONTH, expense.getMonth());
//
//            ExpenseModel firstExpense = null;
//
//            for (int i = 0; i < expense.getTimeAccount(); i++) {
//
//                ExpenseModel newExpense = ExpenseModel.builder()
//                        .year(calendar.get(Calendar.YEAR))
//                        .month(calendar.get(Calendar.MONTH))
//                        .nameExpense(expense.getNameExpense())
//                        .descriptionExpense(expense.getDescriptionExpense())
//                        .value(expense.getValue())
//                        .isFixed(expense.getIsFixed())
//                        .paidOut(expense.getPaidOut())
//                        .typeOfExpense(expense.getTypeOfExpense())
//                        .user(expense.getUser())
//                        .registrationDate(LocalDateTime.now())
//                        .build();
//
//                if (firstExpense != null) {
//                    firstExpense = expenseRepository.save(newExpense);
//                } else {
//                    newExpense.setParentExpenseId(firstExpense.getId());
//                    expenseRepository.save(newExpense);
//                }
//                calendar.add(Calendar.MONTH, 1);
//            }
//        } else {
//            expense.setRegistrationDate(LocalDateTime.now());
//            expenseRepository.save(expense);
//        }
//    }
}

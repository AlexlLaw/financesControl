package com.api.financescontrol.services;

import com.api.financescontrol.config.ExpenseSpecifications;
import com.api.financescontrol.dtos.expense.ExpenseAllDTO;
import com.api.financescontrol.dtos.expense.ExpenseCreateDTO;
import com.api.financescontrol.dtos.expense.ExpensesAllDTO;
import com.api.financescontrol.enums.TypeofExpense;
import com.api.financescontrol.models.ExpenseModel;
import com.api.financescontrol.repositories.ExpenseRepository;
import com.api.financescontrol.repositories.UserRepository;
import com.api.financescontrol.utils.FileUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private  final UserRepository userRepository;
    private final FileUtil imageUtils;

    @Transactional
    public void save(ExpenseCreateDTO expenseCreateDTO) {
        var expenseModel = new ExpenseModel();
        BeanUtils.copyProperties(expenseCreateDTO, expenseModel);
        expenseModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        expenseModel.setUser(userRepository.findById(expenseCreateDTO.getUserId()).get());

        saveExpense(expenseModel);
    }

    public ExpenseAllDTO findAllByUser(UUID user_id, int month, int year, Boolean paidOut,
                                       TypeofExpense typeofExpense, Boolean isFixed
    ){
        var spec = Specification.where(ExpenseSpecifications.withUserAndMonthAndYear(user_id, month, year));

        if (paidOut != null) {
            spec = spec.and(ExpenseSpecifications.withPaidOut(paidOut));
        }

        if (typeofExpense != null) {
            spec = spec.and(ExpenseSpecifications.withTypeofExpense(typeofExpense));
        }

        if (isFixed != null) {
            spec = spec.and(ExpenseSpecifications.withIsFixed(isFixed));
        }

        var expensesMounth = expenseRepository.findAll((Specification<ExpenseModel>) spec)
                .stream().map(this::toExpensesAllDTO).toList();;
//        var expensesMounth = expenseRepository.findByUserAndMonthAndYear(
//                        userRepository.findById(user_id).get(), month, year)
//                .stream().map(this::toExpensesAllDTO).toList();
        var amount = 0;

        for (ExpensesAllDTO expenseMounth : expensesMounth) {
            amount = expenseMounth.getValue() + amount;
        }

        return ExpenseAllDTO.builder()
                .expenses(expensesMounth)
                .amount(amount)
                .build();
    }

    public Optional<ExpenseModel> findById(UUID expense_id) {
        return expenseRepository.findById(expense_id);
    }

    public int findByExpenseForYear(UUID user_id, int year) {
        var expensesYear = expenseRepository.findByUserAndYear(userRepository.findById(user_id).get(), year);
        var amount = 0;

        for (ExpenseModel expenseYear : expensesYear) {
            amount = expenseYear.getValue() + amount;
        }

        return amount;
    }

    public ExpenseModel put(UUID expense_id, ExpenseModel model, ExpenseModel UpdateModel) {
        return expenseRepository.save(UpdateModel);
    }

    public void putPayment(List<UUID> expenses_ids) {
        List<ExpenseModel> expenses = expenseRepository.findAllById(expenses_ids);

        for (ExpenseModel expense: expenses) {
            boolean value = expense.getPaidOut();
            expense.setPaidOut(!value);
        }

        expenseRepository.saveAll(expenses);
    }

    public String delete(List<UUID> expenses_ids) {
        var isNotFound = new ArrayList<>();
        String message;
        expenses_ids.forEach(id -> {
            Optional<ExpenseModel> modelOptional = findById(id);
            if (!modelOptional.isPresent()) {
                isNotFound.add(modelOptional.get().getId());
                return;
            }

            expenseRepository.deleteById(id);
        });

        if (!isNotFound.isEmpty()) {
          return  message = "All items deleted, but some not found";
        }

        return message = "All items deleted" ;
    }

    private void saveExpense(ExpenseModel expense) {
        ExpenseModel firstExpense = new ExpenseModel();
        if (expense.getIsFixed() && expense.getTimeAccount() > 0) {
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
            return;
        }

        expense.setRegistrationDate(LocalDateTime.now());
        expenseRepository.save(expense);
    }

    private ExpensesAllDTO toExpensesAllDTO(ExpenseModel model) {
        return ExpensesAllDTO.builder()
                .id(model.getId())
                .year(model.getYear())
                .month(model.getMonth())
                .nameExpense(model.getNameExpense())
                .descriptionExpense(model.getDescriptionExpense())
                .value(model.getValue())
                .isFixed(model.getIsFixed())
                .timeAccount(model.getTimeAccount())
                .paidOut(model.getPaidOut())
                .typeOfExpense(model.getTypeOfExpense())
                .build();
    }

    private ExpenseModel toExpensesUpdateDTO(ExpenseModel model, ExpenseModel updateModel ) {
        return ExpenseModel.builder()
                .id(model.getId())
                .year(updateModel.getYear())
                .month(updateModel.getMonth())
                .nameExpense(updateModel.getNameExpense())
                .descriptionExpense(updateModel.getDescriptionExpense())
                .value(updateModel.getValue())
                .isFixed(updateModel.getIsFixed())
                .timeAccount(updateModel.getTimeAccount())
                .paidOut(updateModel.getPaidOut())
                .typeOfExpense(updateModel.getTypeOfExpense())
                .build();
    }
}

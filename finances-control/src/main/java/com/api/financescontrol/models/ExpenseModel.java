package com.api.financescontrol.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="TB_EXPENSE")

public class ExpenseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = false)
    private Integer year;

    @Column(nullable = false, unique = false)
    private Integer month;

    @Column(nullable = false, unique = false, length = 36)
    private String nameExpense;

    @Column(nullable = false, unique = false, length = 255)
    private String descriptionExpense;

    @Column(nullable = false, unique = false)
    private Integer value;

    @Column(nullable = false, unique = false)
    private Boolean isFixed;

    @Column(nullable = true, unique = false)
    private Integer timeAccount;

    @Column(nullable = false, unique = false)
    private Boolean paidOut;

    @Column(nullable = false, unique = false, length = 255)
    private String typeOfExpense;

    @Column(nullable = true, unique = false)
    private UUID parentExpenseId;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserModel user;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

}
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

package com.api.financescontrol.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(nullable = true, unique = false)
    private String paymentVoucher;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    private UserModel user;

    @Column(nullable = false)
    private LocalDateTime registrationDate;
}

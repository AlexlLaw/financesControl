package com.api.financescontrol.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name="TB_USER")
@AllArgsConstructor
@Builder
public class UserModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false, unique = true, length = 255)
    private String name;

    @Column(nullable = false, unique = false, length = 255)
    private String lastName;

    @Column(nullable = false, unique = false)
    private Boolean active;

    @Column(nullable = true, unique = false)
    private Boolean firstAcess;

    @Column(nullable = false, unique = false)
    private String restrictions;

    @Column(nullable = false, unique = false, length = 16)
    private String password;

    @Column(nullable = false, unique = false)
    private double wage;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ExpenseModel> expense;

    public UserModel() {

    }
}

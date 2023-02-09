package com.api.financescontrol.controllers;

import com.api.financescontrol.dtos.expense.ExpenseCreateDTO;
import com.api.financescontrol.dtos.user.UsersAllDTO;
import com.api.financescontrol.models.ExpenseModel;
import com.api.financescontrol.models.UserModel;
import com.api.financescontrol.services.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping("{user_id}")
    public ResponseEntity<Object> create(@PathVariable(value = "user_id") UUID id, @RequestBody @Valid ExpenseCreateDTO expenseCreateDTO) {
        expenseService.save(expenseCreateDTO, id);
        return ResponseEntity.status(HttpStatus.CREATED).body("Expense create success");
    }

    @GetMapping("{user_id}")
    public ResponseEntity<List<ExpenseModel>> getAll(@PathVariable(value = "user_id") UUID user_id) {
        return  ResponseEntity.status(HttpStatus.OK).body(expenseService.findAllByUser(user_id));
    }
}

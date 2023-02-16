package com.api.financescontrol.controllers;

import com.api.financescontrol.dtos.expense.ExpenseCreateDTO;
import com.api.financescontrol.dtos.expense.ExpensesAllDTO;
import com.api.financescontrol.models.ExpenseModel;
import com.api.financescontrol.services.ExpenseService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping("{user_id}")
    public ResponseEntity<Object> create(@PathVariable(value = "user_id") UUID id,
                                         @RequestBody @Valid ExpenseCreateDTO expenseCreateDTO
    ) {
        expenseService.save(expenseCreateDTO, id);
        return ResponseEntity.status(HttpStatus.CREATED).body("Expense create success");
    }

    @GetMapping("{user_id}")
    public ResponseEntity<List<ExpensesAllDTO>> findAllByUser(@PathVariable(value = "user_id") UUID user_id,
                                                              @Parameter(description = "month that includes expenses")
                                                              @RequestParam(name = "month") int month,
                                                              @Parameter(description = "year that includes expenses")
                                                              @RequestParam(name = "year") int year
    ) {
        return  ResponseEntity.status(HttpStatus.OK).body(expenseService.findAllByUser(user_id, month, year));
    }

    @GetMapping("{expense_id}/detail")
    public ResponseEntity<ExpenseModel> findByExpense(@PathVariable(value = "expense_id") UUID expense_id) {
        return  ResponseEntity.status(HttpStatus.OK).body(expenseService.findById(expense_id).get());
    }

    @PutMapping("{expense_id}")
    public ResponseEntity<Object> put(@PathVariable(value = "expense_id") UUID expense_id,
                                      @RequestBody @Valid ExpenseModel model
    ) {
        Optional<ExpenseModel> modelOptional = expenseService.findById(expense_id);

        if (!modelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found.");
        }

        return  ResponseEntity.status(HttpStatus.OK).body(expenseService.put(expense_id, modelOptional.get(), model));
    }

    @PutMapping()
    public ResponseEntity<Object> put(@RequestBody List<UUID> expenses_ids
    ) {
        expenseService.putPayment(expenses_ids);
        return  ResponseEntity.status(HttpStatus.OK).body("successfully paid items");
    }

    @DeleteMapping()
    public ResponseEntity<String> delete(@RequestBody List<UUID> expenses_ids) {
        return ResponseEntity.status(HttpStatus.OK).body(expenseService.delete(expenses_ids));
    }
}

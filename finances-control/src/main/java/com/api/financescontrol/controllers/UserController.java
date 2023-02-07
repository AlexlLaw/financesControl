package com.api.financescontrol.controllers;

import com.api.financescontrol.dtos.user.UserCreateDTO;
import com.api.financescontrol.dtos.user.UserUpdateDTO;
import com.api.financescontrol.dtos.user.UserViewDTO;
import com.api.financescontrol.dtos.user.UsersAllDTO;
import com.api.financescontrol.models.UserModel;
import com.api.financescontrol.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody @Valid UserCreateDTO userCreateDTO) {

        if (userService.existsByCpf(userCreateDTO.getCpf())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF is already being used.");
        }
        var userModel = new UserModel();
        BeanUtils.copyProperties(userCreateDTO, userModel);
        userModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));

       return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
   }

   @GetMapping("/{id}/calculoFinanceiro")
   public ResponseEntity<Object> getFinancialCalculation(@PathVariable(value = "id") UUID id) {
       Optional<UserViewDTO> userViewDTOOptional = userService.findById(id);
       if (!userViewDTOOptional.isPresent()) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
       }
       return ResponseEntity.status(HttpStatus.OK).body(userService.getFinancialCalculation(userViewDTOOptional.get()));
   }

    @GetMapping
    public ResponseEntity<Page<UsersAllDTO>> getAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll(pageable));
   }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") UUID id) {

        Optional<UserViewDTO> userViewDTOOptional = userService.findById(id);
        if (!userViewDTOOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userViewDTOOptional.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> put(@PathVariable(value = "id") UUID id, @RequestBody @Valid UserUpdateDTO userUpdateDTO) {
        Optional<UserViewDTO> userViewDTOOptional = userService.findById(id);
        if (!userViewDTOOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.put(userUpdateDTO, id, userViewDTOOptional));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {
        Optional<UserViewDTO> userViewDTOOptional = userService.findById(id);
        if (!userViewDTOOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfuly.");
    }
}

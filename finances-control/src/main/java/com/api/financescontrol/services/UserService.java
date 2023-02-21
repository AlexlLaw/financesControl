package com.api.financescontrol.services;

import com.api.financescontrol.services.dtos.user.UserFinancialCalculationDTO;
import com.api.financescontrol.services.dtos.user.UserUpdateDTO;
import com.api.financescontrol.services.dtos.user.UserViewDTO;
import com.api.financescontrol.services.dtos.user.UsersAllDTO;
import com.api.financescontrol.models.UserModel;
import com.api.financescontrol.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }

    @Transactional
    public boolean existsByCpf(String cpf) {
        return userRepository.existsByCpf(cpf);
    }

    @Transactional
    public Page<UsersAllDTO> findAll(Pageable pageable) {
      return  userRepository.findAll(pageable).map(this::toUsersAllDTO);
    }

    @Transactional
    public Optional<UserViewDTO> findById(UUID id) {
        return userRepository.findById(id).map(this::toUserViewDTO);
    }

    @Transactional
    public UserModel put(UserUpdateDTO userUpdateDTO, UUID id, Optional<UserViewDTO> userViewDTOOptional) {
        return userRepository.save(toUserModelDTO(userUpdateDTO, id, userViewDTOOptional));
    }

    @Transactional
    public void delete(UUID id) {
         userRepository.deleteById(id);
    }

    @Transactional
    public UserFinancialCalculationDTO getFinancialCalculation(UserViewDTO userViewDTO) {
        var essentials = 0.2 * userViewDTO.getWage();
        var bullshit = 0.2 * userViewDTO.getWage();
        var financialReserve = 0.6 * userViewDTO.getWage();

        return UserFinancialCalculationDTO.builder()
                .essentials(essentials)
                .bullshit(bullshit)
                .financialReserve(financialReserve)
                .build();
    }

    private UserModel toUserModelDTO(UserUpdateDTO userUpdateDTO, UUID id, Optional<UserViewDTO> userViewDTOOptional) {
        var userModel = new UserModel();
        BeanUtils.copyProperties(userUpdateDTO, userModel);
        userModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setActive(userViewDTOOptional.get().getActive());
        userModel.setFirstAcess(userViewDTOOptional.get().getFirstAcess());
        userModel.setId(id);

        return userModel;
    }

    private UsersAllDTO toUsersAllDTO(UserModel userModels) {
       return UsersAllDTO.builder()
               .id(userModels.getId())
                .cpf(userModels.getCpf())
                .name(userModels.getName())
                .lastName(userModels.getLastName())
                .active(userModels.getActive())
                .restrictions(userModels.getRestrictions())
                .wage(userModels.getWage())
                .build();
    }

    private UserViewDTO toUserViewDTO(UserModel userModels) {
        return UserViewDTO.builder()
                .id(userModels.getId())
                .cpf(userModels.getCpf())
                .name(userModels.getName())
                .lastName(userModels.getLastName())
                .active(userModels.getActive())
                .firstAcess(userModels.getFirstAcess())
                .restrictions(userModels.getRestrictions())
                .wage(userModels.getWage())
                .password(userModels.getPassword())
                .build();
    }
}

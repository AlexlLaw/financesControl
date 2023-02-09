package com.api.financescontrol.repositories;

import com.api.financescontrol.models.ExpenseModel;
import com.api.financescontrol.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseModel, UUID> {

    List<ExpenseModel> findAllByUser(UserModel user_id);
}

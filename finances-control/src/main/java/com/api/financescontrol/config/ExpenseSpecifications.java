package com.api.financescontrol.config;

import com.api.financescontrol.enums.TypeofExpense;
import com.api.financescontrol.models.ExpenseModel;
import com.api.financescontrol.models.UserModel;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ExpenseSpecifications {
    public static Specification<ExpenseModel> withUserAndMonthAndYear(UUID user_id, Integer month, Integer year) {
        return (root, query, builder) -> builder.and(
                builder.equal(root.get("user").get("id"), user_id),
                builder.equal(root.get("month"), month),
                builder.equal(root.get("year"), year)
        );
    }

    public static Specification<ExpenseModel> withPaidOut(Boolean paidOut) {
        return (root, query, builder) -> builder.equal(root.get("paidOut"), paidOut);
    }

    public static Specification<ExpenseModel> withTypeofExpense(TypeofExpense typeofExpense) {
        return (root, query, builder) -> builder.equal(root.get("typeOfExpense"), typeofExpense.name());
    }

    public static Specification<ExpenseModel> withIsFixed(Boolean isFixed) {
        return (root, query, builder) -> builder.equal(root.get("isFixed"), isFixed);
    }
}

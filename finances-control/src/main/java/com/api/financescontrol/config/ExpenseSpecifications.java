package com.api.financescontrol.config;

import com.api.financescontrol.models.ExpenseModel;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.Optional;
import java.util.UUID;

public class ExpenseSpecifications {

    public static Specification<ExpenseModel> findByUserIdMonthYearAndPaidOut(
            Optional<UUID> userId,
            Optional<Integer> month,
            Optional<Integer> year,
            Optional<Boolean> paidOut) {

        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            userId.map(u -> criteriaBuilder.equal(root.get("userId"), u))
                    .ifPresent(predicate.getExpressions()::add);

            month.map(m -> criteriaBuilder.equal(root.get("month"), m))
                    .ifPresent(predicate.getExpressions()::add);

            year.map(y -> criteriaBuilder.equal(root.get("year"), y))
                    .ifPresent(predicate.getExpressions()::add);

            if (paidOut.isPresent()) {
                if (paidOut.get()) {
                    predicate.getExpressions().add(criteriaBuilder.isTrue(root.get("paidOut")));
                } else {
                    predicate.getExpressions().add(criteriaBuilder.or(
                            criteriaBuilder.isNull(root.get("paidOut")),
                            criteriaBuilder.isFalse(root.get("paidOut"))
                    ));
                }
            }

            return predicate;
        };
    }
}

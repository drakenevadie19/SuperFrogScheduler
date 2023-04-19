package edu.tcu.cs.superfrogscheduler.user;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class SuperFrogUserSpecification implements Specification<SuperFrogUser> {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    public SuperFrogUserSpecification(String firstName, String lastName, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }


    @Override
    public Predicate toPredicate(Root<SuperFrogUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (firstName != null && !firstName.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%"));
        }

        if (lastName != null && !lastName.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%"));
        }

        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("phoneNumber")), "%" + phoneNumber.toLowerCase() + "%"));
        }

        if (email != null && !email.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
        }

//        // sorting
//        Expression<?> orderExpression = root.get(sortBy);
//        // sortDirection can only be ASC or DSC as filtered out in UserSearchDto
//        Order order = sortDirection.equals("ASC") ? cb.asc(orderExpression) : cb.desc(orderExpression);
//
//        query.orderBy(order);

        // sorting and pagination will be handle by pagination class

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}

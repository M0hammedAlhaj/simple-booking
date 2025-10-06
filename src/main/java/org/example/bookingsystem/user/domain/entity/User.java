package org.example.bookingsystem.user.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.bookingsystem.customer.domain.entity.Customer;
import org.example.bookingsystem.shared.domain.BaseEntity;
import org.example.bookingsystem.user.domain.model.UserType;

@Table(name = "users")
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    private String email;

    private String password;

    public UserType getType() {
        if (this instanceof Customer) {
            return UserType.CUSTOMER;
        }
        return UserType.PROVIDER;
    }
}

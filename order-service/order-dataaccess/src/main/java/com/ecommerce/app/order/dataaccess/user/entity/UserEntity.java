package com.ecommerce.app.order.dataaccess.user.entity;

import com.ecommerce.app.common.domain.valueobject.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class UserEntity {
    @Id
    private UUID id;
    private UUID warehouseId;
    private String email;
    private boolean  isEmailVerified;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return isEmailVerified == that.isEmailVerified && Objects.equals(id, that.id) && Objects.equals(email, that.email) && role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, isEmailVerified, role);
    }
}

package com.ecommerce.app.user.dataaccess.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_addresses")
@Entity
public class UserAddressEntity {
    @Id
    private UUID id;
    private UUID userId;
    private String street;
    private String postalCode;
    private String city;
    private String latitude;
    private String longitude;
    private boolean isPrimary;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAddressEntity that = (UserAddressEntity) o;
        return isPrimary == that.isPrimary && Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(street, that.street) && Objects.equals(postalCode, that.postalCode) && Objects.equals(city, that.city) && Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, street, postalCode, city, latitude, longitude, isPrimary);
    }
}

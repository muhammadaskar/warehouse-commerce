package com.ecommerce.app.common.domain.valueobject;

import java.util.Objects;

public abstract class BaseId<T> {

    private final T value;

    public BaseId(T value) {
        this.value = value;
    }

//    public BaseId(T value) {
//        if (value == null) {
//            throw new IllegalArgumentException("ID cannot be null");
//        }
//        this.value = value;
//    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BaseId<?> baseId = (BaseId<?>) obj;
        return Objects.equals(value, baseId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}

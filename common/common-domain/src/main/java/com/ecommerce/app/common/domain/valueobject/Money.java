package com.ecommerce.app.common.domain.valueobject;

import java.math.BigDecimal;

public class Money {
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

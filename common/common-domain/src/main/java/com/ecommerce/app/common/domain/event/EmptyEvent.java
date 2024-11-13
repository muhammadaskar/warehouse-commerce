package com.ecommerce.app.common.domain.event;

public class EmptyEvent implements DomainEvent{

    public static final EmptyEvent INSTANCE = new EmptyEvent();

    private EmptyEvent() {
    }

    @Override
    public void fire() {
    }
}


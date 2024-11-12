package com.ecommerce.app.common.domain.event;

public class EmptyEvent implements DomainEvent{

    public static final EmptyEvent INSTANCE = new EmptyEvent();

    // Private constructor to prevent instantiation
    private EmptyEvent() {
    }

    @Override
    public void fire() {

    }

    // Optional: You can add any common methods or functionality here if needed
}


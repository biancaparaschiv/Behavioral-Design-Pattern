
package org.example.handler;

import org.example.model.Order;

public abstract class OrderValidationHandler {
    protected OrderValidationHandler next;
    protected OrderValidationHandler nextHandler; // The next handler in the chain

    public void setNext(OrderValidationHandler next) {
        this.next = next;
    }

    public void validate(Order order) {
        if (next != null) {
            next.validate(order);
        }
    }

    // Method to set the next handler in the chain
    public void setNextHandler(OrderValidationHandler handler) {
        this.nextHandler = handler;
    }

    // Abstract handle method to be implemented by subclasses
    public abstract void handle(Order order);
}

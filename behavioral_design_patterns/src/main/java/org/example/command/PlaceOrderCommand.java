package org.example.command;

import org.example.model.Order;
import org.example.handler.InventoryCheckHandler;
import org.example.handler.PaymentValidationHandler;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlaceOrderCommand implements OrderCommand {

    private final OrderService orderService;
    private final InventoryCheckHandler inventoryHandler;
    private final PaymentValidationHandler paymentHandler;

    private Long orderId;

    @Autowired
    public PlaceOrderCommand(OrderService orderService, InventoryCheckHandler inventoryHandler, PaymentValidationHandler paymentHandler) {
        this.orderService = orderService;
        this.inventoryHandler = inventoryHandler;
        this.paymentHandler = paymentHandler;
    }

    // Setter method to set the order ID for which the command will be executed
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public void execute() {
        // Retrieve the order by ID
        Order order = orderService.getOrderById(orderId);

        if (order == null) {
            throw new IllegalArgumentException("Order not found with ID: " + orderId);
        }

        // Step 1: Check inventory
        inventoryHandler.handle(order);

        // Step 2: Validate payment
        paymentHandler.handle(order);

        // Step 3: Finalize order placement
        orderService.placeOrder(order);

        // Optionally, log the successful execution
        System.out.println("Order placed successfully for order ID: " + orderId);
    }
}
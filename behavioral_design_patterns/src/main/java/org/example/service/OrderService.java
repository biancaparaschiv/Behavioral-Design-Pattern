package org.example.service;

import org.example.model.Order;
import org.example.model.OrderStatus;
import org.example.payment.PaymentStrategy;
import org.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private PaymentStrategy paymentStrategy;
    @Autowired
    private OrderRepository orderRepository;

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public Order placeOrder(Order order) {
        order.setStatus(String.valueOf(OrderStatus.PLACED));
        orderRepository.save(order);
        return order;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public boolean processOrderPayment(double amount) {
        return paymentStrategy.pay(amount);
    }

    // Method to update an existing order
    public Order updateOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setCustomerName(updatedOrder.getCustomerName());
                    order.setTotalAmount(updatedOrder.getTotalAmount());
                    order.setStatus(updatedOrder.getStatus());
                    order.setPaymentDetails(updatedOrder.getPaymentDetails());
                    return orderRepository.save(order);
                })
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // Method to delete an order by ID
    public void deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new RuntimeException("Order not found");
        }
    }
}

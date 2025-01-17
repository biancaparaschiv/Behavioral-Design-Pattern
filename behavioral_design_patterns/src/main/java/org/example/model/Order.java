package org.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String status;
    private double totalAmount;
    @Setter
    @Getter
    @OneToOne(cascade = CascadeType.ALL)  // Cascade all operations, so PaymentDetails will be persisted automatically
    @JoinColumn(name = "payment_details_id")
    private PaymentDetails paymentDetails;
    @Getter
    @Setter
    @OneToMany
    private List<Item> items;  // List of items in the order

    public void updateStatus(String newStatus) {
        this.status = newStatus;
        System.out.println("Order " + id + " status updated to: " + newStatus);
    }

}


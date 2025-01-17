package org.example.repository;

import org.example.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // Finds inventory by product ID and throws an exception if not found
    default Inventory findByProductId(Long productId) {
        Inventory inventory = findByProductIdWithoutException(productId);
        
        if (inventory == null) {
            throw new RuntimeException("Inventory not found for product ID: " + productId);
        }
        
        return inventory;
    }

    // Original method to find by product ID (no exception handling)
    Inventory findByProductIdWithoutException(Long productId);

}


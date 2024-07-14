package org.nahid.ecommerce.repository;

import org.nahid.ecommerce.models.OrderItem;
import org.nahid.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}

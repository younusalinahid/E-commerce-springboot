package org.nahid.ecommerce.repository;

import org.nahid.ecommerce.models.Order;
import org.nahid.ecommerce.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {


}

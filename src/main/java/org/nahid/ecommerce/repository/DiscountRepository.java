package org.nahid.ecommerce.repository;

import org.nahid.ecommerce.models.Company;
import org.nahid.ecommerce.models.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

}

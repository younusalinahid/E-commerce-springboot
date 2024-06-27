package org.nahid.ecommerce.repository;

import org.nahid.ecommerce.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByName(String name);
    List<Product> findAll();

    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND " +
            "(:productName IS NULL OR p.name LIKE %:productName%) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:discount IS FALSE OR p.discount IS NOT NULL)")
    Page<Product> findByCategoryIdAndFilters(@Param("categoryId") Long categoryId,
                                             @Param("productName") String productName,
                                             @Param("minPrice") Integer minPrice,
                                             @Param("maxPrice") Integer maxPrice,
                                             @Param("discount") boolean discount,
                                             Pageable pageable);

}

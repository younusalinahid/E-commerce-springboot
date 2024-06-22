package org.nahid.ecommerce.repository;

import org.nahid.ecommerce.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByName(String name);

    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);


    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.name LIKE %:productName%")
    Page<Product> findByCategoryIdAndNameContaining(@Param("categoryId") Long categoryId,
                                                    @Param("productName") String productName,
                                                    Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.price BETWEEN :minPrice AND :maxPrice")
    Page<Product> findByCategoryIdAndPriceRange(@Param("categoryId") Long categoryId,
                                                @Param("minPrice") Integer minPrice,
                                                @Param("maxPrice") Integer maxPrice,
                                                Pageable pageable);

}

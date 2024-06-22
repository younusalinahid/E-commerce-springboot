package org.nahid.ecommerce.mapper;

import org.nahid.ecommerce.dto.CompanyDTO;
import org.nahid.ecommerce.dto.ProductDTO;
import org.nahid.ecommerce.models.Category;
import org.nahid.ecommerce.models.Product;
import org.nahid.ecommerce.request.ProductRequest;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    private ProductMapper() {

    }

    public static Product convertProductRequestWithoutId(Long categoryId, ProductRequest productRequest) {
        Category category = new Category();
        category.setId(categoryId);
        return new Product(0,
                productRequest.getName(),
                productRequest.getPrice(),
                productRequest.getSize(),
                productRequest.getDescription(),
                category);
    }

    public static Product convertProductRequestWithId(Long categoryId, Long productId, ProductRequest productRequest) {
        Category category = new Category();
        category.setId(categoryId);
        return new Product(
                productId,
                productRequest.getName(),
                productRequest.getPrice(),
                productRequest.getSize(),
                productRequest.getDescription(),
                category);
    }

    public static ProductDTO toProductDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getSize(),
                product.getDescription(),
                product.getCompanies().stream()
                        .map(company -> new CompanyDTO(company.getId(), company.getCompanyName()))
                        .collect(Collectors.toList())
        );
    }

}

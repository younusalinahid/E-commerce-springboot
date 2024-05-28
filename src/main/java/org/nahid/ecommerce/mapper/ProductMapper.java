package org.nahid.ecommerce.mapper;

import org.nahid.ecommerce.models.Category;
import org.nahid.ecommerce.models.Product;
import org.nahid.ecommerce.request.ProductRequest;

public class ProductMapper {

    private ProductMapper() {

    }

    public static Product convertProductRequestWithoutId(Long categoryId, ProductRequest productRequest) {
        Category category = new Category();
        category.setId(categoryId);
        return new Product(null,
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

}

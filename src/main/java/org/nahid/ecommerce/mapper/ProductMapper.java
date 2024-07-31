package org.nahid.ecommerce.mapper;

import org.nahid.ecommerce.dto.CompanyDTO;
import org.nahid.ecommerce.dto.DiscountDTO;
import org.nahid.ecommerce.dto.ProductDTO;
import org.nahid.ecommerce.dto.ProductWithDiscountDTO;
import org.nahid.ecommerce.models.Category;
import org.nahid.ecommerce.models.Company;
import org.nahid.ecommerce.models.Discount;
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
                productRequest.getStockQuantity(),
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
                productRequest.getStockQuantity(),
                productRequest.getDescription(),
                category);
    }


    public static ProductDTO convertProductWithoutDiscount(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getSize(),
                product.getStockQuantity(),
                product.getDescription(),
                product.getCreatedDate(),
                product.getCompanies().stream()
                        .map(company -> new CompanyDTO(company.getId(), company.getCompanyName()))
                        .collect(Collectors.toList())
        );
    }

    public static ProductWithDiscountDTO toProductDTO(Product product) {
        ProductWithDiscountDTO productDTO = new ProductWithDiscountDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setSize(product.getSize());
        productDTO.setStockQuantity(product.getStockQuantity());
        productDTO.setDescription(product.getDescription());
        productDTO.setCreatedDate(product.getCreatedDate());

        List<CompanyDTO> companyDTOs = product.getCompanies().stream()
                .map(company -> new CompanyDTO(company.getId(), company.getCompanyName()))
                .collect(Collectors.toList());
        productDTO.setCompanies(companyDTOs);

        if (product.getDiscount() != null) {
            DiscountDTO discountDTO = new DiscountDTO();
            discountDTO.setId(product.getDiscount().getId());
            discountDTO.setPercentage(product.getDiscount().getPercentage());
            productDTO.setDiscountDTO(discountDTO);
        }

        return productDTO;
    }

}

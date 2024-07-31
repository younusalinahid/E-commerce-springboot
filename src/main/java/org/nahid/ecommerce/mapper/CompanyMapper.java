package org.nahid.ecommerce.mapper;

import org.nahid.ecommerce.dto.CompanyDTO;
import org.nahid.ecommerce.dto.CompanyWithProductDTO;
import org.nahid.ecommerce.dto.DiscountDTO;
import org.nahid.ecommerce.dto.ProductDTO;
import org.nahid.ecommerce.models.Company;
import org.nahid.ecommerce.models.Discount;
import org.nahid.ecommerce.request.CompanyRequest;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyMapper {

    private CompanyMapper() {

    }

    public static Company convertCompanyRequestWithoutId(CompanyRequest companyRequest) {
        return new Company(null,
                companyRequest.getCompanyName()
        );
    }

    public static CompanyDTO convertCompanyWithoutProductDTO(Company company) {
        return new CompanyDTO(
                company.getId(),
                company.getCompanyName()
        );
    }

    public static Company convertCompanyRequestWithId(CompanyRequest companyRequest, long id) {
        return new Company(
                id,
                companyRequest.getCompanyName());
    }

    public static CompanyWithProductDTO convertCompanyWithProductDTO(Company company) {
        List<ProductDTO> productDTOs = company.getProducts().stream()
                .map(product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getSize(),
                        product.getStockQuantity(),
                        product.getDescription(),
                        product.getCreatedDate(),
                        product.getCompanies().stream()
                                .map(comp -> new CompanyDTO(comp.getId(), comp.getCompanyName()))
                                .collect(Collectors.toList())
                        ))
                .collect(Collectors.toList());

        return new CompanyWithProductDTO(
                company.getId(),
                company.getCompanyName(),
                productDTOs
        );
    }

//    public static CompanyWithProductDTO convertCompanyWithProductDTO(Company company) {
//        List<ProductDTO> productDTOs = company.getProducts().stream()
//                .map(product -> {
//                    Discount discount = product.getDiscount();
//                    DiscountDTO discountDTO = discount != null ? new DiscountDTO(
//                            discount.getId(),
//                            discount.getPercentage()
//                    ) : null;
//
//                    return new ProductDTO(
//                            product.getId(),
//                            product.getName(),
//                            product.getPrice(),
//                            product.getSize(),
//                            product.getDescription(),
//                            product.getCreatedDate(),
//                            discountDTO,
//                            product.getCompanies().stream()
//                                    .map(comp -> new CompanyDTO(comp.getId(), comp.getCompanyName()))
//                                    .collect(Collectors.toList())
//                    );
//                })
//                .collect(Collectors.toList());
//
//        return new CompanyWithProductDTO(
//                company.getId(),
//                company.getCompanyName(),
//                productDTOs
//        );
//    }
}

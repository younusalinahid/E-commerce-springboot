package org.nahid.ecommerce.mapper;

import org.nahid.ecommerce.dto.CompanyDTO;
import org.nahid.ecommerce.dto.CompanyProductDTO;
import org.nahid.ecommerce.dto.ProductDTO;
import org.nahid.ecommerce.models.Company;
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

    public static CompanyProductDTO convertCompanyWithProductDTO(Company company) {
        List<ProductDTO> productDTOs = company.getProducts().stream()
                .map(product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getSize(),
                        product.getDescription()))
                .collect(Collectors.toList());

        return new CompanyProductDTO(
                company.getId(),
                company.getCompanyName(),
                productDTOs
        );
    }

}

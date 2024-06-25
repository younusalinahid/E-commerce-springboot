package org.nahid.ecommerce.mapper;

import org.nahid.ecommerce.dto.CompanyDTO;
import org.nahid.ecommerce.dto.CompanyWithProductDTO;
import org.nahid.ecommerce.dto.DiscountDTO;
import org.nahid.ecommerce.dto.ProductDTO;
import org.nahid.ecommerce.models.Company;
import org.nahid.ecommerce.models.Discount;
import org.nahid.ecommerce.request.CompanyRequest;
import org.nahid.ecommerce.request.DiscountRequest;

import java.util.List;
import java.util.stream.Collectors;

public class DiscountMapper {

    private DiscountMapper() {

    }

    public static Discount convertDiscountRequestWithoutId(DiscountRequest discountRequest) {
        return new Discount(null,
                discountRequest.getPercentage()
        );
    }

    public static DiscountDTO convertDiscountWithoutProductDTO(Discount discount) {
        return new DiscountDTO(
                discount.getId(),
                discount.getPercentage()
        );
    }

    public static Discount convertDiscountRequestWithId(DiscountRequest discountRequest, long id) {
        return new Discount(
                id,
                discountRequest.getPercentage());
    }

}

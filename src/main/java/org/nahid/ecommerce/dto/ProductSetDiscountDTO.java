package org.nahid.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class ProductSetDiscountDTO {

    private Long discountId;
    private List<Long> productIds;

}

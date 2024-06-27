package org.nahid.ecommerce.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class ProductDiscountRequest {

    private List<Long> productIds;
    private Long discountId;
}

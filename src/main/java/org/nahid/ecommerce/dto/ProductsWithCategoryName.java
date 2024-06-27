package org.nahid.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nahid.ecommerce.response.PageResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsWithCategoryName{
    private Long id;
    private String name;
    private PageResponse<ProductWithDiscountDTO> products;
//    private DiscountDTO discountDTO;


}
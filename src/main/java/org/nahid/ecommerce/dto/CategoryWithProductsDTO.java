package org.nahid.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nahid.ecommerce.response.PageResponse;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryWithProductsDTO {
    private Long id;
    private String name;
    private List<ProductDTO> products;

}

package org.nahid.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nahid.ecommerce.models.Product;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyProductDTO {

    private Long id;
    private String name;
    private List<ProductDTO> products;


}

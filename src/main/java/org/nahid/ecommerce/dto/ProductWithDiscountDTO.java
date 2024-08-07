package org.nahid.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithDiscountDTO {

    private Long id;
    private String name;
    private int price;
    private String size;
    private int stockQuantity;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private LocalDateTime createdDate;
    private DiscountDTO discountDTO;
    private List<CompanyDTO> companies;

}

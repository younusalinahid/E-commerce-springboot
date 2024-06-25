package org.nahid.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nahid.ecommerce.models.Product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data @AllArgsConstructor @NoArgsConstructor
public class DiscountDTO {

    private Long id;
    private String percentage;

}

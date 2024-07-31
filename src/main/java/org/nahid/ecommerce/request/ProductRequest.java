package org.nahid.ecommerce.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nahid.ecommerce.utils.Constants;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    @NotNull(message = Constants.NOT_EMPTY_NAME)
    private String name;

    @Min(value = 1, message = Constants.MIN_PRICE)
    private int price;

    @NotNull(message = Constants.NOT_EMPTY_SIZE)
    private String size;

    private int stockQuantity;

    @Size(min = 10, message = Constants.MIN_DESCRIPTION_LENGTH)
    private String description;


}

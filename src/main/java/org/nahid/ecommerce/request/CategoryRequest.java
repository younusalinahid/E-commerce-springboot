package org.nahid.ecommerce.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nahid.ecommerce.utils.Constants;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {

    @NotNull(message = Constants.NOT_EMPTY_NAME)
    private String name;

}

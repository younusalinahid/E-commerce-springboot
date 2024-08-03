package org.nahid.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class AddressDTO {

    private Long id;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private Long userId;

}

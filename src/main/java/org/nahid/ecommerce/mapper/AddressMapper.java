package org.nahid.ecommerce.mapper;

import org.nahid.ecommerce.dto.AddressDTO;
import org.nahid.ecommerce.models.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public AddressDTO addressDTO(Address address) {
        AddressDTO dto = new AddressDTO();
        dto.setId(address.getId());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setState(address.getStreet());
        dto.setZipCode(address.getZipCode());
        dto.setCountry(address.getCountry());
        dto.setUserId(address.getUser().getId());
        return dto;
    }

    public Address toEntity(AddressDTO dto) {
        Address address = new Address();
        address.setId(dto.getId());
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setStreet(dto.getStreet());
        address.setZipCode(dto.getZipCode());
        address.setCountry(dto.getCountry());
        return address;
    }

}

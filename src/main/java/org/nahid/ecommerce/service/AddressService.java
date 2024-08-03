package org.nahid.ecommerce.service;

import org.nahid.ecommerce.dto.AddressDTO;
import org.nahid.ecommerce.mapper.AddressMapper;
import org.nahid.ecommerce.models.Address;
import org.nahid.ecommerce.models.User;
import org.nahid.ecommerce.repository.AddressRepository;
import org.nahid.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressMapper addressMapper;

    public AddressDTO addAddress(Long userId, AddressDTO addressDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Address address = addressMapper.toEntity(addressDTO);
        address.setUser(user);
        address = addressRepository.save(address);
        return addressMapper.addressDTO(address);
    }

    public List<AddressDTO> getAddressesByUserId(Long userId) {
        List<Address> addresses = addressRepository.findByUserId(userId);
        return addresses.stream()
                .map(addressMapper::addressDTO)
                .collect(Collectors.toList());
    }

    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setZipCode(addressDTO.getZipCode());
        address.setCountry(addressDTO.getCountry());
        address = addressRepository.save(address);
        return addressMapper.addressDTO(address);
    }

    public void deleteAddress(Long addressId) {
        addressRepository.deleteById(addressId);
    }

}

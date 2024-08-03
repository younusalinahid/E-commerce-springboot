package org.nahid.ecommerce.controller;

import org.nahid.ecommerce.dto.AddressDTO;
import org.nahid.ecommerce.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressDTO> addAddress(@PathVariable Long userId, @RequestBody AddressDTO addressDTO) {
        AddressDTO saveAddress = addressService.addAddress(userId, addressDTO);
        return new ResponseEntity<>(saveAddress, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AddressDTO>> getAddressByUserId(@PathVariable Long userId) {
        List<AddressDTO> addressDTOS = addressService.getAddressesByUserId(userId);
        return new ResponseEntity<>(addressDTOS, HttpStatus.OK);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId, @RequestBody AddressDTO addressDTO) {
        AddressDTO updateAddress = addressService.updateAddress(addressId, addressDTO);
        return new ResponseEntity<>(updateAddress, HttpStatus.OK);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

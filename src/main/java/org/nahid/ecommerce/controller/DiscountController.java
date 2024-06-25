package org.nahid.ecommerce.controller;

import org.nahid.ecommerce.dto.CompanyWithProductDTO;
import org.nahid.ecommerce.exception.ConstraintsViolationException;
import org.nahid.ecommerce.mapper.CompanyMapper;
import org.nahid.ecommerce.mapper.DiscountMapper;
import org.nahid.ecommerce.models.Discount;
import org.nahid.ecommerce.repository.DiscountRepository;
import org.nahid.ecommerce.request.CompanyRequest;
import org.nahid.ecommerce.request.DiscountRequest;
import org.nahid.ecommerce.response.ApiResponse;
import org.nahid.ecommerce.response.ObjectResponse;
import org.nahid.ecommerce.service.DiscountService;
import org.nahid.ecommerce.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/discount")
public class DiscountController {

    @Autowired
    DiscountService discountService;

    @Autowired
    DiscountRepository discountRepository;

    @PostMapping
    public ResponseEntity<ObjectResponse> createDiscount(@RequestBody DiscountRequest discountRequest) throws ConstraintsViolationException {
        Discount saveDiscount = DiscountMapper.convertDiscountRequestWithoutId(discountRequest);
        return new ResponseEntity<>(new ObjectResponse(true, Constants.DISCOUNT_CREATED,
                DiscountMapper.convertDiscountWithoutProductDTO(discountService.createDiscount(saveDiscount))),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ObjectResponse> getAllDiscount() {
        List<Discount> discounts = discountService.getAllDiscount();
        ObjectResponse response = new ObjectResponse(true, "Success", discounts);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{discountId}")
    public ResponseEntity<ObjectResponse> getById(@PathVariable("discountId") @Valid long id) {
        Discount discount = discountService.getById(id);
        return ResponseEntity.ok(new ObjectResponse(true, Constants.DISCOUNT_FOUND, discount));
    }

//    @GetMapping("/{discountId}")
//    public ResponseEntity<ObjectResponse> getDiscountWithId(@PathVariable Long discountId) {
//        Discount discount = discountService.getById(discountId);
//        if (discount == null) {
//            return ResponseEntity.notFound().build();
//        }
//        CompanyWithProductDTO companyProductDTO = CompanyMapper.convertCompanyWithProductDTO(discount);
//        return ResponseEntity.ok(new ObjectResponse(true, Constants.COMPANY_FOUND_WITH_PRODUCT, companyProductDTO));
//    }

    @PutMapping("{discountId}")
    public ResponseEntity<ObjectResponse> update(@PathVariable("discountId") Long id,
                                                 @Valid @RequestBody DiscountRequest discountRequest) throws ConstraintsViolationException {
        Discount discount = DiscountMapper.convertDiscountRequestWithId(discountRequest, id);
        return ResponseEntity.ok(
                new ObjectResponse(true, Constants.DISCOUNT_UPDATED,
                        DiscountMapper.convertDiscountWithoutProductDTO(discountService.update(discount)))
        );
    }

    @DeleteMapping("/{discountId}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable("discountId") @Valid long id) {
        discountService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse(true, Constants.COMPANY_DELETED));
    }
}
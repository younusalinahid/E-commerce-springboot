package org.nahid.ecommerce.controller;

import org.nahid.ecommerce.dto.ProductSetDiscountDTO;
import org.nahid.ecommerce.models.Product;
import org.nahid.ecommerce.request.CompanyProductRequest;
import org.nahid.ecommerce.request.ProductDiscountRequest;
import org.nahid.ecommerce.response.ApiResponse;
import org.nahid.ecommerce.response.ObjectResponse;
import org.nahid.ecommerce.service.CompanyProductService;
import org.nahid.ecommerce.service.CompanyService;
import org.nahid.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CompanyService companyService;

    @Autowired
    CompanyProductService companyProductService;

    @GetMapping
    public ResponseEntity<ObjectResponse> getAllProducts(
            @RequestParam(value = "name", required = false) String name) {

        List<Product> products;
        if (name != null) {
            products = productService.getProductsByName(name);
        } else {
            products = productService.getAllProducts();
        }

        ObjectResponse response = new ObjectResponse(true, "Success", products);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/addCompany")
    public ResponseEntity<ApiResponse> addProductsToCompany(@RequestBody CompanyProductRequest companyProductRequest) {
        companyProductService.saveProductsInCompany(companyProductRequest);
        return ResponseEntity.ok().body(new ApiResponse(true,"Products added to company successfully"));

    }

    @PostMapping("/addDiscount")
    public ResponseEntity<List<ProductSetDiscountDTO>> setDiscountsOnProducts(@RequestBody ProductDiscountRequest request) {
        List<ProductSetDiscountDTO> productDiscountDTOs = productService.setDiscountsOnProducts(request.getProductIds(), request.getDiscountId());
        return ResponseEntity.ok(productDiscountDTOs);
    }

}

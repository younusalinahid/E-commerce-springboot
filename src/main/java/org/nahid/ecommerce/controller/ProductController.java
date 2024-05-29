package org.nahid.ecommerce.controller;

import org.nahid.ecommerce.models.Product;
import org.nahid.ecommerce.response.ObjectResponse;
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


}

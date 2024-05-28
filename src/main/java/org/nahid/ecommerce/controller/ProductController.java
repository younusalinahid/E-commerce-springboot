package org.nahid.ecommerce.controller;

import org.nahid.ecommerce.ConstraintsViolationException;
import org.nahid.ecommerce.models.Product;
import org.nahid.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping()
public class ProductController {

    @Autowired
    ProductService productService;

}

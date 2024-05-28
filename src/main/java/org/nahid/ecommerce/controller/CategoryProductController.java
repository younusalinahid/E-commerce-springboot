package org.nahid.ecommerce.controller;

import org.nahid.ecommerce.ConstraintsViolationException;
import org.nahid.ecommerce.mapper.CategoryMapper;
import org.nahid.ecommerce.mapper.ProductMapper;
import org.nahid.ecommerce.models.Category;
import org.nahid.ecommerce.models.Product;
import org.nahid.ecommerce.repository.CategoryRepository;
import org.nahid.ecommerce.request.CategoryRequest;
import org.nahid.ecommerce.request.ProductRequest;
import org.nahid.ecommerce.response.ApiResponse;
import org.nahid.ecommerce.response.ObjectResponse;
import org.nahid.ecommerce.service.CategoryService;
import org.nahid.ecommerce.service.ProductService;
import org.nahid.ecommerce.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryProductController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductService productService;

    @PostMapping
    public ResponseEntity<ObjectResponse> createCategory(@RequestBody CategoryRequest categoryRequest) throws ConstraintsViolationException {
        Category savedCategory = CategoryMapper.convertCategoryRequestWithoutId(categoryRequest);
        return new ResponseEntity<>(new ObjectResponse(true, Constants.CATEGORY_CREATED,
                CategoryMapper.convertCategoryWithoutProductDTO(categoryService.createCategory(savedCategory))),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ObjectResponse> getAllCategories() {
        List<Category> category = categoryService.getAllCategories();
        ObjectResponse response = new ObjectResponse(true, "Success", category);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObjectResponse> getById(@PathVariable("id") @Valid long id) {
        Category category = categoryService.getById(id);
        return ResponseEntity.ok(new ObjectResponse(true, Constants.CATEGORY_FOUND, category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjectResponse> update(@PathVariable("id") Long id,
                                                 @Valid @RequestBody CategoryRequest categoryRequest) throws ConstraintsViolationException {
        Category category = CategoryMapper.convertCategoryRequestWithId(categoryRequest, id);
        return ResponseEntity.ok(
                new ObjectResponse(true, Constants.CATEGORY_UPDATED,
                        CategoryMapper.convertCategoryWithoutProductDTO(categoryService.update(category)))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable("id") @Valid long id) {
        categoryService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse(true, Constants.CATEGORY_DELETED));
    }

    @PostMapping("/{categoriesId}/products")
    public ResponseEntity<ObjectResponse> createProduct(@PathVariable("categoriesId") Long categoriesId,
                                                        @Valid @RequestBody ProductRequest productRequest)
            throws ConstraintsViolationException {
        Product product = ProductMapper.convertProductRequestWithoutId(categoriesId, productRequest);
        return new ResponseEntity<>(
                new ObjectResponse(true, Constants.PRODUCT_CREATED, productService.createProduct(product)),
                HttpStatus.CREATED);
    }

    @GetMapping("/{categoriesId}/products/{productsId}")
    public ResponseEntity<ObjectResponse> getStudentById(@PathVariable("categoriesId") Long categoriesId,
                                                         @PathVariable("productsId") Long productsId) {
        categoryRepository.getById(categoriesId);
        Product product = productService.getById(productsId);
        return ResponseEntity.ok(new ObjectResponse(true, Constants.PRODUCT_FOUND,product));
    }

    @PutMapping("{categoriesId}/products/{productsId}")
    public ResponseEntity<ObjectResponse> updateStudent(@PathVariable("categoriesId") Long categoriesId,
                                                        @PathVariable("productsId") Long productsId,
                                                        @Valid @RequestBody ProductRequest productRequest)
            throws ConstraintsViolationException {
        Product product = ProductMapper.convertProductRequestWithId(categoriesId, productsId, productRequest);
        return ResponseEntity.ok(
                new ObjectResponse(true, Constants.PRODUCT_UPDATED, productService.update(product))
        );
    }

    @DeleteMapping("{categoriesId}/products/{productsId}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable("categoriesId") Long categoriesId,
                                                  @PathVariable("productsId") Long productsId) {
        categoryService.getById(categoriesId);
        productService.deleteById(productsId);
        return ResponseEntity.ok(new ApiResponse(true, Constants.PRODUCT_DELETED));
    }
}

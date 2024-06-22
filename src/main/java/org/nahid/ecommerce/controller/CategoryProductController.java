package org.nahid.ecommerce.controller;

import org.nahid.ecommerce.dto.CategoryDTO;
import org.nahid.ecommerce.dto.CategoryWithProductsDTO;
import org.nahid.ecommerce.dto.ProductDTO;
import org.nahid.ecommerce.dto.ProductsWithCategoryName;
import org.nahid.ecommerce.exception.ConstraintsViolationException;
import org.nahid.ecommerce.mapper.CategoryMapper;
import org.nahid.ecommerce.mapper.ProductMapper;
import org.nahid.ecommerce.models.Category;
import org.nahid.ecommerce.models.Product;
import org.nahid.ecommerce.repository.CategoryRepository;
import org.nahid.ecommerce.request.CategoryRequest;
import org.nahid.ecommerce.request.ProductRequest;
import org.nahid.ecommerce.response.ApiResponse;
import org.nahid.ecommerce.response.ObjectResponse;
import org.nahid.ecommerce.response.PageResponse;
import org.nahid.ecommerce.service.CategoryService;
import org.nahid.ecommerce.service.ProductService;
import org.nahid.ecommerce.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
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
    public ResponseEntity<ObjectResponse> getAllCategories() throws ConstraintViolationException {
        List<CategoryDTO> category = categoryService.getAllCategories();
        ObjectResponse response = new ObjectResponse(true, Constants.CATEGORY_FOUND, category);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/home")
    public ResponseEntity<PageResponse<CategoryWithProductsDTO>> getAllCategoriesWithProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "8") int productPageSize) {

        Pageable categoryPageable = PageRequest.of(page, size);
        PageResponse<CategoryWithProductsDTO> response = categoryService.getAllCategoriesWithProducts(categoryPageable, productPageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ProductsWithCategoryName> getProductsByCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice) {

        Pageable pageable = PageRequest.of(page, size);
        ProductsWithCategoryName response = categoryService.getCategoryWithProducts(categoryId, pageable, productName, minPrice, maxPrice);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/{categoryId}")
    public ResponseEntity<ObjectResponse> update(@PathVariable("categoryId") Long id,
                                                 @Valid @RequestBody CategoryRequest categoryRequest) throws ConstraintsViolationException {
        Category category = CategoryMapper.convertCategoryRequestWithId(categoryRequest, id);
        return ResponseEntity.ok(
                new ObjectResponse(true, Constants.CATEGORY_UPDATED,
                        CategoryMapper.convertCategoryWithoutProductDTO(categoryService.update(category)))
        );
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable("id") @Valid long id) {
        categoryService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse(true, Constants.CATEGORY_DELETED));
    }

    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ObjectResponse> createProduct(@PathVariable("categoryId") Long categoriesId,
                                                        @Valid @RequestBody ProductRequest productRequest)
            throws ConstraintsViolationException {
        Product product = ProductMapper.convertProductRequestWithoutId(categoriesId, productRequest);
        return new ResponseEntity<>(
                new ObjectResponse(true, Constants.PRODUCT_CREATED, productService.createProduct(product)),
                HttpStatus.CREATED);
    }

    @GetMapping("/{categoryId}/products/{productsId}")
    public ResponseEntity<ObjectResponse> getStudentById(@PathVariable("categoryId") Long categoriesId,
                                                         @PathVariable("productsId") Long productsId) {
        categoryRepository.getById(categoriesId);
        Product product = productService.getById(productsId);
        return ResponseEntity.ok(new ObjectResponse(true, Constants.PRODUCT_FOUND,product));
    }

    @PutMapping("{categoryId}/products/{productsId}")
    public ResponseEntity<ObjectResponse> updateStudent(@PathVariable("categoryId") Long categoriesId,
                                                        @PathVariable("productsId") Long productsId,
                                                        @Valid @RequestBody ProductRequest productRequest)
            throws ConstraintsViolationException {
        Product product = ProductMapper.convertProductRequestWithId(categoriesId, productsId, productRequest);
        return ResponseEntity.ok(
                new ObjectResponse(true, Constants.PRODUCT_UPDATED, productService.update(product))
        );
    }

    @DeleteMapping("{categoryId}/products/{productsId}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable("categoryId") Long categoriesId,
                                                  @PathVariable("productsId") Long productsId) {
        categoryService.getById(categoriesId);
        productService.deleteById(productsId);
        return ResponseEntity.ok(new ApiResponse(true, Constants.PRODUCT_DELETED));
    }
}

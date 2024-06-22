package org.nahid.ecommerce.service;

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
import org.nahid.ecommerce.repository.ProductRepository;
import org.nahid.ecommerce.response.PageResponse;
import org.nahid.ecommerce.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    private static Logger logger = LoggerFactory.getLogger(CategoryService.class);

    public Category createCategory(Category category) throws ConstraintsViolationException {
        try {
            return categoryRepository.save(category);
        } catch (DataIntegrityViolationException exception) {
            logger.warn(Constants.DATA_VIOLATION + exception.getMessage());
            throw new ConstraintsViolationException(Constants.ALREADY_EXISTS);
        }
    }

    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryMapper:: toCategoryDTO)
                .collect(Collectors.toList());
    }


    public List<Category> getAllCategoriesWithProducts() {
        return categoryRepository.findAll();
    }


    public Category getById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()) {
            return category.get();
        } else {
            logger.warn(Constants.CATEGORY_NOT_FOUND + id);
            throw new EntityNotFoundException(Constants.CATEGORY_NOT_FOUND + id);
        }
    }

    public Category update(Category category) throws ConstraintsViolationException{
        Category updateCategory = getById(category.getId());
        BeanUtils.copyProperties(category, updateCategory, "id");
        try {
            return categoryRepository.save(updateCategory);
        } catch (DataIntegrityViolationException | ConstraintViolationException exception) {
            logger.warn(Constants.DATA_VIOLATION + exception.getMessage());
            throw new ConstraintsViolationException(Constants.ALREADY_EXISTS);
        }
    }

    public void deleteById(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            logger.warn(Constants.CATEGORY_NOT_FOUND + id);
            throw new EntityNotFoundException(Constants.CATEGORY_NOT_FOUND + id);
        }
    }


    public PageResponse<CategoryWithProductsDTO> getAllCategoriesWithProducts(Pageable categoryPageable, int productPageSize) {
        Page<Category> categories = categoryRepository.findAll(categoryPageable);

        List<CategoryWithProductsDTO> categoryDTOs = categories.getContent().stream()
                .map(category -> {
                    List<Product> products = productRepository.findByCategoryId(category.getId(), PageRequest.of(0, productPageSize)).getContent();
                    List<ProductDTO> productDTOList = products.stream()
                            .map(ProductMapper::toProductDTO)
                            .collect(Collectors.toList());
                    return new CategoryWithProductsDTO(category.getId(), category.getName(), productDTOList);
                })
                .collect(Collectors.toList());

        return new PageResponse<>(categoryDTOs, categories.getNumber(), categories.getSize(),
                categories.getTotalElements(), categories.getTotalPages(), categories.isLast());
    }

    public ProductsWithCategoryName getCategoryWithProducts(Long categoryId, Pageable pageable, String productName, Integer minPrice, Integer maxPrice, String sortDirection) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), "price");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        Page<Product> productsPage = productRepository.findByCategoryIdAndFilters(categoryId, productName, minPrice, maxPrice, pageable);

//        Page<Product> productsPage;
//        if (productName != null && !productName.isEmpty()) {
//            productsPage = productRepository.findByCategoryIdAndNameContaining(categoryId, productName, pageable);
//        } else if (minPrice != null && maxPrice != null) {
//            productsPage = productRepository.findByCategoryIdAndPriceRange(categoryId, minPrice, maxPrice, pageable);
//        } else {
//            productsPage = productRepository.findByCategoryId(categoryId, pageable);
//        }

        List<ProductDTO> productDTOList = productsPage.getContent()
                .stream()
                .map(ProductMapper::toProductDTO)
                .collect(Collectors.toList());

        PageResponse<ProductDTO> productPageResponse = new PageResponse<>(
                productDTOList, productsPage.getNumber(), productsPage.getSize(),
                productsPage.getTotalElements(), productsPage.getTotalPages(), productsPage.isLast()
        );

        return new ProductsWithCategoryName(category.getId(), category.getName(), productPageResponse);
    }

}

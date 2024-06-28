package org.nahid.ecommerce.service;

import org.nahid.ecommerce.dto.ProductSetDiscountDTO;
import org.nahid.ecommerce.exception.ConstraintsViolationException;
import org.nahid.ecommerce.models.Discount;
import org.nahid.ecommerce.models.Product;
import org.nahid.ecommerce.repository.DiscountRepository;
import org.nahid.ecommerce.repository.ProductRepository;
import org.nahid.ecommerce.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);


    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CompanyService companyService;

    @Autowired
    DiscountRepository discountRepository;

    public Product createProduct(Product product) throws ConstraintsViolationException {
        try {
            categoryService.getById(product.getCategory().getId());
            return productRepository.save(product);
        } catch (DataIntegrityViolationException exception) {
            logger.warn(Constants.DATA_VIOLATION + exception.getMessage());
            throw new ConstraintsViolationException(Constants.ALREADY_EXISTS);
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            logger.warn(Constants.PRODUCT_NOT_FOUND + id);
            throw new EntityNotFoundException(Constants.PRODUCT_NOT_FOUND + id);
        }
    }

    public Product update(Product product) throws ConstraintsViolationException {
        Product updateProduct = getById(product.getId());
        categoryService.getById(product.getCategory().getId());
        BeanUtils.copyProperties(product, updateProduct,"id");
        try {
            return productRepository.save(updateProduct);
        } catch (DataIntegrityViolationException | ConstraintViolationException exception) {
            logger.warn(Constants.DATA_VIOLATION + exception.getMessage());
            throw new ConstraintsViolationException(Constants.ALREADY_EXISTS);
        }
    }

    public void deleteById(Long id) {
        try {
            productRepository.deleteById(id);
        }catch (EmptyResultDataAccessException exception) {
            logger.warn(Constants.PRODUCT_NOT_FOUND + id);
            throw new EntityNotFoundException(Constants.PRODUCT_NOT_FOUND + id);
        }
    }

    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    public void setDiscountOnProducts(ProductSetDiscountDTO dto) {
        Optional<Discount> discountOpt = discountRepository.findById(dto.getDiscountId());
        if (!discountOpt.isPresent()) {
            throw new RuntimeException("Discount not found: " + dto.getDiscountId());
        }
        Discount discount = discountOpt.get();

        List<Product> products = productRepository.findAllById(dto.getProductIds());
        for (Product product : products) {
            product.setDiscount(discount);
        }
        productRepository.saveAll(products);
    }
}

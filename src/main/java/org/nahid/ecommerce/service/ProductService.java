package org.nahid.ecommerce.service;

import org.nahid.ecommerce.ConstraintsViolationException;
import org.nahid.ecommerce.models.Product;
import org.nahid.ecommerce.repository.ProductRepository;
import org.nahid.ecommerce.request.ProductRequest;
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
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);


    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryService categoryService;

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

}

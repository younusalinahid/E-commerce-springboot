package org.nahid.ecommerce.service;

import org.nahid.ecommerce.ConstraintsViolationException;
import org.nahid.ecommerce.models.Category;
import org.nahid.ecommerce.repository.CategoryRepository;
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
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    private static Logger logger = LoggerFactory.getLogger(CategoryService.class);

    public Category createCategory(Category category) throws ConstraintsViolationException {
        try {
            return categoryRepository.save(category);
        } catch (DataIntegrityViolationException exception) {
            logger.warn(Constants.DATA_VIOLATION + exception.getMessage());
            throw new ConstraintsViolationException(Constants.ALREADY_EXISTS);
        }
    }

    public List<Category> getAllCategories() {
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

    public Category update(Category category) throws ConstraintViolationException, ConstraintsViolationException {
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

}

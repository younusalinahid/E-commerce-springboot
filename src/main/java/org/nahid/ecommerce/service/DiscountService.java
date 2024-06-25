package org.nahid.ecommerce.service;

import org.nahid.ecommerce.exception.ConstraintsViolationException;
import org.nahid.ecommerce.models.Company;
import org.nahid.ecommerce.models.Discount;
import org.nahid.ecommerce.repository.CompanyRepository;
import org.nahid.ecommerce.repository.DiscountRepository;
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
public class DiscountService {

    @Autowired
    DiscountRepository discountRepository;

    @Autowired
    private ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(DiscountService.class);

    public Discount createDiscount(Discount discount) throws ConstraintsViolationException {
        try {
            return discountRepository.save(discount);
        } catch (DataIntegrityViolationException exception) {

            logger.warn(Constants.DATA_VIOLATION + exception.getMessage());
            throw new ConstraintsViolationException(Constants.ALREADY_EXISTS);
        }
    }

    public List<Discount> getAllDiscount() {
        return discountRepository.findAll();
    }

    public Discount getById(Long id) {
        Optional<Discount> discount = discountRepository.findById(id);
        if(discount.isPresent()) {
            return discount.get();
        } else {
            logger.warn(Constants.COMPANY_NOT_FOUND + id);
            throw new EntityNotFoundException(Constants.CATEGORY_NOT_FOUND + id);
        }
    }

    public Discount update(Discount discount) throws ConstraintsViolationException{
        Discount updateDiscount = getById(discount.getId());
        BeanUtils.copyProperties(discount, updateDiscount, "discountId");
        try {
            return discountRepository.save(updateDiscount);
        } catch (DataIntegrityViolationException | ConstraintViolationException exception) {
            logger.warn(Constants.DATA_VIOLATION + exception.getMessage());
            throw new ConstraintsViolationException(Constants.ALREADY_EXISTS);
        }
    }

    public void deleteById(Long id) {
        try {
            discountRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            logger.warn(Constants.COMPANY_NOT_FOUND + id);
            throw new EntityNotFoundException(Constants.COMPANY_NOT_FOUND + id);
        }
    }
}

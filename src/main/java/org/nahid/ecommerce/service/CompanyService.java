package org.nahid.ecommerce.service;

import org.nahid.ecommerce.exception.ConstraintsViolationException;
import org.nahid.ecommerce.models.Category;
import org.nahid.ecommerce.models.Company;
import org.nahid.ecommerce.models.Product;
import org.nahid.ecommerce.repository.CompanyRepository;
import org.nahid.ecommerce.request.CompanyProductRequest;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    private ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);

    public Company createCompany(Company company) throws ConstraintsViolationException {
        try {
            return companyRepository.save(company);
        } catch (DataIntegrityViolationException exception) {
            logger.warn(Constants.DATA_VIOLATION + exception.getMessage());
            throw new ConstraintsViolationException(Constants.ALREADY_EXISTS);
        }
    }

    public List<Company> getAllCompany() {
        return companyRepository.findAll();
    }

    public Company getById(Long id) {
        Optional<Company> company = companyRepository.findById(id);
        if(company.isPresent()) {
            return company.get();
        } else {
            logger.warn(Constants.COMPANY_NOT_FOUND + id);
            throw new EntityNotFoundException(Constants.CATEGORY_NOT_FOUND + id);
        }
    }

    public Company update(Company company) throws ConstraintsViolationException{
        Company updateCompany = getById(company.getId());
        BeanUtils.copyProperties(company, updateCompany, "companyId");
        try {
            return companyRepository.save(updateCompany);
        } catch (DataIntegrityViolationException | ConstraintViolationException exception) {
            logger.warn(Constants.DATA_VIOLATION + exception.getMessage());
            throw new ConstraintsViolationException(Constants.ALREADY_EXISTS);
        }
    }

    public void deleteById(Long id) {
        try {
            companyRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            logger.warn(Constants.COMPANY_NOT_FOUND + id);
            throw new EntityNotFoundException(Constants.COMPANY_NOT_FOUND + id);
        }
    }
}

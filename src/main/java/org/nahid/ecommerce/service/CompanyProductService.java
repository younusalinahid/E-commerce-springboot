package org.nahid.ecommerce.service;

import org.nahid.ecommerce.models.Company;
import org.nahid.ecommerce.models.Product;
import org.nahid.ecommerce.repository.CompanyRepository;
import org.nahid.ecommerce.request.CompanyProductRequest;
import org.nahid.ecommerce.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyProductService {

    @Autowired
    CompanyService companyService;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ProductService productService;

    public void saveProductsInCompany(CompanyProductRequest request) {
        Company company = companyService.getById(request.getCompanyId());

        List<Product> products = new ArrayList<>();
        for (Long productId : request.getProductsId()) {
            Product product = productService.getById(productId);
            products.add(product);
        }

        company.getProducts().addAll(products);
        companyRepository.save(company);
    }
}

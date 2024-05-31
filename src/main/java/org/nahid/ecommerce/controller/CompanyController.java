package org.nahid.ecommerce.controller;

import org.nahid.ecommerce.dto.CompanyProductDTO;
import org.nahid.ecommerce.exception.ConstraintsViolationException;
import org.nahid.ecommerce.mapper.CompanyMapper;
import org.nahid.ecommerce.models.Company;
import org.nahid.ecommerce.repository.CompanyRepository;
import org.nahid.ecommerce.request.CompanyRequest;
import org.nahid.ecommerce.response.ApiResponse;
import org.nahid.ecommerce.response.ObjectResponse;
import org.nahid.ecommerce.service.CompanyService;
import org.nahid.ecommerce.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @Autowired
    CompanyRepository companyRepository;

    @PostMapping
    public ResponseEntity<ObjectResponse> createCompany(@RequestBody CompanyRequest companyRequest) throws ConstraintsViolationException {
        Company savedCompany = CompanyMapper.convertCompanyRequestWithoutId(companyRequest);
        return new ResponseEntity<>(new ObjectResponse(true, Constants.COMPANY_CREATED,
                CompanyMapper.convertCompanyWithoutProductDTO(companyService.createCompany(savedCompany))),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ObjectResponse> getAllCompany() {
        List<Company> companies = companyService.getAllCompany();
        ObjectResponse response = new ObjectResponse(true, "Success", companies);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @GetMapping("/{companyId}")
//    public ResponseEntity<ObjectResponse> getById(@PathVariable("companyId") @Valid long id) {
//        Company company = companyService.getById(id);
//        return ResponseEntity.ok(new ObjectResponse(true, Constants.CATEGORY_FOUND, company));
//    }

    @GetMapping("/{companyId}")
    public ResponseEntity<ObjectResponse> getCompanyWithProducts(@PathVariable Long companyId) {
        Company company = companyService.getById(companyId);
        if (company == null) {
            return ResponseEntity.notFound().build();
        }
        CompanyProductDTO companyProductDTO = CompanyMapper.convertCompanyWithProductDTO(company);
        return ResponseEntity.ok(new ObjectResponse(true, Constants.COMPANY_FOUND_WITH_PRODUCT, companyProductDTO));
    }

    @PutMapping("{companyId}")
    public ResponseEntity<ObjectResponse> update(@PathVariable("companyId") Long id,
                                                 @Valid @RequestBody CompanyRequest companyRequest) throws ConstraintsViolationException {
        Company company = CompanyMapper.convertCompanyRequestWithId(companyRequest, id);
        return ResponseEntity.ok(
                new ObjectResponse(true, Constants.COMPANY_UPDATED,
                        CompanyMapper.convertCompanyWithoutProductDTO(companyService.update(company)))
        );
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable("companyId") @Valid long id) {
        companyService.deleteById(id);
        return ResponseEntity.ok(new ApiResponse(true, Constants.COMPANY_DELETED));
    }
}
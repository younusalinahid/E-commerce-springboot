package org.nahid.ecommerce.utils;

public class Constants {

    private Constants() {

    }

    public static final String NOT_EMPTY_NAME = "Name can not be null";
    public static final String DATA_VIOLATION = "Error: An unexpected data integrity violation occurred.";
    public static final String ALREADY_EXISTS = "Entity with the given name already exists. Please choose a unique name.";
    public static final String MIN_PRICE = "Price must be greater than or equal to 1";
    public static final String NOT_EMPTY_SIZE = "Size must not be empty";
    public static final String MIN_DESCRIPTION_LENGTH = "Description must be at least 10 characters long";


    //Categories
    public static final String CATEGORY_CREATED = "Category create successfully";
    public static final String CATEGORY_UPDATED = "Category update successfully";
    public static final String CATEGORY_NOT_FOUND = "Category not found for id";
    public static final String CATEGORY_FOUND = "Category found successfully";
    public static final String CATEGORY_DELETED = "Category delete successfully";

    //Products
    public static final String PRODUCT_CREATED = "Product create successfully";
    public static final String PRODUCT_UPDATED = "Product update successfully";
    public static final String PRODUCT_NOT_FOUND = "Product not found for id";
    public static final String PRODUCT_FOUND = "Product found successfully";
    public static final String PRODUCT_DELETED = "Product delete successfully";

    //Company
    public static final String COMPANY_CREATED = "Company create successfully";
    public static final String COMPANY_UPDATED = "Company update successfully";
    public static final String COMPANY_NOT_FOUND = "Company not found for id";
    public static final String COMPANY_FOUND_WITH_PRODUCT = "Company found successfully with products";
    public static final String COMPANY_DELETED = "Company delete successfully";

}

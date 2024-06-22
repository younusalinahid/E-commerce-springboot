package org.nahid.ecommerce.mapper;
import org.nahid.ecommerce.dto.CategoryDTO;
import org.nahid.ecommerce.models.Category;
import org.nahid.ecommerce.request.CategoryRequest;

public class CategoryMapper {

    private CategoryMapper() {

    }

    public static Category convertCategoryRequestWithoutId(CategoryRequest categoryRequest) {
        return new Category(null,
                categoryRequest.getName());
    }

    public static CategoryDTO convertCategoryWithoutProductDTO(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName()
        );
    }

    public static CategoryDTO toCategoryDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }

    public static Category convertCategoryRequestWithId(
            CategoryRequest categoryRequest, long id) {
        return new Category(
                id,
                categoryRequest.getName());
    }
}

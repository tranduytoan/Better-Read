package dbmsforeveread.foreveread.category;

import dbmsforeveread.foreveread.exceptions.BookNotFoundException;
import dbmsforeveread.foreveread.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Category not found with id: " + id));
        return convertToDTO(category);
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        Category parent = categoryRepository.findById(categoryDTO.getParentId())
                .orElse(null); // Handle null parent gracefully
        category.setParent(parent);
        category = categoryRepository.save(category);
        return convertToDTO(category);
    }

    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Category not found with id: " + id));
        category.setName(categoryDTO.getName());
        Category parent = categoryRepository.findById(categoryDTO.getParentId())
                .orElse(null);
        category.setParent(parent);
        category = categoryRepository.save(category);
        return convertToDTO(category);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Category not found with id: " + id));
        categoryRepository.delete(category);
    }

    public List<CategoryDTO> getChildCategories(Long parentId) {
        Category parent = categoryRepository.findById(parentId)
                .orElseThrow(() -> new BookNotFoundException("Parent category not found with id: " + parentId));
        return categoryRepository.findByParent(parent).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setParentId(category.getParent() != null ? category.getParent().getId() : null);
        return dto;
    }

    public List<CategoryDTO> getReversedCategoryHierarchy(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (!categoryOptional.isPresent()) {
            throw new ResourceNotFoundException("Category not found with id: " + categoryId);
        }

        List<CategoryDTO> hierarchy = new ArrayList<>();
        Category current = categoryOptional.get();
        while (current != null) {
            hierarchy.add(convertToCategoryDTO(current));
            current = current.getParent();
        }

        Collections.reverse(hierarchy);
        return hierarchy;
    }
    private CategoryDTO convertToCategoryDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setParentId(category.getParent() != null ? category.getParent().getId() : null);
        return dto;
    }
}

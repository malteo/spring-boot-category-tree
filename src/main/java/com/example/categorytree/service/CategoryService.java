package com.example.categorytree.service;

import com.example.categorytree.dto.CategoryCreateDTO;
import com.example.categorytree.dto.CategoryDTO;
import com.example.categorytree.dto.CategoryUpdateDTO;
import com.example.categorytree.entity.Category;
import com.example.categorytree.mapper.CategoryMapper;
import com.example.categorytree.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryDTO createCategory(CategoryCreateDTO createDTO) {
        Category category = categoryMapper.toEntity(createDTO);

        if (createDTO.getParentId() != null) {
            Category parent = categoryRepository.findById(createDTO.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found with id: " + createDTO.getParentId()));
            category.setParent(parent);
        }

        Category savedCategory = categoryRepository.save(category);
        categoryRepository.flush(); // Ensure the entity is persisted
        return categoryMapper.toDTO(savedCategory);
    }

    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return categoryMapper.toDTO(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toDTOList(categories);
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> getRootCategories() {
        List<Category> rootCategories = categoryRepository.findRootCategories();
        return categoryMapper.toDTOList(rootCategories);
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> getChildCategories(Long parentId) {
        List<Category> children = categoryRepository.findByParentId(parentId);
        return categoryMapper.toDTOList(children);
    }

    public CategoryDTO updateCategory(Long id, CategoryUpdateDTO updateDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        categoryMapper.updateEntity(updateDTO, category);

        if (updateDTO.getParentId() != null) {
            if (updateDTO.getParentId().equals(id)) {
                throw new RuntimeException("A category cannot be its own parent");
            }
            Category parent = categoryRepository.findById(updateDTO.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found with id: " + updateDTO.getParentId()));
            category.setParent(parent);
        } else {
            category.setParent(null);
        }

        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(updatedCategory);
    }

    /**
     * Deletes a category by ID.
     * Note: Due to CascadeType.ALL on the parent-child relationship,
     * deleting a category will also delete all its children recursively.
     * 
     * @param id the ID of the category to delete
     * @throws RuntimeException if the category is not found
     */
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        categoryRepository.delete(category);
    }
}

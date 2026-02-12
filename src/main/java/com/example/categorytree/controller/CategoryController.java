package com.example.categorytree.controller;

import com.example.categorytree.dto.CategoryDTO;
import com.example.categorytree.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO createDTO) {
        CategoryDTO createdCategory = categoryService.createCategory(createDTO);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/roots")
    public ResponseEntity<List<CategoryDTO>> getRootCategories() {
        List<CategoryDTO> rootCategories = categoryService.getRootCategories();
        return ResponseEntity.ok(rootCategories);
    }

    @GetMapping("/{id}/children")
    public ResponseEntity<List<CategoryDTO>> getChildCategories(@PathVariable Long id) {
        List<CategoryDTO> children = categoryService.getChildCategories(id);
        return ResponseEntity.ok(children);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDTO updateDTO) {
        CategoryDTO updatedCategory = categoryService.updateCategory(id, updateDTO);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}

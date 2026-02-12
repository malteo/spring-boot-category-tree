package com.example.categorytree.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryCreateDTO {

    @NotBlank(message = "Name is required")
    private String name;

    private String description;
    private Long parentId;

    public CategoryCreateDTO() {
    }

    public CategoryCreateDTO(String name, String description, Long parentId) {
        this.name = name;
        this.description = description;
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}

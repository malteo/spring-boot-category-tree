package com.example.categorytree.mapper;

import com.example.categorytree.dto.CategoryDTO;
import com.example.categorytree.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "parentId", expression = "java(mapParentId(category))")
    @Mapping(target = "children", qualifiedByName = "mapChildren")
    CategoryDTO toDTO(Category category);

    default Long mapParentId(Category category) {
        try {
            if (category.getParent() != null) {
                return category.getParent().getId();
            }
        } catch (org.hibernate.LazyInitializationException e) {
            // Parent is not loaded, return null
        }
        return null;
    }

    @Named("mapChildren")
    default List<CategoryDTO> mapChildren(List<Category> children) {
        try {
            if (children == null || children.isEmpty()) {
                return null;
            }
            return children.stream()
                    .map(this::toDTOWithoutChildren)
                    .toList();
        } catch (org.hibernate.LazyInitializationException e) {
            // Children collection is not initialized, return null
            return null;
        }
    }

    @Mapping(target = "parentId", expression = "java(mapParentId(category))")
    @Mapping(target = "children", ignore = true)
    CategoryDTO toDTOWithoutChildren(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    Category toEntity(CategoryDTO createDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    void updateEntity(CategoryDTO updateDTO, @MappingTarget Category category);

    default List<CategoryDTO> toDTOList(List<Category> categories) {
        if (categories == null) {
            return null;
        }
        return categories.stream()
                .map(this::toDTO)
                .toList();
    }
}

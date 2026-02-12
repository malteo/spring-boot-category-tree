package com.example.categorytree.mapper;

import com.example.categorytree.dto.CategoryCreateDTO;
import com.example.categorytree.dto.CategoryDTO;
import com.example.categorytree.dto.CategoryUpdateDTO;
import com.example.categorytree.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "children", qualifiedByName = "mapChildren")
    CategoryDTO toDTO(Category category);

    @Named("mapChildren")
    default List<CategoryDTO> mapChildren(List<Category> children) {
        if (children == null || children.isEmpty()) {
            return null;
        }
        return children.stream()
                .map(this::toDTOWithoutChildren)
                .toList();
    }

    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "children", ignore = true)
    CategoryDTO toDTOWithoutChildren(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    Category toEntity(CategoryCreateDTO createDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    void updateEntity(CategoryUpdateDTO updateDTO, @MappingTarget Category category);

    default List<CategoryDTO> toDTOList(List<Category> categories) {
        if (categories == null) {
            return null;
        }
        return categories.stream()
                .map(this::toDTO)
                .toList();
    }
}

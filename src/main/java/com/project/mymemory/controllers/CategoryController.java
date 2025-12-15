package com.project.mymemory.controllers;

import com.project.mymemory.services.impl.CategoryServiceImpl;
import com.project.mymemory.dto.response.ApiResponse;
import com.project.mymemory.entitys.Category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.project.mymemory.exception.ErrorsException.notFound;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
@SuppressWarnings("unused")
public class CategoryController {

    private final CategoryServiceImpl categoryServiceImpl;

    // GET ALL
    @GetMapping
    public ApiResponse<List<Category>> getAllCategory() {
        List<Category> categories = categoryServiceImpl.getAll();

        if (categories.isEmpty()) {
            throw notFound("Category not found.");
        }

        return new ApiResponse<>(
                200,
                "Get all categories successfully",
                categories
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ApiResponse<Category> getCategoryById(@PathVariable Long id) {
        return new ApiResponse<>(
                200,
                "Get categories successfully",
                categoryServiceImpl.getById(id)
        );
    }

    // CREATE
    @PostMapping
    public ApiResponse<Category> createCategory(@RequestBody Category category) {
        return new ApiResponse<>(
                201,
                "Category create successfully",
                categoryServiceImpl.create(category)
        );
    }

    // UPDATE BY ID
    @PutMapping ("/{id}")
    public ApiResponse<Category> update (
        @PathVariable Long id,
        @RequestBody Category updatedCategory
    ) {
        return new ApiResponse<>(
                200,
                "Category update successfully",
                categoryServiceImpl.update(id, updatedCategory)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        categoryServiceImpl.delete(id); // delete the category
        return new ApiResponse<>(200,
                "Delete memory successfully",
                "Deleted category with id: " + id
        );
    }

}

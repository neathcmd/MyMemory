package com.project.mymemory.services.impl;

import com.project.mymemory.entitys.Category;
import com.project.mymemory.repository.CategoryRepository;
import com.project.mymemory.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.mymemory.exception.ErrorsException.notFound;
import static com.project.mymemory.exception.ErrorsException.validation;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;


    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> notFound("Category with ID" + id + "not found."));
    }

    @Override
    public Category create(Category request) {
        if (request.getName().isEmpty()) {
            throw validation("Category name can't be empty.");
        }

        return categoryRepository.save(request);
    }

    @Override
    public Category update(Long id, Category updated) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> notFound("Category not found."));

       category.setName(updated.getName());
       category.setDescription(updated.getDescription());

       categoryRepository.save(category);
       return category;
    }

    @Override
    public void delete(Long id) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> notFound("Category not found."));
        categoryRepository.delete(category);

    }
}

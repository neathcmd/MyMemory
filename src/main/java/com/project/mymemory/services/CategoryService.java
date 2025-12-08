package com.project.mymemory.services;

import java.util.List;
import com.project.mymemory.entitys.Category;

public interface CategoryService {

    List<Category> getAll();
    Category getById(Long id);
    Category create(Category category);
    Category update(Long id, Category category);
    void delete(Long id);
}
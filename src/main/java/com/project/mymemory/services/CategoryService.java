package com.project.mymemory.services;
import com.project.mymemory.entitys.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    Category getById(Long id);
    Category create(Category request);
    Category update(Long id, Category request);
    void delete(Long id);
}

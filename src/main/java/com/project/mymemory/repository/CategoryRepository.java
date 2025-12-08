package com.project.mymemory.repository;


import com.project.mymemory.entitys.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String lifestyle);
}

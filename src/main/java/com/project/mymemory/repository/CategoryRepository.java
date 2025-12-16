package com.project.mymemory.repository;


import com.project.mymemory.entitys.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String lifestyle);
    Optional<Category> findByName(String name);
}

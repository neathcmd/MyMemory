package com.project.mymemory.seed;

import com.project.mymemory.entitys.Category;
import com.project.mymemory.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class SeedCategory implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {

        // Define categories to seed
        String[][] categories = {
                {"Technology", "Posts related to technology."},
                {"Lifestyle", "Posts about daily life and lifestyle."},
                {"Health", "Posts about health and wellness."},
                {"Education", "Posts related to learning and education."},
                {"Entertainment", "Posts about movies, music, and fun activities."},
                {"Sports", "Posts about sports, games, and physical activities."}

        };

        // Loop through each category and seed if not exists
        for (String[] cat : categories) {
            String name = cat[0];
            String description = cat[1];

            if (!categoryRepository.existsByName(name)) {
                Category category = new Category();
                category.setName(name);
                category.setDescription(description);
                categoryRepository.save(category);
                System.out.println("Seeded " + name + " category!");
            }
        }
    }
}

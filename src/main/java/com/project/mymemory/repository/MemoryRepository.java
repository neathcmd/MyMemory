package com.project.mymemory.repository;

import com.project.mymemory.entitys.Memory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemoryRepository extends JpaRepository<Memory, Long> {

    // Get all memories by user
    List<Memory> findByUserId(Long userId);

    // ===== Simple global search =====
    @Query("""
           SELECT m FROM Memory m
           WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
              OR LOWER(m.content) LIKE LOWER(CONCAT('%', :keyword, '%'))
           """)
    List<Memory> searchByKeyword(@Param("keyword") String keyword);
}

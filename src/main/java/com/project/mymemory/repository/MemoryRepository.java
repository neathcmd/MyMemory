package com.project.mymemory.repository;

import com.project.mymemory.entitys.Memory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MemoryRepository extends JpaRepository<Memory, Long> {
    List<Memory> findByUserId(Long userId);

    @Query("SELECT m FROM Memory m WHERE m.title LIKE %:keyword% OR m.content LIKE %:keyword%")
    List<Memory> searchByKeyword(@Param("keyword") String keyword);
}

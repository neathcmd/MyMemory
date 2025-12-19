package com.project.mymemory.repository;

import com.project.mymemory.entitys.Memory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MemoryRepository extends JpaRepository<Memory, Long> {
    List<Memory> findByUserId(Long userId);

    List<Memory> searchByKeyword(String trim);
}

package com.project.mymemory.services;

import com.project.mymemory.entitys.Memory;

import java.util.List;

public interface MemoryService {
    List<Memory> getAll();


    Memory getById(Long id);
    Memory create(Long userId, Memory memory);
    Memory update(Long userId, Long memoryId, Memory memory);
    String delete(Long userId, Long memoryId);
    List<Memory> getAllByUser(Long userId);
    List<Memory> search(String keyword);

    // fuzzy search by user
}

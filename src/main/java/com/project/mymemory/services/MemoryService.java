package com.project.mymemory.services;

import com.project.mymemory.entitys.Memory;

import java.util.List;

public interface MemoryService {
    List<Memory> getAll();

    Memory getById(Long id);

    Memory createMemory(Long userId, Memory memory);

    Memory updateMemory(Long userId, Long memoryId, Memory memory);

    String deleteMemory(Long userId, Long memoryId);

    List<Memory> getAllByUser(Long userId);
}

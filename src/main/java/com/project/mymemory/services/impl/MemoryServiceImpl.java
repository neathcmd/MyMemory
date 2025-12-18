package com.project.mymemory.services.impl;

import com.project.mymemory.entitys.Memory;
import com.project.mymemory.repository.MemoryRepository;
import com.project.mymemory.repository.UserRepository;
import com.project.mymemory.services.MemoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.mymemory.exception.ErrorsException.badRequest;
import static com.project.mymemory.exception.ErrorsException.notFound;
import static com.project.mymemory.exception.ErrorsException.unauthorized;

@Service
@RequiredArgsConstructor
public class MemoryServiceImpl implements MemoryService {

    private final MemoryRepository memoryRepository;
    private final UserRepository userRepository;

    @Override
    public List<Memory> getAll() {
        return memoryRepository.findAll();
    }

    @Override
    public Memory getById(Long id) {
        return memoryRepository.findById(id)
                .orElseThrow(() -> notFound("Memory with ID " + id + " not found."));
    }

    @Override
    public Memory create(Long userId, Memory memory) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> notFound("User not found."));

        memory.setUser(user);
        return memoryRepository.save(memory);
    }

    @Override
    public Memory update(Long userId, Long memoryId, Memory updated) {
        Memory memory = memoryRepository.findById(memoryId)
                .orElseThrow(() -> notFound("Memory not found."));

        if (!memory.getUser().getId().equals(userId)) {
            throw unauthorized("Unauthorized");
        }

        memory.setTitle(updated.getTitle());
        memory.setContent(updated.getContent());

        return memoryRepository.save(memory);
    }

    @Override
    public String delete(Long userId, Long memoryId) {
        Memory memory = memoryRepository.findById(memoryId)
                .orElseThrow(() -> notFound("Memory not found."));

        if (!memory.getUser().getId().equals(userId)) {
            throw unauthorized("Unauthorized");
        }

        memoryRepository.delete(memory);
        return "Memory deleted successfully.";
    }

    @Override
    public List<Memory> getAllByUser(Long userId) {
        return memoryRepository.findByUserId(userId);
    }

    // ===== Simple Global Search =====
    @Override
    public List<Memory> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw badRequest("Search keyword is required.");
        }
        return memoryRepository.searchByKeyword(keyword.trim());
    }



}

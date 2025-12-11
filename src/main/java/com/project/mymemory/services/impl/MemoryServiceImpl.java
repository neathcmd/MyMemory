package com.project.mymemory.services.impl;

import com.project.mymemory.entitys.Memory;
import com.project.mymemory.repository.MemoryRepository;
import com.project.mymemory.repository.UserRepository;
import com.project.mymemory.services.MemoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .orElseThrow(() -> notFound("Memory with ID" + id + "not found."));
    }

    @Override
    public Memory create(Long userId, Memory memory) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> notFound("Unable to create memory. Please create your account. And try again."));

        memory.setUser(user);
        return memoryRepository.save(memory);
    }

    @Override
    public Memory update(Long userId, Long memoryId, Memory updated) {
        var memory = memoryRepository.findById(memoryId)
                .orElseThrow(() -> notFound("Memory with this ID" + memoryId + "not found."));

        if (!memory.getUser().getId().equals(userId)) {
            throw unauthorized("unauthorized");
        }

        memory.setTitle(updated.getTitle());
        memory.setContent(updated.getContent());

        memoryRepository.save(memory);
        return memory;
    }

    @Override
    public String delete(Long userId, Long memoryId) {

        var memory = memoryRepository.findById(memoryId)
                .orElseThrow(() -> notFound("Memory not found."));

        memoryRepository.delete(memory);

        return "Memory deleted successfully.";
    }



    @Override
    public List<Memory> getAllByUser(Long userId) {
        return memoryRepository.findByUserId(userId);
    }
}

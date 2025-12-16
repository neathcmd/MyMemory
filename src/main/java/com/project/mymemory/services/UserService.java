package com.project.mymemory.services;
import com.project.mymemory.entitys.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getById(Long id);
    User create(User request);
    User update(Long id, User request);
    void delete(Long id);
}

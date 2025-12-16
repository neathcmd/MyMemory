package com.project.mymemory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.mymemory.entitys.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}

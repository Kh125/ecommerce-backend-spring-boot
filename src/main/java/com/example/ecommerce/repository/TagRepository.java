package com.example.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{
    Tag findByName(String tagName);
}

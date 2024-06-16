package com.example.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Tag;
import com.example.ecommerce.repository.TagRepository;

@Service
public class TagService {
    private final TagRepository tagRepository;
    
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag getTagById(Long tagId) {
        return tagRepository.findById(tagId).orElse(null);
    }

    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public Tag updateTag(Long tagId, Tag updatedTag) {
        return tagRepository.findById(tagId)
            .map(tag -> {
                if(updatedTag.getName() != null) {
                    tag.setName(updatedTag.getName());
                }
                return tagRepository.save(tag);
            })
            .orElse(null);
    }

    public void deleteTag(Long tagId) {
        tagRepository.deleteById(tagId);
    }
}

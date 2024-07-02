package com.example.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.TagDto;
import com.example.ecommerce.model.Tag;
import com.example.ecommerce.repository.TagRepository;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;
    
    public TagService(
        TagRepository tagRepository,
        ModelMapper modelMapper
    ) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    public List<TagDto> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(tag -> {
                    TagDto tagDto = modelMapper.map(tag, TagDto.class);

                    //Not to return all product list related to the tag entity
                    // tagDto.setProducts(new ArrayList<>());
                    return tagDto;
                })
                .collect(Collectors.toList());
    }

    public TagDto getTagById(Long tagId) {
        return tagRepository.findById(tagId)
                .map(tag -> modelMapper.map(tag, TagDto.class))
                .orElse(null);
    }

    public Tag createTag(TagDto tagDto) {
        Tag tag = modelMapper.map(tagDto, Tag.class);
        return tagRepository.save(tag);
    }

    public Tag updateTag(Long tagId, TagDto updatedTagDto) {
        return tagRepository.findById(tagId)
            .map(tag -> {
                if(updatedTagDto.getName() != null) {
                    tag.setName(updatedTagDto.getName());
                }
                return tagRepository.save(tag);
            })
            .orElse(null);
    }

    public void deleteTag(Long tagId) {
        tagRepository.deleteById(tagId);
    }
}

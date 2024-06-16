package com.example.ecommerce.restController;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.ecommerce.model.Tag;
import com.example.ecommerce.service.TagService;

public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/getAllTags")
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @GetMapping("/getTagById/{tagId}")
    public Tag getTagById(@PathVariable Long tagId) {
        return tagService.getTagById(tagId);
    }

    @PostMapping("/createTag")
    public Tag createTag(@RequestBody Tag tag) {
        return tagService.createTag(tag);
    }

    @PostMapping("/updateTag/{tagId}")
    public Tag updateTag(@PathVariable Long tagId, @RequestBody Tag updatedTag) {
        return tagService.updateTag(tagId, updatedTag);
    }

    @DeleteMapping("/deleteTag/{tagId}")
    public void deleteTag(@PathVariable Long tagId) {
        tagService.deleteTag(tagId);
    }
}

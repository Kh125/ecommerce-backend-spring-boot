package com.example.ecommerce.restController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.TagDto;
import com.example.ecommerce.model.Tag;
import com.example.ecommerce.service.TagService;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/getAllTags")
    public ResponseEntity<List<TagDto>> getAllTags() {
        List<TagDto> tagDtos = tagService.getAllTags();
        
        return tagDtos != null ? new ResponseEntity<>(tagDtos, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getTagById/{tagId}")
    public ResponseEntity<TagDto> getTagById(@PathVariable Long tagId) {
        TagDto tagDto = tagService.getTagById(tagId);
        
        return tagDto != null ? new ResponseEntity<>(tagDto, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/createTag")
    public ResponseEntity<Tag> createTag(@RequestBody TagDto tagDto) {
        Tag tag = tagService.createTag(tagDto);

        return tag != null ? new ResponseEntity<>(tag, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/updateTag/{tagId}")
    public ResponseEntity<Tag> updateTag(@PathVariable Long tagId, @RequestBody TagDto updatedTag) {
        Tag tag = tagService.updateTag(tagId, updatedTag);

        return tag != null ? new ResponseEntity<>(tag, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/deleteTag/{tagId}")
    public ResponseEntity<HttpStatus> deleteTag(@PathVariable Long tagId) {
        tagService.deleteTag(tagId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

package com.DubHacks.demo.controller;

import com.DubHacks.demo.database.entities.MediaEntity;
import com.DubHacks.demo.dto.MediaDto;
import com.DubHacks.demo.service.MediaService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.DubHacks.demo.database.entities.KeywordEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/media")
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Hello World");
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<MediaDto>> getPage(@PathVariable int page) {
        Page<MediaEntity> mediaPage = mediaService.getAllMedia(page);
        if (mediaPage.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Page<MediaDto> dtoPage = mediaPage.map(MediaController::toDto);
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<MediaDto> getById(@PathVariable int id) {
        Optional<MediaEntity> entity = mediaService.getMediaById(id);
        if (entity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toDto(entity.get()));
    }

    // Example search route — can later map keywords to DTOs too
    @GetMapping("/search")
    public ResponseEntity<Page<MediaDto>> search(@RequestParam List<String> tags) {
        Page<MediaEntity> p = mediaService.searchMedia(tags);
        if (p.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Page<MediaDto> dtoPage = p.map(MediaController::toDto);
        return ResponseEntity.ok(dtoPage);
    }

    public static MediaDto toDto(MediaEntity entity) {
        MediaDto dto = new MediaDto();  // uses @NoArgsConstructor

        dto.setId(entity.getId());
        dto.setOriginalTitle(entity.getOriginalTitle());
        dto.setOverview(entity.getOverview());
        dto.setRatingTmdb(entity.getRatingTmdb());
        dto.setGenres(
                entity.getKeywords().stream()
                        .map(keyword -> keyword.getName())  // ✅ explicit getter call
                        .toList()
        );

        return dto;
    }
}





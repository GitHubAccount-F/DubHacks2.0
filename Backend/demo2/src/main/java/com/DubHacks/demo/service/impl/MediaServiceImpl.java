package com.DubHacks.demo.service.impl;

import com.DubHacks.demo.database.entities.MediaEntity;
import com.DubHacks.demo.database.respository.MediaRepository;
import com.DubHacks.demo.service.MediaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MediaServiceImpl implements MediaService {

    MediaRepository mediaRepository;
    int size = 10; // items per page

    public MediaServiceImpl(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }


    @Override
    public Optional<MediaEntity> getMediaById(int id) {
        return mediaRepository.findById(id);
    }

    @Override
    public Page<MediaEntity> getAllMedia(int page) {
        Pageable pageable = PageRequest.of(page, size);
        return mediaRepository.findAll(pageable);
    }



    public Page<MediaEntity> searchMedia(List<String> tags) {
        Pageable pageable = PageRequest.of(0, 10);
        return mediaRepository.findByTags(tags, pageable);
    }

    @Override
    public List<MediaEntity> getAllMedia() {
        return mediaRepository.findAll();
    }
}

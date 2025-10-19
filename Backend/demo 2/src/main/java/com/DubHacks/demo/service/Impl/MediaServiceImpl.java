package com.DubHacks.demo.service.Impl;

import com.DubHacks.demo.database.entities.MediaEntity;
import com.DubHacks.demo.database.respository.MediaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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


    @Override // update
    public Page<MediaEntity> searchMedia(String keyword) {
        return null;
    }
}

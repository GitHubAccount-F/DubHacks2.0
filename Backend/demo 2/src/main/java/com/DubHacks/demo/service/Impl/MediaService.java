package com.DubHacks.demo.service.Impl;


import com.DubHacks.demo.database.entities.MediaEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface MediaService {

    Optional<MediaEntity> getMediaById(int id);                   // Retrieve single media
    Page<MediaEntity> getAllMedia(int page);                     // Retrieve all media     // Retrieves 20 random media

    Page<MediaEntity> searchMedia(String keyword);       // Search by title or tags
}

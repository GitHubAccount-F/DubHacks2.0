package com.DubHacks.demo.database.respository;

import com.DubHacks.demo.database.entities.MediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository  extends JpaRepository<MediaEntity, Integer> {

}

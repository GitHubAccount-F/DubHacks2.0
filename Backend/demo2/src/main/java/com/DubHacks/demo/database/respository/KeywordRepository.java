package com.DubHacks.demo.database.respository;

import com.DubHacks.demo.database.entities.KeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeywordRepository extends JpaRepository<KeywordEntity, Integer> {
    Optional<KeywordEntity> findByName(String name);
}

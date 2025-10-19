package com.DubHacks.demo.database.respository;

import com.DubHacks.demo.database.entities.MediaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MediaRepository  extends JpaRepository<MediaEntity, Integer> {

    @Query(value = """
    SELECT m FROM MediaEntity m 
    JOIN m.keywords k 
    WHERE k.name IN :tags
    GROUP BY m
    HAVING COUNT(DISTINCT k.name) = :#{#tags.size()}
    """,
            countQuery = """
    SELECT COUNT(DISTINCT m) FROM MediaEntity m 
    JOIN m.keywords k 
    WHERE k.name IN :tags
    GROUP BY m
    HAVING COUNT(DISTINCT k.name) = :#{#tags.size()}
    """)
    Page<MediaEntity> findByTags(@Param("tags") List<String> tags, Pageable pageable);


}

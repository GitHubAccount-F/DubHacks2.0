package com.DubHacks.demo.database.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import com.DubHacks.demo.database.entities.MediaEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "keyword_entity")
public class KeywordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "keywords")
    private Set<MediaEntity> media = new HashSet<>();


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }




}

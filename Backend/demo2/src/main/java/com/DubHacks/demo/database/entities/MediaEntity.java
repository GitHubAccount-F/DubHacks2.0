package com.DubHacks.demo.database.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import com.DubHacks.demo.database.entities.KeywordEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Entity
public class MediaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String originalTitle;
    private String overview;
    private double ratingTmdb;
    @ManyToMany
    @JoinTable(
            name = "media_entity_keywords",
            joinColumns = @JoinColumn(name = "media_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id")
    )
    private Set<KeywordEntity> keywords = new HashSet<>();




    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setKeywords(Set<KeywordEntity> keywords) {
        this.keywords = keywords;
    }

    public void setRatingTmdb(double ratingTmdb) {
        this.ratingTmdb = ratingTmdb;
    }


    public int getId() {
        return id;
    }

    public Set<KeywordEntity> getKeywords() {
        return keywords;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public double getRatingTmdb() {
        return ratingTmdb;
    }



}





    //rating, overview, title, genres



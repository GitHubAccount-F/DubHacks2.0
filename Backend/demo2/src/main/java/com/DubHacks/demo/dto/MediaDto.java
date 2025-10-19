package com.DubHacks.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MediaDto {
    public int id;
    public String originalTitle;
    public String overview;
    public double ratingTmdb;
    public List<String> genres;

    public void setId(int id) {
        this.id = id;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRatingTmdb(double ratingTmdb) {
        this.ratingTmdb = ratingTmdb;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

}

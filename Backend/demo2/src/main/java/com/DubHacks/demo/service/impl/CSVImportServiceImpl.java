package com.DubHacks.demo.service.impl;

import com.DubHacks.demo.database.entities.KeywordEntity;
import com.DubHacks.demo.database.entities.MediaEntity;
import com.DubHacks.demo.database.respository.KeywordRepository;
import com.DubHacks.demo.database.respository.MediaRepository;
import com.DubHacks.demo.service.CSVImportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

@Service
public class CSVImportServiceImpl implements CSVImportService {

    MediaRepository mediaRepository;
    KeywordRepository keywordRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CSVImportServiceImpl(MediaRepository mediaRepository, KeywordRepository keywordRepository) {

        this.mediaRepository = mediaRepository;
        this.keywordRepository = keywordRepository;
    }


    private Set<String> parseNamesFromJson(String jsonString) {
        Set<String> result = new HashSet<>();
        if (jsonString == null || jsonString.isBlank()) return result;

        try {
            // Fix single quotes → valid JSON
            String fixed = jsonString.replace("'", "\"");

            JsonNode array = objectMapper.readTree(fixed);
            if (array.isArray()) {
                for (JsonNode obj : array) {
                    JsonNode nameNode = obj.get("name");
                    if (nameNode != null) {
                        result.add(nameNode.asText().trim().toLowerCase());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to parse JSON: " + jsonString);
        }

        return result;
    }

    public void importCsv(String filePath) throws Exception {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            reader.readNext(); // skip header
            int count = 0;

            while ((line = reader.readNext()) != null) {
                MediaEntity movie = new MediaEntity();
                movie.setOriginalTitle(line[4]);

                movie.setOverview(line[5]);
                if (line[5].length() >= 255) {
                    movie.setOverview(line[5].substring(0, 250));
                }

                if (!line[14].isBlank())
                    movie.setRatingTmdb(Double.parseDouble(line[14]));

                // --- Parse genres + keywords ---
                String genresJson = line[2];
                String keywordsJson = line[15];

                Set<String> genreNames = parseNamesFromJson(genresJson);
                Set<String> keywordNames = parseNamesFromJson(keywordsJson);

                // Merge both into one set
                Set<String> allTags = new HashSet<>();
                allTags.addAll(genreNames);
                allTags.addAll(keywordNames);

                Set<KeywordEntity> keywordEntities = new HashSet<>();
                for (String tag : allTags) {
                    KeywordEntity keyword = keywordRepository.findByName(tag)
                            .orElseGet(() -> {
                                KeywordEntity newKeyword = new KeywordEntity();
                                newKeyword.setName(tag);
                                return keywordRepository.save(newKeyword);
                            });
                    keywordEntities.add(keyword);
                }


                movie.setKeywords(keywordEntities);

                mediaRepository.save(movie);
                System.out.println("Inserted movie #" + (++count));
            }
        }
    }

}
/*

MediaEntity movie = new MediaEntity();


                System.out.println(line[0]);
                System.out.println(line[1]);
                System.out.println(line[2]);
                System.out.println(line[3]);
                System.out.println(line[4]);
                System.out.println(line[5]);
                System.out.println(line[6]);
                System.out.println(line[7]);
                System.out.println(line[8]);

if (count == 0) {
                    System.out.println(line[0]);
                    System.out.println(line[1]);
                    System.out.println(line[2]);
                    System.out.println(line[3]);
                    System.out.println(line[4]);
                    System.out.println(line[5]);
                    System.out.println(line[6]);
                    System.out.println(line[7]);
                    System.out.println(line[8]);
                    System.out.println(line[9]);
                    System.out.println(line[10]);
                    System.out.println(line[11]);
                    System.out.println(line[12]);
                    System.out.println(line[13]);
                    System.out.println(line[14]);
                    System.out.println(line[15]);

                }
MediaEntity movie = MediaEntity.builder()
        .originalTitle(title)
        .releaseDate(date)
        .genres(genres)
        .popularity(popularity)
        .originalLanguage(language)
        .overview(overview)
        .runtime(runtime)
        .revenue(revenue)
        .ratingTmdb(rating)
        .build();


                movie.setOriginalTitle(line[0]);
                movie.setReleaseDate(Integer.parseInt(line[1]));
        movie.setGenres(line[2]);
                movie.setPopularity(Double.parseDouble(line[3]));
        movie.setOriginalLanguage(line[4]);
                movie.setOverview(line[5]);
                movie.setRuntime(Integer.parseInt(line[6]));
        movie.setRevenue(Integer.parseInt(line[7]));
        movie.setRatingTmdb(Double.parseDouble(line[8]));

        // set others if needed

        mediaRepository.save(movie);
 */
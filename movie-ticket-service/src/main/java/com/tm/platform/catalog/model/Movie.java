package com.tm.platform.catalog.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="movies")
public class Movie {

    @Id
    private String id;
    private String title;
    private String description;
    private String genre;
    private String language;
    private int durationInMinutes;
    private double rating;
    private List<String> cast;
    private List<String> activeCities;
}

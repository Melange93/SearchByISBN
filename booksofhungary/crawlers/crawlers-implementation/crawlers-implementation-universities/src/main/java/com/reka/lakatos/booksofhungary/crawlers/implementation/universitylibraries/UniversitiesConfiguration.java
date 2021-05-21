package com.reka.lakatos.booksofhungary.crawlers.implementation.universitylibraries;

import com.reka.lakatos.booksofhungary.crawlers.domain.database.CoverType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "universities")
public class UniversitiesConfiguration {

    @Value("${crawler.book-crawler.university-catalog-main-url}")
    private String universityCatalogMainUrl;

    @Bean
    public List<String> getUniversitySearchingDocumentTypes() {
        return getUniversitiesSearchingDocumentTypes().get(universityCatalogMainUrl);
    }

    private Map<String, List<String>> getUniversitiesSearchingDocumentTypes() {
        return Map.ofEntries(
                Map.entry("https://hunteka.sze.hu/", Arrays.asList("book", "edocument", "map")),
                Map.entry("http://hunteka.lib.semmelweis.hu/", Arrays.asList("book", "edocument", "map")),
                Map.entry("http://hunteka.neprajz.hu/", Arrays.asList("book", "edocument", "map")),
                Map.entry("http://hunteka.szepmuveszeti.hu/", Arrays.asList("book", "edocument", "map")),
                Map.entry("http://hunteka.hoppmuseum.hu/", Arrays.asList("book", "edocument", "map"))
        );
    }

    @Bean
    public Map<String, CoverType> getCoverTypeByDocumentType() {
        return Map.ofEntries(
                Map.entry("edocument", CoverType.DIGITAL),
                Map.entry("map", CoverType.MAP)
        );
    }
}

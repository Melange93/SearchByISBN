package com.reka.lakatos.searchbyisbn.crawler.universitylibraries;

import com.reka.lakatos.searchbyisbn.document.CoverType;
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
                Map.entry("https://hunteka.sze.hu/", Arrays.asList("book", "edocument", "map", "music")),
                Map.entry("http://hunteka.lib.semmelweis.hu/", Arrays.asList("book", "edocument", "map", "music")),
                Map.entry("http://hunteka.neprajz.hu/", Arrays.asList("book", "edocument", "map", "music")),
                Map.entry("http://hunteka.szepmuveszeti.hu/", Arrays.asList("book", "edocument", "map", "music")),
                Map.entry("https://opac3-konyvtar.hadtori.hunteka.hu/", Arrays.asList("book", "edocument", "map", "music")),
                Map.entry("http://hunteka.hoppmuseum.hu/", Arrays.asList("book", "edocument", "map", "music"))
        );
    }

    @Bean
    public Map<String, CoverType> getCoverTypeByDocumentType() {
        return Map.ofEntries(
                Map.entry("edocument", CoverType.DIGITAL),
                Map.entry("map", CoverType.MAP),
                Map.entry("music", CoverType.SOUND_RECORD)
        );
    }
}

package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary;

import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation.strategy.*;
import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.validator.PropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.validator.strategy.DefaultISBNPropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.validator.strategy.DefaultNotesPropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.propertiesvalidator.startegy.SeeAlsoPropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.service.util.BookISBNManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "ervin")
public class StrategyConfiguration {

    private final BookISBNManager bookISBNManager;

    @Bean
    public Map<PropertyUpdatingStrategy, String> getBookPropertyUpdatingStrategyMap() {
        return Map.ofEntries(
                Map.entry(new DefaultTitlePropertyUpdatingStrategy(), "Cím:"),
                Map.entry(new DefaultSpecialCoverTypePropertyUpdatingStrategy(), "Cím:"),
                Map.entry(new DefaultAuthorPropertyUpdatingStrategy(), "Szerző:"),
                Map.entry(new DefaultISBNPropertyUpdatingStrategy(), "ISBN:"),
                Map.entry(new DefaultBasicCoverTypePropertyUpdatingStrategy(), "ISBN:"),
                Map.entry(new DefaultPublisherPropertyUpdatingStrategy(), "Megjelenés:"),
                Map.entry(new DefaultDatePropertyUpdatingStrategy(), "Dátum:"),
                Map.entry(new DefaultEditionNumberPropertyUpdatingStrategy("[\\d]+(?=\\.\\skiad\\.)"), "Dátum:"),
                Map.entry(new DefaultThicknessPropertyUpdatingStrategy(), "Terjedelem:"),
                Map.entry(new DefaultPageNumberPropertyUpdatingStrategy(), "Terjedelem:"),
                Map.entry(new DefaultContributorsPropertyUpdatingStrategy(), "Egyéb nevek:")
        );
    }

    @Bean
    public Map<String, PropertyValidatorStrategy> getPropertyValidatorStrategyMap() {
        return Map.ofEntries(
                Map.entry("Megjegyzések:", new DefaultNotesPropertyValidatorStrategy()),
                Map.entry("Lásd még:", new SeeAlsoPropertyValidatorStrategy()),
                Map.entry("ISBN:", new DefaultISBNPropertyValidatorStrategy(bookISBNManager))
        );
    }
}

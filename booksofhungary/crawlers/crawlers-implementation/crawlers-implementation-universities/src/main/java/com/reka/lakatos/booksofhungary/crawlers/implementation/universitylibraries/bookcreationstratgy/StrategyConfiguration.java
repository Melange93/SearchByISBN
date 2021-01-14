package com.reka.lakatos.booksofhungary.crawlers.implementation.universitylibraries.bookcreationstratgy;

import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.DefaultBookListPreparatory;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.strategy.*;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.validator.PropertyValidatorStrategy;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.validator.strategy.DefaultISBNPropertyValidatorStrategy;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.validator.strategy.DefaultNotValidPropertyValidatorStrategy;
import com.reka.lakatos.booksofhungary.crawlers.implementation.universitylibraries.bookcreationstratgy.creation.PublisherPropertyUpdatingStrategy;
import com.reka.lakatos.booksofhungary.crawlers.implementation.universitylibraries.bookcreationstratgy.creation.TitlePropertyUpdatingStrategy;
import com.reka.lakatos.booksofhungary.isbnmanager.service.BookISBNManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "universities")
public class StrategyConfiguration {

    private final BookISBNManager bookISBNManager;

    @Bean
    public Map<PropertyUpdatingStrategy, String> getBookPropertyUpdatingStrategyMap() {
        return Map.ofEntries(
                Map.entry(new TitlePropertyUpdatingStrategy(), "Cím:"),
                Map.entry(new DefaultAuthorPropertyUpdatingStrategy(), "Személynév:"),
                Map.entry(new DefaultDatePropertyUpdatingStrategy(), "Megj. éve:"),
                Map.entry(new PublisherPropertyUpdatingStrategy(), "Kiadó neve:"),
                Map.entry(new DefaultThicknessPropertyUpdatingStrategy(), "Terjedelem, fizikai jellemzők:"),
                Map.entry(new DefaultPageNumberPropertyUpdatingStrategy(), "Terjedelem, fizikai jellemzők:"),
                Map.entry(new DefaultISBNPropertyUpdatingStrategy(), "ISBN:"),
                Map.entry(new DefaultEditionNumberPropertyUpdatingStrategy("[\\d]+"), "Kiadás:"),
                Map.entry(new DefaultContributorsPropertyUpdatingStrategy(), "További személynév:")
        );
    }

    @Bean
    public Map<String, PropertyValidatorStrategy> getBookPropertyValidationStrategyMap() {
        return Map.ofEntries(
                Map.entry("ISBN:", new DefaultISBNPropertyValidatorStrategy(bookISBNManager)),
                Map.entry("Sorozat:", new DefaultNotValidPropertyValidatorStrategy())
        );
    }

    @Bean
    public DefaultBookListPreparatory getDefaultBookListPreparatory() {
        return new DefaultBookListPreparatory("További személynév:");
    }
}

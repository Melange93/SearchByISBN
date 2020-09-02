package com.reka.lakatos.searchbyisbn.crawler.universitylibraries.bookcreationstratgy;

import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.DefaultBookListPreparatory;
import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation.*;
import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.validator.PropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.validator.strategy.DefaultISBNPropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.validator.strategy.DefaultNotValidPropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.crawler.universitylibraries.bookcreationstratgy.creation.PublisherPropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.universitylibraries.bookcreationstratgy.creation.TitlePropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.service.util.BookISBNManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "universities")
public class StrategyConfiguration {

    private final BookISBNManager bookISBNManager;

    @Bean
    public Map<String, List<PropertyUpdatingStrategy>> getBookPropertyUpdatingStrategyMap() {
        return Map.ofEntries(
                Map.entry("Cím:", Collections.singletonList(new TitlePropertyUpdatingStrategy())),
                Map.entry("Személynév:", Collections.singletonList(new DefaultAuthorPropertyUpdatingStrategy())),
                Map.entry("Megj. éve:", Collections.singletonList(new DefaultDatePropertyUpdatingStrategy())),
                Map.entry("Kiadó neve:", Collections.singletonList(new PublisherPropertyUpdatingStrategy())),
                Map.entry("Terjedelem, fizikai jellemzők:", Arrays.asList(new DefaultThicknessPropertyUpdatingStrategy(), new DefaultPageNumberPropertyUpdatingStrategy())),
                Map.entry("ISBN:", Collections.singletonList(new DefaultISBNPropertyUpdatingStrategy())),
                Map.entry("Kiadás:", Collections.singletonList(new DefaultEditionNumberPropertyUpdatingStrategy("[\\d]+"))),
                Map.entry("További személynév:", Collections.singletonList(new DefaultContributorsPropertyUpdatingStrategy()))
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

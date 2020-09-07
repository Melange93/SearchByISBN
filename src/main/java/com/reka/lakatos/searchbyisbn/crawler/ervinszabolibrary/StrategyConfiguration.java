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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "ervin")
public class StrategyConfiguration {

    private final BookISBNManager bookISBNManager;

    @Bean
    public Map<String, List<PropertyUpdatingStrategy>> getBookPropertyUpdatingStrategyMap() {
        return Map.ofEntries(
                Map.entry("Cím:", Arrays.asList(new DefaultTitlePropertyUpdatingStrategy(), new DefaultSpecialCoverTypePropertyUpdatingStrategy())),
                Map.entry("Szerző:", Collections.singletonList(new DefaultAuthorPropertyUpdatingStrategy())),
                Map.entry("ISBN:", Arrays.asList(new DefaultISBNPropertyUpdatingStrategy(), new DefaultBasicCoverTypePropertyUpdatingStrategy())),
                Map.entry("Megjelenés:", Collections.singletonList(new DefaultPublisherPropertyUpdatingStrategy())),
                Map.entry("Dátum:", Arrays.asList(new DefaultDatePropertyUpdatingStrategy(), new DefaultEditionNumberPropertyUpdatingStrategy("[\\d]+(?=\\.\\skiad\\.)"))),
                Map.entry("Terjedelem:", Arrays.asList(new DefaultThicknessPropertyUpdatingStrategy(), new DefaultPageNumberPropertyUpdatingStrategy())),
                Map.entry("Egyéb nevek:", Collections.singletonList(new DefaultContributorsPropertyUpdatingStrategy()))
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

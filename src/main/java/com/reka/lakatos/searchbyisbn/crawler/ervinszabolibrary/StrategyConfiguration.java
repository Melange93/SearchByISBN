package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary;

import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation.*;
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
    public Map<String, PropertyUpdatingStrategy> getBookPropertyUpdatingStrategyMap() {
        return Map.ofEntries(
                Map.entry("Cím:", new DefaultTitlePropertyUpdatingStrategy()),
                Map.entry("Szerző:", new DefaultAuthorPropertyUpdatingStrategy()),
                Map.entry("ISBN:", new DefaultISBNPropertyUpdatingStrategy()),
                Map.entry("Megjelenés:", new DefaultPublisherPropertyUpdatingStrategy()),
                Map.entry("Dátum:", new DefaultDatePropertyUpdatingStrategy()),
                Map.entry("Terjedelem:", new DefaultSizePropertyUpdatingStrategy()),
                Map.entry("Egyéb nevek:", new DefaultContributorsPropertyUpdatingStrategy())
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

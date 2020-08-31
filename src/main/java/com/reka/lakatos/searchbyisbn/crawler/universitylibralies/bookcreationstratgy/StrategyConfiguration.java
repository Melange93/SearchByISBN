package com.reka.lakatos.searchbyisbn.crawler.universitylibralies.bookcreationstratgy;

import com.reka.lakatos.searchbyisbn.crawler.bookcreation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.bookcreation.defaultstrategies.*;
import com.reka.lakatos.searchbyisbn.crawler.bookcreation.validator.PropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.crawler.bookcreation.validator.strategy.DefaultISBNPropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.crawler.bookcreation.validator.strategy.DefaultNotValidPropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.crawler.universitylibralies.bookcreationstratgy.creation.DatePropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.universitylibralies.bookcreationstratgy.creation.PublisherPropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.universitylibralies.bookcreationstratgy.creation.TitlePropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.service.util.BookISBNManager;
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
    public Map<String, PropertyUpdatingStrategy> getBookPropertyUpdatingStrategyMap() {
        return Map.ofEntries(
                Map.entry("Cím:", new TitlePropertyUpdatingStrategy()),
                Map.entry("Személynév:", new DefaultAuthorPropertyUpdatingStrategy()),
                Map.entry("Megj. éve:", new DatePropertyUpdatingStrategy()),
                Map.entry("Kiadó neve:", new PublisherPropertyUpdatingStrategy()),
                Map.entry("Terjedelem, fizikai jellemzők:", new DefaultSizePropertyUpdatingStrategy()),
                Map.entry("ISBN:", new DefaultISBNPropertyUpdatingStrategy()),
                Map.entry("Kiadás:", new DefaultEditionNumberPropetryUpdatingStrategy()),
                Map.entry("További személynév:", new DefaultContributorsPropertyUpdatingStrategy())
        );
    }

    @Bean
    public Map<String, PropertyValidatorStrategy> getBookPropertyValidationStrategyMap() {
        return Map.ofEntries(
                Map.entry("ISBN:", new DefaultISBNPropertyValidatorStrategy(bookISBNManager)),
                Map.entry("Sorozat:", new DefaultNotValidPropertyValidatorStrategy())
        );
    }

}

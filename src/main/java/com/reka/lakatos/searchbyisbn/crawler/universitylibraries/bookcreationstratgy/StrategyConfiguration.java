package com.reka.lakatos.searchbyisbn.crawler.universitylibraries.bookcreationstratgy;

import com.reka.lakatos.searchbyisbn.crawler.bookcreation.DefaultBookListPreparatory;
import com.reka.lakatos.searchbyisbn.crawler.bookcreation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.bookcreation.defaultstrategies.DefaultAuthorPropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.bookcreation.defaultstrategies.DefaultContributorsPropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.bookcreation.defaultstrategies.DefaultEditionNumberPropetryUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.bookcreation.defaultstrategies.DefaultSizePropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.bookcreation.validator.PropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.crawler.bookcreation.validator.strategy.DefaultISBNPropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.crawler.bookcreation.validator.strategy.DefaultNotValidPropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.crawler.universitylibraries.bookcreationstratgy.creation.DatePropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.universitylibraries.bookcreationstratgy.creation.ISBNPropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.universitylibraries.bookcreationstratgy.creation.PublisherPropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.universitylibraries.bookcreationstratgy.creation.TitlePropertyUpdatingStrategy;
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
                Map.entry("ISBN:", new ISBNPropertyUpdatingStrategy()),
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

    @Bean
    public DefaultBookListPreparatory getDefaultBookListPreparatory() {
        return new DefaultBookListPreparatory("További személynév:");
    }
}

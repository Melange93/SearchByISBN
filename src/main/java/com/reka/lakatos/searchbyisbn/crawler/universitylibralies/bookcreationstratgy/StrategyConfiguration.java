package com.reka.lakatos.searchbyisbn.crawler.universitylibralies.bookcreationstratgy;

import com.reka.lakatos.searchbyisbn.crawler.bookcreation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.bookcreation.defaultstrategies.*;
import com.reka.lakatos.searchbyisbn.crawler.universitylibralies.bookcreationstratgy.creation.DatePropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.universitylibralies.bookcreationstratgy.creation.PublisherPropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.universitylibralies.bookcreationstratgy.creation.TitlePropertyUpdatingStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "universities")
public class StrategyConfiguration {

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

}

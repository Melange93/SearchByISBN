package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary;

import com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.strategy.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class StrategyConfiguration {

    @Bean
    public Map<String, PropertyUpdatingStrategy> getBookPropertyUpdatingStrategyMap() {
        return Map.ofEntries(
                Map.entry("Cím:", new TitlePropertyUpdatingStrategy()),
                Map.entry("Szerző:", new AuthorPropertyUpdatingStrategy()),
                Map.entry("ISBN:", new ISBNPropertyUpdatingStrategy()),
                Map.entry("Megjelenés:", new PublisherPropertyUpdatingStrategy()),
                Map.entry("Dátum:", new DatePropertyUpdatingStrategy()),
                Map.entry("Terjedelem:", new SizePropertyUpdatingStrategy()),
                Map.entry("Egyéb nevek:", new ContributorsPropertyUpdatingStrategy())
        );
    }
}

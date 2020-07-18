package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary;

import com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.strategy.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class StrategyConfiguration {

    @Bean
    public Map<String, BookPropertyUpdatingStrategy> getBookPropertyUpdatingStrategyMap() {
        return Map.ofEntries(
                Map.entry("Cím:", new TitleBookPropertyUpdatingStrategy()),
                Map.entry("Szerző:", new AuthorBookPropertyUpdatingStrategy()),
                Map.entry("ISBN:", new ISBNBookPropertyUpdatingStrategy()),
                Map.entry("Megjelenés:", new PublisherBookPropertyUpdatingStrategy()),
                Map.entry("Dátum:", new DateBookPropertyUpdatingStrategy()),
                Map.entry("Terjedelem:", new SizeBookPropertyUpdatingStrategy()),
                Map.entry("Egyéb nevek:", new ContributorsBookPropertyUpdatingStrategy())
        );
    }
}

package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation;

import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation.updatingtrategy.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class SzechenyiStrategyConfiguration {

    @Bean
    public Map<String, PropertiesUpdatingStrategy> getSzechenyiBookPropertyUpdatingStrategyMap() {
        return Map.ofEntries(
                Map.entry("ISBN :", new ISBNPropertiesUpdatingStrategy()),
                Map.entry("Terj./Fiz. jell.:", new SizePropertiesUpdatingStrategy()),
                Map.entry("Cím és szerzőségi közlés:", new TitlePropertiesUpdatingStrategy()),
                Map.entry("Név/nevek:", new ContributorsPropertiesUpdatingStrategy()),
                Map.entry("Szerző:", new AuthorPropertiesUpdatingStrategy()),
                Map.entry("Megjelenés:", new PublisherPropertiesUpdatingStrategy()),
                Map.entry("Kiadás:", new EditionNumberPropertiesUpdatingStrategy())
        );
    }

}

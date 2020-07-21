package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary;

import com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.propertiesvalidator.startegy.ISBNPropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.propertiesvalidator.startegy.NotesPropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.propertiesvalidator.startegy.PropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.propertiesvalidator.startegy.SeeAlsoPropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.strategy.*;
import com.reka.lakatos.searchbyisbn.service.util.BookISBNManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class StrategyConfiguration {

    private final BookISBNManager bookISBNManager;

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

    @Bean Map<String, PropertyValidatorStrategy> getPropertyValidatorStrategyMap() {
        return Map.ofEntries(
                Map.entry("Megjegyzések:", new NotesPropertyValidatorStrategy()),
                Map.entry("Lásd még:", new SeeAlsoPropertyValidatorStrategy()),
                Map.entry("ISBN:", new ISBNPropertyValidatorStrategy(bookISBNManager))
        );
    }
}

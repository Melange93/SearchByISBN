package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation;

import com.reka.lakatos.searchbyisbn.crawler.bookcreation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.bookcreation.defaultstrategies.DefaultAuthorPropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.bookcreation.defaultstrategies.DefaultContributorsPropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.bookcreation.defaultstrategies.DefaultISBNPropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.bookcreation.defaultstrategies.DefaultSizePropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.bookcreation.validator.PropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.crawler.bookcreation.validator.strategy.DefaultISBNPropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation.updatingtrategy.EditionNumberPropertiesUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation.updatingtrategy.PublisherPropertiesUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation.updatingtrategy.TitlePropertiesUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.document.CoverType;
import com.reka.lakatos.searchbyisbn.service.util.BookISBNManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class StrategyConfiguration {

    private final BookISBNManager bookISBNManager;

    @Bean
    public Map<String, PropertyUpdatingStrategy> getBookPropertyUpdatingStrategyMap() {
        return Map.ofEntries(
                Map.entry("ISBN :", new DefaultISBNPropertyUpdatingStrategy()),
                Map.entry("Terj./Fiz. jell.:", new DefaultSizePropertyUpdatingStrategy()),
                Map.entry("Cím és szerzőségi közlés:", new TitlePropertiesUpdatingStrategy(getCoverTypeConverter())),
                Map.entry("Név/nevek:", new DefaultContributorsPropertyUpdatingStrategy()),
                Map.entry("Szerző:", new DefaultAuthorPropertyUpdatingStrategy()),
                Map.entry("Megjelenés:", new PublisherPropertiesUpdatingStrategy()),
                Map.entry("Kiadás:", new EditionNumberPropertiesUpdatingStrategy())
        );
    }

    @Bean
    public Map<String, CoverType> getCoverTypeConverter() {
        return Map.ofEntries(
                Map.entry("hangfelvétel", CoverType.SOUND_RECORD),
                Map.entry("elektronikus dok.", CoverType.DIGITAL),
                Map.entry("elektronikus kartográfiai dok.", CoverType.DIGITAL),
                Map.entry("nyomtatott kotta", CoverType.SHEET_MUSIC)
        );
    }

    @Bean
    public Map<String, PropertyValidatorStrategy> getPropertyValidatorStrategyMap() {
        return Map.ofEntries(
                Map.entry("ISBN :", new DefaultISBNPropertyValidatorStrategy(bookISBNManager))
        );
    }
}

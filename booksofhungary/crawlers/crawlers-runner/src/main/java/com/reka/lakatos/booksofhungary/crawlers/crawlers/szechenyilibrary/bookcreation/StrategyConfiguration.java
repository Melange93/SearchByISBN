package com.reka.lakatos.booksofhungary.crawlers.crawlers.szechenyilibrary.bookcreation;

import com.reka.lakatos.booksofhungary.crawlers.crawlers.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.booksofhungary.crawlers.crawlers.defaultbookcreation.creation.strategy.*;
import com.reka.lakatos.booksofhungary.crawlers.crawlers.defaultbookcreation.validator.PropertyValidatorStrategy;
import com.reka.lakatos.booksofhungary.crawlers.crawlers.defaultbookcreation.validator.strategy.DefaultISBNPropertyValidatorStrategy;
import com.reka.lakatos.booksofhungary.crawlers.crawlers.szechenyilibrary.bookcreation.updatingtrategy.*;
import com.reka.lakatos.booksofhungary.crawlers.crawlers.szechenyilibrary.bookcreation.validation.ExtendISBNPropertyValidatorStrategy;
import com.reka.lakatos.booksofhungary.crawlers.domain.database.CoverType;
import com.reka.lakatos.booksofhungary.crawlers.service.registrationservice.BookISBNManager;
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
    public Map<PropertyUpdatingStrategy, String> getBookPropertyUpdatingStrategyMap() {
        return Map.ofEntries(
                Map.entry(new DefaultISBNPropertyUpdatingStrategy(), "ISBN :"),
                Map.entry(new DefaultBasicCoverTypePropertyUpdatingStrategy(), "ISBN :"),
                Map.entry(new DefaultThicknessPropertyUpdatingStrategy(), "Terj./Fiz. jell.:"),
                Map.entry(new DefaultPageNumberPropertyUpdatingStrategy(), "Terj./Fiz. jell.:"),
                Map.entry(new DefaultTitlePropertyUpdatingStrategy(), "Cím és szerzőségi közlés:"),
                Map.entry(new SpecialCoverTypeInTitlePropertyUpdatingStrategy(getCoverTypeConverter()), "Cím és szerzőségi közlés:"),
                Map.entry(new DefaultContributorsPropertyUpdatingStrategy(), "Név/nevek:"),
                Map.entry(new DefaultAuthorPropertyUpdatingStrategy(), "Szerző:"),
                Map.entry(new PublisherPropertyUpdatingStrategy(), "Megjelenés:"),
                Map.entry(new DefaultDatePropertyUpdatingStrategy(), "Megjelenés:"),
                Map.entry(new DefaultEditionNumberPropertyUpdatingStrategy("[\\d]+"), "Kiadás:"),
                Map.entry(new SpecialCoverTypeMapAndDigitalCheckPropertyUpdatingStrategy(getCoverTypeConverter()), "Tárgyszavak:"),
                Map.entry(new SpecialCoverTypeMapCheckerPropertyUpdatingStrategy(), "Egységesített cím - főtétel:"),
                Map.entry(new SpecialCoverTypeDigitalCheckerPropertyUpdatingStrategy(), "Elektr. dok. jell.:")
        );
    }

    @Bean
    public Map<String, CoverType> getCoverTypeConverter() {
        return Map.ofEntries(
                Map.entry("hangfelvétel", CoverType.SOUND_RECORD),
                Map.entry("elektronikus dok.", CoverType.DIGITAL),
                Map.entry("elektronikus kartográfiai dok.", CoverType.DIGITAL),
                Map.entry("nyomtatott kotta", CoverType.MUSIC_BOOK),
                Map.entry("kotta", CoverType.MUSIC_BOOK),
                Map.entry("kartográfiai dokumentum", CoverType.MAP),
                Map.entry("elektronikus dokumentum", CoverType.DIGITAL),
                Map.entry("térkép", CoverType.MAP)
        );
    }

    @Bean
    public Map<String, PropertyValidatorStrategy> getPropertyValidatorStrategyMap() {
        return Map.ofEntries(
                Map.entry("ISBN :", new ExtendISBNPropertyValidatorStrategy(
                                new DefaultISBNPropertyValidatorStrategy(bookISBNManager),
                                getPropertyValueContainsResult()
                        )
                )
        );
    }

    @Bean
    public Map<String, Boolean> getPropertyValueContainsResult() {
        return Map.ofEntries(
                Map.entry("hibás", false),
                Map.entry("*", false)
        );
    }
}

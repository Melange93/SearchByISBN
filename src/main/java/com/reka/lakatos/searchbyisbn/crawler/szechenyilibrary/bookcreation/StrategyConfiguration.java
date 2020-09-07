package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation;

import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation.strategy.*;
import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.validator.PropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.validator.strategy.DefaultISBNPropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation.updatingtrategy.*;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation.validation.ExtendISBNPropertyValidatorStrategy;
import com.reka.lakatos.searchbyisbn.document.CoverType;
import com.reka.lakatos.searchbyisbn.service.util.BookISBNManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class StrategyConfiguration {

    private final BookISBNManager bookISBNManager;

    @Bean
    public Map<String, List<PropertyUpdatingStrategy>> getBookPropertyUpdatingStrategyMap() {
        return Map.ofEntries(
                Map.entry("ISBN :", Arrays.asList(new DefaultISBNPropertyUpdatingStrategy(), new DefaultBasicCoverTypePropertyUpdatingStrategy())),
                Map.entry("Terj./Fiz. jell.:", Arrays.asList(new DefaultThicknessPropertyUpdatingStrategy(), new DefaultPageNumberPropertyUpdatingStrategy())),
                Map.entry("Cím és szerzőségi közlés:", Arrays.asList(new DefaultTitlePropertyUpdatingStrategy(), new SpecialCoverTypeInTitlePropertyUpdatingStrategy(getCoverTypeConverter()))),
                Map.entry("Név/nevek:", Collections.singletonList(new DefaultContributorsPropertyUpdatingStrategy())),
                Map.entry("Szerző:", Collections.singletonList(new DefaultAuthorPropertyUpdatingStrategy())),
                Map.entry("Megjelenés:", Arrays.asList(new PublisherPropertyUpdatingStrategy(), new DefaultDatePropertyUpdatingStrategy())),
                Map.entry("Kiadás:", Collections.singletonList(new DefaultEditionNumberPropertyUpdatingStrategy("[\\d]+"))),
                Map.entry("Tárgyszavak:", Collections.singletonList(new SpecialCoverTypeMapAndDigitalCheckPropertyUpdatingStrategy(getCoverTypeConverter()))),
                Map.entry("Egységesített cím - főtétel:", Collections.singletonList(new SpecialCoverTypeMapCheckerPropertyUpdatingStrategy())),
                Map.entry("Elektr. dok. jell.:", Collections.singletonList(new SpecialCoverTypeDigitalCheckerPropertyUpdatingStrategy()))
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

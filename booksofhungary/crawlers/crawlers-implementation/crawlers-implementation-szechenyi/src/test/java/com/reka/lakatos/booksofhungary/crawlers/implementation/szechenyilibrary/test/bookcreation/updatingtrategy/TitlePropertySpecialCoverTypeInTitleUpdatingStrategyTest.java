package com.reka.lakatos.booksofhungary.crawlers.implementation.szechenyilibrary.test.bookcreation.updatingtrategy;

import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import com.reka.lakatos.booksofhungary.crawlers.domain.database.CoverType;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.booksofhungary.crawlers.implementation.szechenyilibrary.bookcreation.updatingtrategy.SpecialCoverTypeInTitlePropertyUpdatingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TitlePropertySpecialCoverTypeInTitleUpdatingStrategyTest {

    @Mock
    private Map<String, CoverType> coverTypeConverter;

    private PropertyUpdatingStrategy propertyUpdatingStrategy;

    @BeforeEach
    void init() {
        propertyUpdatingStrategy = new SpecialCoverTypeInTitlePropertyUpdatingStrategy(coverTypeConverter);
    }

    @Test
    void updatePropertyCoverTypeHaveOne() {
        Book book = Book.builder().coverType(CoverType.PAPERBACK).build();
        String property = "test";
        propertyUpdatingStrategy.updateProperty(book, property);

        CoverType result = book.getCoverType();
        assertThat(result).isEqualTo(CoverType.PAPERBACK);
    }

    @Test
    void updatePropertyCoverTypeSoundRecord() {
        Book book = Book.builder().build();
        String property = "Zöldcsillag [hangfelvétel] : Radics Béla felvételei / Tűzkerék.";
        when(coverTypeConverter.get("hangfelvétel")).thenReturn(CoverType.SOUND_RECORD);
        propertyUpdatingStrategy.updateProperty(book, property);

        CoverType result = book.getCoverType();
        assertThat(result).isEqualTo(CoverType.SOUND_RECORD);
    }

    @Test
    void updatePropertyCoverTypeDigital() {
        Book book = Book.builder().build();
        String property = "Klasszikus zenék [elektronikus dok.] : [hangos zenei lexikon].";
        when(coverTypeConverter.get("elektronikus dok.")).thenReturn(CoverType.DIGITAL);
        propertyUpdatingStrategy.updateProperty(book, property);

        CoverType result = book.getCoverType();
        assertThat(result).isEqualTo(CoverType.DIGITAL);
    }

    @Test
    void updatePropertyCoverTypeDigitalMap() {
        Book book = Book.builder().build();
        String property = "Nógrád megye 1867-1868 [elektronikus kartográfiai dok.] / Biszak Sándor, Timár Gábor.";
        when(coverTypeConverter.get("elektronikus kartográfiai dok.")).thenReturn(CoverType.DIGITAL);
        propertyUpdatingStrategy.updateProperty(book, property);

        CoverType result = book.getCoverType();
        assertThat(result).isEqualTo(CoverType.DIGITAL);
    }
}

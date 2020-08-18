package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation.updatingtrategy;

import com.reka.lakatos.searchbyisbn.crawler.bookcreation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.CoverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TitlePropertyUpdatingStrategyTest {

    @Mock
    private Map<String, CoverType> coverTypeConverter;

    private PropertyUpdatingStrategy propertyUpdatingStrategy;

    @BeforeEach
    void init() {
        propertyUpdatingStrategy = new TitlePropertyUpdatingStrategy(coverTypeConverter);
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
    void updatePropertyCoverTypeSheetMusic1() {
        Book book = Book.builder().build();
        String property = "55 kétszólamú énekgyakorlat [nyomtatott kotta] : felső szólam / Kodály Zoltán.";
        when(coverTypeConverter.get("nyomtatott kotta")).thenReturn(CoverType.SHEET_MUSIC);
        propertyUpdatingStrategy.updateProperty(book, property);

        CoverType result = book.getCoverType();
        assertThat(result).isEqualTo(CoverType.SHEET_MUSIC);
    }

    @Test
    void updatePropertyCoverTypeSheetMusic2() {
        Book book = Book.builder().build();
        String property = "55 kétszólamú énekgyakorlat [kotta] : felső szólam / Kodály Zoltán.";
        when(coverTypeConverter.get("kotta")).thenReturn(CoverType.SHEET_MUSIC);
        propertyUpdatingStrategy.updateProperty(book, property);

        CoverType result = book.getCoverType();
        assertThat(result).isEqualTo(CoverType.SHEET_MUSIC);
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

    @Test
    void updatePropertyTitle() {
        Book book = Book.builder().build();
        String property = "Nógrád megye 1867-1868 [elektronikus kartográfiai dok.] / Biszak Sándor, Timár Gábor.";
        propertyUpdatingStrategy.updateProperty(book, property);

        String title = book.getTitle();
        assertThat(title).isEqualTo("Nógrád megye 1867-1868");
    }

    @Test
    void updatePropertyTitleWithSubtitle() {
        Book book = Book.builder().build();
        String property = "55 kétszólamú énekgyakorlat [nyomtatott kotta] : felső szólam / Kodály Zoltán.";
        propertyUpdatingStrategy.updateProperty(book, property);

        String title = book.getTitle();
        assertThat(title).isEqualTo("55 kétszólamú énekgyakorlat : felső szólam");
    }

    @Test
    void updatePropertyTitleWithSubtitleWithOutSlash() {
        Book book = Book.builder().build();
        String property = "55 kétszólamú énekgyakorlat [nyomtatott kotta] : felső szólam Kodály Zoltán.";
        propertyUpdatingStrategy.updateProperty(book, property);

        String title = book.getTitle();
        assertThat(title).isNull();
    }
}
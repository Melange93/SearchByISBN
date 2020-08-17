package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation.updatingtrategy;

import com.reka.lakatos.searchbyisbn.crawler.bookcreation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.CoverType;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpecialCoverTypeMapAndDigitalCheckPropertyUpdatingStrategyTest {
    @Mock
    private Map<String, CoverType> coverTypeConverter;

    private PropertyUpdatingStrategy propertyUpdatingStrategy;

    private static final Set<String> keySet = Sets.newHashSet(
            Arrays.asList(
                    "hangfelvétel",
                    "elektronikus dok.",
                    "elektronikus kartográfiai dok.",
                    "nyomtatott kotta",
                    "kotta",
                    "kartográfiai dokumentum",
                    "elektronikus dokumentum",
                    "térkép"
            )
    );

    @BeforeEach
    public void init() {
        propertyUpdatingStrategy = new SpecialCoverTypeMapAndDigitalCheckPropertyUpdatingStrategy(coverTypeConverter);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Budapest\ntelepüléstérkép",
            "Koszovó\nautótérkép"
    })
    void updatePropertyMap(String property) {
        Book testBook = Book.builder().build();
        when(coverTypeConverter.keySet()).thenReturn(keySet);
        when(coverTypeConverter.get("térkép")).thenReturn(CoverType.MAP);

        propertyUpdatingStrategy.updateProperty(testBook, property);
        CoverType coverTypeResult = testBook.getCoverType();
        assertThat(coverTypeResult).isEqualTo(CoverType.MAP);

    }

    @Test
    void updatePropertyMap2() {
        Book testBook = Book.builder().build();
        String property = "Marcal\nkartográfiai dokumentum";
        when(coverTypeConverter.keySet()).thenReturn(keySet);
        when(coverTypeConverter.get("kartográfiai dokumentum")).thenReturn(CoverType.MAP);

        propertyUpdatingStrategy.updateProperty(testBook, property);
        CoverType coverTypeResult = testBook.getCoverType();
        assertThat(coverTypeResult).isEqualTo(CoverType.MAP);
    }

    @Test
    void updatePropertyDigital() {
        Book testBook = Book.builder().build();
        String property = "fantasy\nsorozat\nelektronikus dok.";

        when(coverTypeConverter.keySet()).thenReturn(keySet);
        when(coverTypeConverter.get("elektronikus dok.")).thenReturn(CoverType.DIGITAL);

        propertyUpdatingStrategy.updateProperty(testBook, property);
        CoverType coverTypeResult = testBook.getCoverType();
        assertThat(coverTypeResult).isEqualTo(CoverType.DIGITAL);
    }

    @Test
    void updatePropertyDigital2() {
        Book testBook = Book.builder().build();
        String property = "elektronikus kartográfiai dok.\nSzékesfehérvár";

        when(coverTypeConverter.keySet()).thenReturn(keySet);
        when(coverTypeConverter.get("elektronikus kartográfiai dok.")).thenReturn(CoverType.DIGITAL);

        propertyUpdatingStrategy.updateProperty(testBook, property);
        CoverType coverTypeResult = testBook.getCoverType();
        assertThat(coverTypeResult).isEqualTo(CoverType.DIGITAL);
    }

    @Test
    void updatePropertyDigital3() {
        Book testBook = Book.builder().build();
        String property = "Tüskevár\nelektronikus dokumentum";

        when(coverTypeConverter.keySet()).thenReturn(keySet);
        when(coverTypeConverter.get("elektronikus dokumentum")).thenReturn(CoverType.DIGITAL);

        propertyUpdatingStrategy.updateProperty(testBook, property);
        CoverType coverTypeResult = testBook.getCoverType();
        assertThat(coverTypeResult).isEqualTo(CoverType.DIGITAL);
    }

    @Test
    void updatePropertyHaveCoverType() {
        Book testBook = Book.builder().coverType(CoverType.HARDCORE).build();
        String property = "Tüskevár\nelektronikus dokumentum";

        propertyUpdatingStrategy.updateProperty(testBook, property);
        CoverType coverTypeResult = testBook.getCoverType();
        assertThat(coverTypeResult).isEqualTo(CoverType.HARDCORE);
    }

    @Test
    void updatePropertyDontSetAnything() {
        Book testBook = Book.builder().build();
        String property = "something";

        propertyUpdatingStrategy.updateProperty(testBook, property);
        CoverType coverTypeResult = testBook.getCoverType();
        assertThat(coverTypeResult).isNull();
    }
}
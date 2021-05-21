package com.reka.lakatos.booksofhungary.crawlers.implementation.szechenyilibrary.test.bookcreation.updatingtrategy;

import com.reka.lakatos.booksofhungary.crawlers.domain.database.Book;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation.creation.strategy.DefaultTitlePropertyUpdatingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TitlePropertyUpdatingStrategyTest {

    private PropertyUpdatingStrategy propertyUpdatingStrategy;

    @BeforeEach
    void init() {
        propertyUpdatingStrategy = new DefaultTitlePropertyUpdatingStrategy();
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
        String property = "55 kétszólamú énekgyakorlat [kötött] : felső szólam / Kodály Zoltán.";
        propertyUpdatingStrategy.updateProperty(book, property);

        String title = book.getTitle();
        assertThat(title).isEqualTo("55 kétszólamú énekgyakorlat : felső szólam");
    }

    @Test
    void updatePropertyTitleWithSubtitleWithOutSlash() {
        Book book = Book.builder().build();
        String property = "55 kétszólamú énekgyakorlat [kötött] : felső szólam Kodály Zoltán.";
        propertyUpdatingStrategy.updateProperty(book, property);

        String title = book.getTitle();
        assertThat(title).isNull();
    }
}

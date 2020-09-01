package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.strategy;

import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation.DefaultTitlePropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.CoverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TitlePropertyUpdatingStrategyTest {

    private PropertyUpdatingStrategy titlePropertyUpdatingStrategy;

    @BeforeEach
    void init() {
        titlePropertyUpdatingStrategy = new DefaultTitlePropertyUpdatingStrategy();
    }

    @Test
    void updateProperty() {
        Book testBook = new Book();
        String testProperty = "Oz, a nagy varázsló [Hangfelvétel] some plus text / " +
                "L. Frank Baum ; ford. Szőllősy Klára ; Mácsai Pál előadásában";

        titlePropertyUpdatingStrategy.updateProperty(testBook, testProperty);
        assertThat(testBook.getCoverType()).isEqualTo(CoverType.SOUND_RECORD);
        assertThat(testBook.getTitle()).isEqualTo("Oz, a nagy varázsló some plus text");
    }
}
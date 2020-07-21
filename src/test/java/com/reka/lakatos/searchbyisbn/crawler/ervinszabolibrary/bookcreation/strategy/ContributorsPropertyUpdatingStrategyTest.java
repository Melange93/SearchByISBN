package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.strategy;

import com.reka.lakatos.searchbyisbn.document.Book;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class ContributorsPropertyUpdatingStrategyTest {

    private PropertyUpdatingStrategy contributorsPropertyUpdatingStrategy;

    @BeforeEach
    void ini() {
        contributorsPropertyUpdatingStrategy = new ContributorsPropertyUpdatingStrategy();
    }

    @Test
    void updatePropertyOneContributors() {
        Book testBook = new Book();
        String testProperty = "Koszta Gabriella (1948-)$";
        contributorsPropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        assertThat(testBook.getContributors())
                .isEqualTo(Sets.newHashSet(
                        Collections.singletonList("Koszta Gabriella (1948-)")
                        )
                );
    }

    @Test
    void updatePropertyMoreContributors() {
        Book testBook = new Book();
        String testProperty =
                "Koszta Gabriella (1948-)$" +
                        "Sulányi Péter$" +
                        "Konrad-Adenauer-Stiftung. Budapesti Képviselet$";
        contributorsPropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        assertThat(testBook.getContributors())
                .isEqualTo(Sets.newHashSet(
                        Arrays.asList(
                                "Koszta Gabriella (1948-)",
                                "Sulányi Péter",
                                "Konrad-Adenauer-Stiftung. Budapesti Képviselet"
                                )
                        )
                );
    }
}
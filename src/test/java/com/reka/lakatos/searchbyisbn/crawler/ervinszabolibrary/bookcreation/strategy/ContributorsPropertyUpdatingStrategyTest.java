package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.strategy;

import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation.DefaultContributorsPropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.Edition;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ContributorsPropertyUpdatingStrategyTest {

    private PropertyUpdatingStrategy contributorsPropertyUpdatingStrategy;
    private static final int TEST_EDITION_INDEX = 0;

    @BeforeEach
    void ini() {
        contributorsPropertyUpdatingStrategy = new DefaultContributorsPropertyUpdatingStrategy();
    }

    @Test
    void updatePropertyOneContributors() {
        Book testBook = Book.builder().editions(Lists.newArrayList(new Edition())).build();
        String testProperty = "Koszta Gabriella (1948-)$";
        contributorsPropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        Set<String> contributors = testBook.getEditions().get(TEST_EDITION_INDEX).getContributors();

        assertThat(contributors)
                .isEqualTo(Sets.newHashSet(
                        Collections.singletonList("Koszta Gabriella (1948-)")
                        )
                );
    }

    @Test
    void updatePropertyMoreContributors() {
        Book testBook = Book.builder().editions(Lists.newArrayList(new Edition())).build();
        String testProperty =
                "Koszta Gabriella (1948-)$" +
                        "Sulányi Péter$" +
                        "Konrad-Adenauer-Stiftung. Budapesti Képviselet$";
        contributorsPropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        Set<String> contributors = testBook.getEditions().get(TEST_EDITION_INDEX).getContributors();

        assertThat(contributors)
                .isEqualTo(Sets.newHashSet(
                        Lists.newArrayList(
                                "Koszta Gabriella (1948-)",
                                "Sulányi Péter",
                                "Konrad-Adenauer-Stiftung. Budapesti Képviselet"
                                )
                        )
                );
    }
}
package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary.bookcreation.strategy;

import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.defaultbookcreation.creation.strategy.DefaultISBNPropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.CoverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ISBNPropertyUpdatingStrategyTest {

    private PropertyUpdatingStrategy isbnPropertyUpdatingStrategy;

    @BeforeEach
    void ini() {
        isbnPropertyUpdatingStrategy = new DefaultISBNPropertyUpdatingStrategy();
    }

    @Test
    void updatePropertyOneISBN13NoBasicCoverType() {
        Book testBook = new Book();
        String testProperty = "978-963-06-5122-6";
        isbnPropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        assertThat(testBook.getIsbn()).isEqualTo(testProperty);
        assertThat(testBook.getCoverType()).isEqualTo(null);
    }

    @Test
    void updatePropertyOneISBN13WithHardcoreCoverType() {
        Book testBook = new Book();
        String testProperty = "978-963-06-5122-6 (kötött)";
        isbnPropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        assertThat(testBook.getIsbn()).isEqualTo("978-963-06-5122-6");
        assertThat(testBook.getCoverType()).isEqualTo(CoverType.HARDCORE);
    }

    @Test
    void updatePropertyOneISBN13WithPaperbackCoverType() {
        Book testBook = new Book();
        String testProperty = "978-963-06-5122-6 (fűzött)";
        isbnPropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        assertThat(testBook.getIsbn()).isEqualTo("978-963-06-5122-6");
        assertThat(testBook.getCoverType()).isEqualTo(CoverType.PAPERBACK);
    }

    @Test
    void updatePropertyOneISBN13OneISBN10() {
        Book testBook = new Book();
        String testProperty = "978-963-06-5122-6 963-06-5122-X";
        isbnPropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        assertThat(testBook.getIsbn()).isEqualTo("978-963-06-5122-6");
    }

    @Test
    void updatePropertyOneISBN10NoBasicCoverTye() {
        Book testBook = new Book();
        String testProperty = "963-06-5122-X";
        isbnPropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        assertThat(testBook.getIsbn()).isEqualTo("963-06-5122-X");
    }

    @Test
    void updatePropertyOneISBN10WithHardcoreCoverType() {
        Book testBook = new Book();
        String testProperty = "963-06-5122-X (kötött)";
        isbnPropertyUpdatingStrategy.updateProperty(testBook, testProperty);

        assertThat(testBook.getIsbn()).isEqualTo("963-06-5122-X");
        assertThat(testBook.getCoverType()).isEqualTo(CoverType.HARDCORE);
    }

    @Test
    void updatePropertyHaveCoverType() {
        Book book = Book.builder().coverType(CoverType.SOUND_RECORD).build();
        String testProperty = "963-06-5122-X (kötött)";
        isbnPropertyUpdatingStrategy.updateProperty(book, testProperty);
        CoverType result = book.getCoverType();
        assertThat(result).isEqualTo(CoverType.SOUND_RECORD);
    }
}
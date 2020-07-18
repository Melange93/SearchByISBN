package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary;

class BookPropertiesValidatorTest {
    /*

    private BookPropertiesValidator bookPropertiesValidator;

    @BeforeEach
    void init() {
        bookPropertiesValidator = new BookPropertiesValidator(
                new BookISBNManager(
                        ISBNValidator.getInstance()));
    }

    @Test
    void isValidBookPropertiesTestNoteFieldContainAnotherBook() {
        String notesField = "[1.], Fejér megye, 1773-1808 / [szerk. Szaszkóné Sin Aranka] 1987 136. p. + 1 térk. (78x55 cm) 963-02-4654-6 (fűzött) : 105,- Ft\n" +
                "[2.], Pest-Pilis-Solt megye és a Kiskunság, 1773-1808 / [szerk. Szaszkóné Sin Aranka] 1988 280 p. + 1 térk. (78x55 cm) 963-7056-38-6 (fűzött) : 292,- Ft\n" +
                "[3.], Sopron megye, 1773-1808 / szerk. Borsodi Csaba 1990 194 p. + 1 térk. (78x55 cm) 963-7071-65-2 (fűzött) : 258,- Ft";
        String ISBNField = "978-963-02-4653-8";
        String seeAlso = null;

        assertThat(bookPropertiesValidator
                .isValidBookProperties(notesField, ISBNField, seeAlso))
                .isFalse();
    }

    @Test
    void isValidBookPropertiesTestHavePartDocumentary() {
        assertThat(bookPropertiesValidator
                .isValidBookProperties(null, "978-963-02-4653-8", "Részdokumentum (1)"))
                .isFalse();
    }

    @Test
    void isValidBookPropertiesTestHaveFieldButNotHavePartDocumentary() {
        assertThat(bookPropertiesValidator
                .isValidBookProperties(null, "978-963-02-4653-8", "Keresés..."))
                .isTrue();
    }

    @Test
    void isValidBookPropertiesTestISBNisNull() {
        assertThat(bookPropertiesValidator
                .isValidBookProperties(null, null, null))
                .isFalse();
    }

    @Test
    void isValidBookPropertiesTestISBNFieldContainsISBNMoreThanOneBookISBNOneISBN13() {
        String isbnField = "978-963-05-1810-9 (kötött)";
        assertThat(bookPropertiesValidator
                .isValidBookProperties(null, isbnField, null))
                .isTrue();
    }

    @Test
    void isValidBookPropertiesTestISBNFieldContainsISBNMoreThanOneBookISBNTwoISBN13() {
        String isbnField = "978-963-05-1810-9 978-963-05-6467-0";
        assertThat(bookPropertiesValidator
                .isValidBookProperties(null, isbnField, null))
                .isFalse();
    }

    @Test
    void isValidBookPropertiesTestISBNFieldContainsISBNMoreThanOneBookISBNThreeISBN13() {
        String isbnField = "978-963-05-1810-9 978-963-05-6467-0 978-963-05-6467-0";
        assertThat(bookPropertiesValidator
                .isValidBookProperties(null, isbnField, null))
                .isFalse();
    }

    @Test
    void isValidBookPropertiesTestISBNFieldContainsISBNMoreThanOneBookISBNOneISBN10() {
        String isbnFiled = "963-05-6467-X";
        assertThat(bookPropertiesValidator
                .isValidBookProperties(null, isbnFiled, null))
                .isTrue();
    }

    @Test
    void isValidBookPropertiesTestISBNFieldContainsISBNMoreThanOneBookISBNTwoISBN10() {
        String isbnField = "963-05-6467-X 963-05-6468-X";
        assertThat(bookPropertiesValidator
                .isValidBookProperties(null, isbnField, null))
                .isFalse();
    }

    @Test
    void isValidBookPropertiesTestISBNFieldContainsISBNMoreThanOneBookISBNThreeISBN10() {
        String isbnField = "963-05-6467-X 963-05-6468-X 963-05-6469-X";
        assertThat(bookPropertiesValidator
                .isValidBookProperties(null, isbnField, null))
                .isFalse();
    }

    @Test
    void isValidBookPropertiesTestISBNFieldContainsISBNMoreThanOneBookISBNOneISBN13AndOneISBN10SameBook() {
        String isbnField = "963-05-6467-X 978-963-05-6467-0";
        assertThat(bookPropertiesValidator
                .isValidBookProperties(null, isbnField, null))
                .isTrue();
    }

    @Test
    void isValidBookPropertiesTestISBNFieldContainsISBNMoreThanOneBookISBNOneISBN13AndOneISBN10SameBook2() {
        String isbnField = "978-963-05-6467-0 963-05-6467-X";
        assertThat(bookPropertiesValidator
                .isValidBookProperties(null, isbnField, null))
                .isTrue();
    }

    @Test
    void isValidBookPropertiesTestISBNFieldContainsISBNMoreThanOneBookISBNOneISBN13AndOneISBN10DifferentBook() {
        String isbnField = "963-05-4922-0 978-963-05-6467-0";
        assertThat(bookPropertiesValidator
                .isValidBookProperties(null, isbnField, null))
                .isFalse();
    }

    @Test
    void isValidBookPropertiesTestISBNFieldContainsISBNMoreThanOneBookISBNOneISBN13AndOneISBN10DifferentBook2() {
        String isbnField = "978-963-05-6467-0 963-05-4922-0";
        assertThat(bookPropertiesValidator
                .isValidBookProperties(null, isbnField, null))
                .isFalse();
    }

     */

}
package com.reka.lakatos.booksofhungary.crawlers.implementation.bookcrationlogic.defaultbookcreation;

import lombok.extern.slf4j.Slf4j;
import com.reka.lakatos.booksofhungary.crawlers.webdocument.domain.WebElement;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class DefaultBookListPreparatory {

    private static final String SPECIAL_SEPARATION_CHARACTER = "$";
    private final String specialCaseContributors;

    public DefaultBookListPreparatory(final String specialCaseContributors) {
        this.specialCaseContributors = specialCaseContributors;
    }

    public List<String> prepareBookProperties(final List<WebElement> bookPropertiesValuesWebElement) {
        return bookPropertiesValuesWebElement
                .stream()
                .map(WebElement::text)
                .collect(Collectors.toList());
    }

    public void setContributors(
            final List<String> bookPropertiesName,
            final List<WebElement> bookPropertiesValuesWebElement,
            final List<String> bookPropertiesValues,
            final String selectCssQuery
    ) {
        final int contributorsIndex = bookPropertiesName.indexOf(specialCaseContributors);
        final String contributorsSeparateBySpecialCharacter =
                getContributorsSeparateBySpecialCharacter(bookPropertiesValuesWebElement, contributorsIndex, selectCssQuery);
        bookPropertiesValues.set(contributorsIndex, contributorsSeparateBySpecialCharacter);
    }

    public Map<String, String> createPropertiesMap(
            final List<String> bookPropertiesName,
            final List<String> bookPropertiesValues
    ) {
        return IntStream.range(0, bookPropertiesValues.size())
                .boxed()
                .collect(
                        Collectors.toMap(
                                bookPropertiesName::get,
                                bookPropertiesValues::get
                        )
                );
    }

    private String getContributorsSeparateBySpecialCharacter(
            final List<WebElement> bookPropertiesValuesWebElement,
            final int contributorsIndex,
            final String selectCssQuery
    ) {
        return bookPropertiesValuesWebElement.get(contributorsIndex)
                .select(selectCssQuery)
                .stream()
                .map(WebElement::text)
                .collect(Collectors.joining(SPECIAL_SEPARATION_CHARACTER));
    }

    public boolean hasContributors(final List<String> bookPropertiesKey) {
        return bookPropertiesKey.contains(specialCaseContributors);
    }
}

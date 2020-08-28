package com.reka.lakatos.searchbyisbn.crawler.bookcreation;

import com.reka.lakatos.searchbyisbn.webdocument.WebElement;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class DefaultBookListPreparatory {

    private static final String SPECIAL_SEPARATION_CHARACTER = "$";
    private String specialCaseContributors;

    public DefaultBookListPreparatory(String specialCaseContributors) {
        this.specialCaseContributors = specialCaseContributors;
    }

    public List<String> prepareBookProperties(List<WebElement> bookPropertiesValuesWebElement) {
        return bookPropertiesValuesWebElement
                .stream()
                .map(WebElement::text)
                .collect(Collectors.toList());
    }

    public void setContributors(
            List<String> bookPropertiesName,
            List<WebElement> bookPropertiesValuesWebElement,
            List<String> bookPropertiesValues,
            String selectCssQuery
    ) {
        int contributorsIndex = bookPropertiesName.indexOf(specialCaseContributors);
        String contributorsSeparateBySpecialCharacter =
                getContributorsSeparateBySpecialCharacter(bookPropertiesValuesWebElement, contributorsIndex, selectCssQuery);
        bookPropertiesValues.set(contributorsIndex, contributorsSeparateBySpecialCharacter);
    }

    public Map<String, String> createPropertiesMap(
            List<String> bookPropertiesName,
            List<String> bookPropertiesValues
    ) {
        try {
            return IntStream.range(0, bookPropertiesValues.size())
                    .boxed()
                    .collect(
                            Collectors.toMap(
                                    bookPropertiesName::get,
                                    bookPropertiesValues::get
                            )
                    );
        } catch (IllegalStateException e) {
            log.error(String.valueOf(e));
            return new HashMap<>();
        }
    }

    private String getContributorsSeparateBySpecialCharacter(
            List<WebElement> bookPropertiesValuesWebElement,
            int contributorsIndex,
            String selectCssQuery
    ) {
        return bookPropertiesValuesWebElement.get(contributorsIndex)
                .select(selectCssQuery)
                .stream()
                .map(WebElement::text)
                .collect(Collectors.joining(SPECIAL_SEPARATION_CHARACTER));
    }

    public boolean hasContributors(List<String> bookPropertiesKey) {
        return bookPropertiesKey.contains(specialCaseContributors);
    }
}

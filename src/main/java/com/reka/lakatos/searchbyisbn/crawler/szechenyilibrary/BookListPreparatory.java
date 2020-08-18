package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary;

import com.reka.lakatos.searchbyisbn.webdocument.WebElement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class BookListPreparatory {

    private static final String SPECIAL_CASE_CONTRIBUTORS = "Név/nevek:";
    private static final String SPECIAL_SEPARATION_CHARACTER = "$";

    List<String> prepareBookProperties(List<WebElement> bookPropertiesValuesWebElement) {
        return bookPropertiesValuesWebElement
                .stream()
                .map(WebElement::text)
                .collect(Collectors.toList());
    }

    void setContributors(
            List<String> bookPropertiesName,
            List<WebElement> bookPropertiesValuesWebElement,
            List<String> bookPropertiesValues
    ) {
        int contributorsIndex = bookPropertiesName.indexOf(SPECIAL_CASE_CONTRIBUTORS);
        String contributorsSeparateBySpecialCharacter =
                getContributorsSeparateBySpecialCharacter(bookPropertiesValuesWebElement, contributorsIndex);
        bookPropertiesValues.set(contributorsIndex, contributorsSeparateBySpecialCharacter);
    }

    Map<String, String> createPropertiesMap(
            List<String> bookPropertiesName,
            List<String> bookPropertiesValues
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
            List<WebElement> bookPropertiesValuesWebElement,
            int contributorsIndex
    ) {
        return bookPropertiesValuesWebElement.get(contributorsIndex)
                .select("a")
                .stream()
                .map(WebElement::text)
                .collect(Collectors.joining(SPECIAL_SEPARATION_CHARACTER));
    }

    boolean hasContributors(List<String> bookPropertiesKey) {
        return bookPropertiesKey.contains(SPECIAL_CASE_CONTRIBUTORS);
    }
}

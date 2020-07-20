package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary;

import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import com.reka.lakatos.searchbyisbn.webdocument.WebElement;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PageReader {

    private static final String SPECIAL_CASE_CONTRIBUTORS = "Egyéb nevek:";
    private static final String SPECIAL_SEPARATION_CHARACTER = "$";

    public Map<String, String> getBookDetailsLinkInformation(WebDocument bookListPage) {
        Map<String, String> booksInformation = new HashMap<>();

        List<WebElement> informationLines = bookListPage.select(".short_item_back script");
        for (WebElement line : informationLines) {
            Pattern lineNumberAndBookId = Pattern.compile("(\\d+,\\d+)");
            Matcher matcher = lineNumberAndBookId.matcher(line.toString());
            if (matcher.find()) {
                String[] information = matcher.group().split(",");
                int lineNumber = 0;
                int bookId = 1;
                booksInformation.put(information[lineNumber], information[bookId]);
            }
        }
        return booksInformation;
    }

    public Map<String, String> getBookProperties(WebDocument bookDetailsPage) {
        List<String> bookPropertiesKey = getBookPropertiesKey(bookDetailsPage);

        List<WebElement> bookPropertiesValuesWebElement = bookDetailsPage.select(".long_value");
        List<String> bookPropertiesValues = getBookPropertiesValues(bookPropertiesValuesWebElement);

        if (hasContributors(bookPropertiesKey)) {
            int contributorsIndex = bookPropertiesKey.indexOf(SPECIAL_CASE_CONTRIBUTORS);
            String contributorsSeparateBySpecialCharacter =
                    getContributorsSeparateBySpecialCharacter(bookPropertiesValuesWebElement, contributorsIndex);
            bookPropertiesValues.set(contributorsIndex, contributorsSeparateBySpecialCharacter);
        }

        return createBookPropertiesMap(bookPropertiesKey, bookPropertiesValues);
    }

    private Map<String, String> createBookPropertiesMap(List<String> bookPropertiesKey, List<String> bookPropertiesValues) {

        Map<String, String> bookProperties = new HashMap<>();
        for (int i = 0; i < bookPropertiesKey.size(); i++) {
            bookProperties.put(bookPropertiesKey.get(i), bookPropertiesValues.get(i));
        }
        return bookProperties;
    }

    private String getContributorsSeparateBySpecialCharacter(List<WebElement> bookPropertiesValuesWebElement, int contributorsIndex) {
        return bookPropertiesValuesWebElement.get(contributorsIndex)
                .select("a")
                .stream()
                .map(WebElement::text)
                .collect(Collectors.joining(SPECIAL_SEPARATION_CHARACTER));
    }

    private boolean hasContributors(List<String> bookPropertiesKey) {
        return bookPropertiesKey.contains(SPECIAL_CASE_CONTRIBUTORS);
    }

    private List<String> getBookPropertiesValues(List<WebElement> bookPropertiesValuesWebElement) {
        return bookPropertiesValuesWebElement
                .stream()
                .map(WebElement::text)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private List<String> getBookPropertiesKey(WebDocument bookDetailsPage) {
        return bookDetailsPage
                .select(".long_key")
                .stream()
                .filter(webElement -> webElement.select("table").isEmpty())
                .map(WebElement::text)
                .map(String::trim)
                .collect(Collectors.toList());
    }
}

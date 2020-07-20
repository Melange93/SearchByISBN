package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary;

import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import com.reka.lakatos.searchbyisbn.webdocument.WebElement;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PageReader {

    private static final String SPECIAL_CASE_OTHER_NAMES = "Egyéb nevek:";
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

    public Map<String, String> getBookInformation(WebDocument bookDetailsPage) {
        List<WebElement> bookProperties = bookDetailsPage.select(".long_key");
        List<WebElement> bookPropertiesValues = bookDetailsPage.select(".long_value");

        Map<String, String> bookInformation = new HashMap<>();
        for (int i = 0; i < bookProperties.size() - 1; i++) {
            if (bookProperties.get(i).text().equals(SPECIAL_CASE_OTHER_NAMES) && !bookPropertiesValues.get(i).select("a").isEmpty()) {
                List<WebElement> names = bookPropertiesValues.get(i).select("a");
                String contributorsName = getContributorsStringWithSpecialSeparationCharacter(names);
                bookInformation.put(bookProperties.get(i).text().trim(), contributorsName);
                break;
            }
            bookInformation.put(bookProperties.get(i).text().trim(), bookPropertiesValues.get(i).text().trim());
        }

        return bookInformation;
    }

    private String getContributorsStringWithSpecialSeparationCharacter(List<WebElement> names) {
        return names.stream()
                .map(WebElement::text)
                .reduce((s, s2) -> s + SPECIAL_SEPARATION_CHARACTER + s2)
                .get();
    }
}

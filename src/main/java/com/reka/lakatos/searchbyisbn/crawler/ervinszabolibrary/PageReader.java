package com.reka.lakatos.searchbyisbn.crawler.ervinszabolibrary;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PageReader {

    private static final String SPECIAL_CASE_OTHER_NAMES = "Egyéb nevek:";
    private static final String SPECIAL_SEPARATION_CHARACTER = "$";


    public Map<String, String> getBookDetailsLinkInformation(Document bookListPage) {
        Map<String, String> booksInformation = new HashMap<>();

        Elements informationLines = bookListPage.select(".short_item_back script");
        for (Element line : informationLines) {
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

    public Map<String, String> getBookInformation(Document bookDetailsPage) {
        Elements bookProperties = bookDetailsPage.select(".long_key");
        Elements bookPropertiesValues = bookDetailsPage.select(".long_value");

        Map<String, String> bookInformation = new HashMap<>();
        for (int i = 0; i < bookProperties.size() - 1; i++) {
            if (bookProperties.get(i).text().equals(SPECIAL_CASE_OTHER_NAMES) && !bookPropertiesValues.get(i).select("a").isEmpty()) {
                Elements names = bookPropertiesValues.get(i).select("a");
                String contributorsName = getContributorsStringWithSpecialSeparationCharacter(names);
                bookInformation.put(bookProperties.get(i).text().trim(), contributorsName);
                break;
            }
            bookInformation.put(bookProperties.get(i).text().trim(), bookPropertiesValues.get(i).text().trim());
        }

        return bookInformation;
    }

    private String getContributorsStringWithSpecialSeparationCharacter(Elements names) {
        return names.stream()
                .map(Element::text)
                .reduce((s, s2) -> s + SPECIAL_SEPARATION_CHARACTER + s2)
                .get();
    }
}

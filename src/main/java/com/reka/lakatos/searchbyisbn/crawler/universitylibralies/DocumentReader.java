package com.reka.lakatos.searchbyisbn.crawler.universitylibralies;

import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import com.reka.lakatos.searchbyisbn.webdocument.WebElement;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "universities")
public class DocumentReader {

    public Optional<String> getPAuthorCode(WebDocument webDocument) {
        Optional<WebElement> urlSpan = webDocument.select("span").stream()
                .filter(webElement -> webElement.hasAttr("style"))
                .filter(webElement -> webElement.attr("style").equals("display:none;"))
                .findFirst();

        if (urlSpan.isPresent()) {
            return findPAuthCode(urlSpan.get().text());
        }
        return Optional.empty();
    }

    private Optional<String> findPAuthCode(String text) {
        Matcher matcher = Pattern.compile("(?<=[\\?]p_auth\\=)(.{8})(?=[\\&])").matcher(text);
        if (matcher.find()) {
            return Optional.of(matcher.group());
        }
        return Optional.empty();
    }

    public List<String> getSearchingResultDetailLinks(WebDocument document) {
        return document.select("a").stream()
                .filter(webElement -> webElement.text().equals("Részletek"))
                .map(webElement -> webElement.attr("href"))
                .collect(Collectors.toList());
    }

    public Map<String, String> getBookProperties(WebDocument webDocument) {
        List<String> bookPropertiesName = getBookPropertiesName(webDocument);
        List<String> bookPropertiesValue = getBookPropertiesValue(webDocument);
        return createPropertiesMap(bookPropertiesName, bookPropertiesValue);
    }

    private List<String> getBookPropertiesName(WebDocument webDocument) {
        return webDocument.select(".metadata-name").stream()
                .map(WebElement::text)
                .collect(Collectors.toList());
    }

    private List<String> getBookPropertiesValue(WebDocument webDocument) {
        return webDocument.select(".metadata-value").stream()
                .map(WebElement::text)
                .collect(Collectors.toList());
    }

    private Map<String, String> createPropertiesMap(
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
}

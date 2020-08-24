package com.reka.lakatos.searchbyisbn.crawler.universitylibralies;

import com.reka.lakatos.searchbyisbn.webdocument.WebDocument;
import com.reka.lakatos.searchbyisbn.webdocument.WebElement;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "universities")
public class DocumentReader {

    public Optional<String> getPAuthorCode(WebDocument webDocument) {
         Optional< WebElement > urlSpan = webDocument.select("span").stream()
                .filter(webElement -> webElement.hasAttr("style"))
                .filter(webElement -> webElement.attr("style").equals("display:none;"))
                .findFirst();
        String text = urlSpan.get().text();
        Matcher matcher = Pattern.compile("(?<=[\\?]p_auth\\=)(.{8})(?=[\\&])").matcher(text);

        if (matcher.find()) {
            return Optional.of(matcher.group());
        }

        return Optional.empty();
    }

}

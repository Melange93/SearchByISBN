package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation.updatingtrategy;

import com.reka.lakatos.searchbyisbn.crawler.bookcreation.PropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.document.Book;
import com.reka.lakatos.searchbyisbn.document.CoverType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class TitlePropertyUpdatingStrategy implements PropertyUpdatingStrategy {

    private final Map<String, CoverType> coverTypeConverter;

    @Override
    public void updateProperty(Book book, String property) {
        setTitle(book, property.trim());
        setSpecialCoverType(property, book);
    }

    private void setTitle(Book book, String property) {
        Matcher matcher = Pattern.compile("(.*?)(?=[\\/])").matcher(property);
        if (matcher.find()) {
            String title = matcher.group()
                    .replaceAll("\\[(.*?)\\]", "")
                    .replaceAll("\\s+", " ")
                    .trim();
            book.setTitle(title);
        }
    }

    private void setSpecialCoverType(String value, Book book) {
        if (book.getCoverType() != null) {
            return;
        }

        Pattern specialCoverTypePattern = Pattern.compile("(?![\\[])[^\\[]*?(?=[\\]])");
        Matcher matcher = specialCoverTypePattern.matcher(value);
        if (matcher.find()) {
            String result = matcher.group();
            if (!result.isBlank()) {
                CoverType coverType = coverTypeConverter.get(result.trim());
                book.setCoverType(coverType);
            }
        }
    }
}

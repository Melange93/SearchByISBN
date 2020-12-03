package com.reka.lakatos.booksofhungary.crawlers.crawlers.szechenyilibrary.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Data
@ToString
@AllArgsConstructor
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class Session {
    private String serverUrl;
    private String id;
}

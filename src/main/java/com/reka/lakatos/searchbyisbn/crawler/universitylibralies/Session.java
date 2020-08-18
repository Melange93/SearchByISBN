package com.reka.lakatos.searchbyisbn.crawler.universitylibralies;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Data
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "universities")
public class Session {
    String jsessionId;
    String guestLanguage;
    boolean cookieSupport;

    public Session() {
        this.guestLanguage = "hu_Hu";
        this.cookieSupport = true;
    }
}

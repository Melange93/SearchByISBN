package com.reka.lakatos.booksofhungary.crawlers.crawlers.szechenyilibrary;

import com.reka.lakatos.booksofhungary.crawlers.crawlers.defaultbookcreation.DefaultBookListPreparatory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "szechenyi")
public class BasicConfiguration {

    @Bean
    public DefaultBookListPreparatory getBookListPreparatory() {
        return new DefaultBookListPreparatory("Név/nevek:");
    }

}

package com.reka.lakatos.searchbyisbn.crawler.universitylibraries;

import com.reka.lakatos.searchbyisbn.crawler.bookcreation.DefaultBookListPreparatory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "universities")
public class BasicConfiguration {

    @Bean
    public DefaultBookListPreparatory getDefaultBookListPreparatory() {
        return new DefaultBookListPreparatory("További személynév:");
    }
}

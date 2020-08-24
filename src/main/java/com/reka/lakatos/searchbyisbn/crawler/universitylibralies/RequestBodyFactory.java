package com.reka.lakatos.searchbyisbn.crawler.universitylibralies;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "crawler.book-crawler", havingValue = "universities")
public class RequestBodyFactory {

    public String getComplexSearchMainReqBody() {
        return "type=advanced&reset=false&searchId=";
    }

    public String getSearchingBody(long isbn) {
        return
    }
}

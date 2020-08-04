package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation;

import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation.updatingtrategy.ISBNPropertyUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation.updatingtrategy.PropertiesUpdatingStrategy;
import com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.bookcreation.updatingtrategy.TitlePropertiesUpdatingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class SzechenyiStrategyConfiguration {

    @Bean
    public Map<String, PropertiesUpdatingStrategy> getSzechenyiBookPropertyUpdatingStrategyMap() {
        return Map.ofEntries(
                Map.entry("ISBN :", new ISBNPropertyUpdatingStrategy()),
                //Map.entry("Terj./Fiz. jell.:", ),
                Map.entry("Cím és szerzőségi közlés:", new TitlePropertiesUpdatingStrategy())
                //Map.entry("Név/nevek:", ),
                //Map.entry("Szerző:", )
                //Map.entry("Kiadás:", )
        );
    }

}

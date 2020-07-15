package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    private String serverUrl;
    private String id;
}

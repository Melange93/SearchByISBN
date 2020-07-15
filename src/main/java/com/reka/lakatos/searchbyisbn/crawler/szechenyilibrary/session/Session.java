package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class Session {
    private String serverUrl;
    private String id;
}

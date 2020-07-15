package com.reka.lakatos.searchbyisbn.crawler.szechenyilibrary.sessionid.model;

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

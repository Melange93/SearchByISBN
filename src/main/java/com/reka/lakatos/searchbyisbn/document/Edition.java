package com.reka.lakatos.searchbyisbn.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Data
@ToString
@Slf4j
@Builder
@AllArgsConstructor
public class Edition {
    private int editionNumber;
    private int yearOfRelease;
    private Set<String> contributors;
    private float thickness;
    private int pageNumber;

    public Edition() {
        this.contributors = new HashSet<>();
    }
}

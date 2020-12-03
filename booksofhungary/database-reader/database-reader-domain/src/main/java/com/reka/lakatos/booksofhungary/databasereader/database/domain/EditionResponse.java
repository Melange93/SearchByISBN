package com.reka.lakatos.booksofhungary.databasereader.database.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class EditionResponse {
    private final int editionNumber;
    private final int yearOfRelease;
    private final Set<String> contributors;
    private final float thickness;
    private final int pageNumber;
}

package com.reka.lakatos.booksofhungary.databasereader.database.domain;

import java.util.Optional;

public enum CoverTypeResponse {

    HARDCORE("kötött"),
    PAPERBACK("fűzött"),
    DIGITAL("elektronikus dokumentum"),
    SOUND_RECORD("hangfelvétel"),
    MAP("kartográfiai dokumentum"),
    DVD("dvd-felvétel");

    private final String hunName;

    CoverTypeResponse(String hunName) {
        this.hunName = hunName;
    }

    public String getHunName() {
        return hunName;
    }

    public static Optional<CoverTypeResponse> findTypeByName(String hunName) {
        for (CoverTypeResponse coverTypeResponse : values()) {
            if (coverTypeResponse.getHunName().equals(hunName)) {
                return Optional.of(coverTypeResponse);
            }
        }

        return Optional.empty();
    }
}

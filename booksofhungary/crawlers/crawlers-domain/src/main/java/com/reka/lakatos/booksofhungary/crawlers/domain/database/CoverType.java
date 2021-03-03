package com.reka.lakatos.booksofhungary.crawlers.domain.database;

import java.util.Optional;

public enum CoverType {

    HARDCORE("kötött"),
    PAPERBACK("fűzött"),
    DIGITAL("elektronikus dokumentum"),
    SOUND_RECORD("hangfelvétel"),
    MAP("kartográfiai dokumentum"),
    DVD("dvd-felvétel");

    private final String hunName;

    CoverType(String hunName) {
        this.hunName = hunName;
    }

    public String getHunName() {
        return hunName;
    }

    public static Optional<CoverType> findTypeByName(String hunName) {
        for (CoverType coverType : values()) {
            if (coverType.getHunName().equals(hunName)) {
                return Optional.of(coverType);
            }
        }

        return Optional.empty();
    }
}

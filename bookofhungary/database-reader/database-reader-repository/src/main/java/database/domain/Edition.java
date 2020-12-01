package database.domain;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
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

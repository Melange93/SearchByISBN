package domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookResponse {
    private final String isbn;
    private final String author;
    private final String title;
    private final String publisher;
    private final List<EditionResponse> editions;
    private final CoverTypeResponse coverTypeResponse;
}

package ro.ilearn.library;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookDto {
    private Long id;
    private String title;
}

package ro.ilearn.library;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookReviewDto {
    private Long id;
    private String review;
    private String writtenBy;
}

package ro.ilearn.library;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class BookDto {
    private Long id;
    private String title;
    private Integer edition;
    private List<BookReviewDto> bookReviews;

    public void addBookReview(BookReviewDto bookReviewDto) {
        if (this.bookReviews == null) {
            this.bookReviews = new ArrayList<>();
        }
        this.bookReviews.add(bookReviewDto);
    }
}

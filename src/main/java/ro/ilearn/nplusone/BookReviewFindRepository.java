package ro.ilearn.nplusone;

import ro.ilearn.library.BookReview;

import java.util.List;

public interface BookReviewFindRepository {

    List<BookReview> findAllFetchTypeEager();

    List<BookReview> findAllFetchTypeLazy();
}
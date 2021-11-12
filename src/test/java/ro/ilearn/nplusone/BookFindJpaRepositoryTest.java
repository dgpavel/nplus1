package ro.ilearn.nplusone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ro.ilearn.library.BookReview;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
class BookFindJpaRepositoryTest {


    private EntityManager entityManager;
    private BookReviewFindRepository bookReviewFindRepository;

    @Autowired
    public BookFindJpaRepositoryTest(EntityManager entityManager) {
        this.entityManager = entityManager;
        bookReviewFindRepository = new BookReviewFindJpaRepository(entityManager);
    }

    @Test
    void findAllFetchTypeEager_then_ReturnAllItems() {
        List<BookReview> bookReviews = bookReviewFindRepository.findAllFetchTypeEager();
        assertEquals(8, bookReviews.size());
    }

    @Test
    void findAllFetchTypeLazy_then_ReturnAllItems() {
        List<BookReview> books = bookReviewFindRepository.findAllFetchTypeLazy();
        assertEquals(8, books.size());
    }

}

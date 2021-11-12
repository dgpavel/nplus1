package ro.ilearn.nplusone;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ro.ilearn.library.BookReview;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
class BookFindJpaRepositoryTest {
    private final Logger logger = LoggerFactory.getLogger(BookFindJpaRepositoryTest.class);

    private EntityManager entityManager;
    private BookReviewFindRepository bookReviewFindRepository;

    @Autowired
    public BookFindJpaRepositoryTest(EntityManager entityManager) {
        this.entityManager = entityManager;
        bookReviewFindRepository = new BookReviewFindJpaRepository(entityManager);
    }

    @Test
    void findAll_then_ReturnAllItems() {
        List<BookReview> bookReviews = bookReviewFindRepository.findAll();
        if (logger.isTraceEnabled()) {
            logger.trace(bookReviews.toString());
        }
        assertEquals(8, bookReviews.size());
    }

    @Test
    void findAllJoinFetch_then_ReturnAllItems() {
        List<BookReview> bookReviews = bookReviewFindRepository.findAllJoinFetch();
        if (logger.isTraceEnabled()) {
            logger.trace(bookReviews.toString());
        }
        assertEquals(8, bookReviews.size());
    }

}

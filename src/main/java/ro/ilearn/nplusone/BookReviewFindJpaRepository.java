package ro.ilearn.nplusone;

import org.springframework.stereotype.Repository;
import ro.ilearn.library.BookReview;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class BookReviewFindJpaRepository implements BookReviewFindRepository {
    private final EntityManager em;

    public BookReviewFindJpaRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<BookReview> findAllFetchTypeEager() {
        TypedQuery<BookReview> typedQuery = em.createQuery("SELECT br FROM BookReview br", BookReview.class);
        return typedQuery.getResultList();
    }

    @Override
    public List<BookReview> findAllFetchTypeLazy() {
        TypedQuery<BookReview> typedQuery = em.createQuery("SELECT br FROM BookReview br join fetch br.book b", BookReview.class);
        return typedQuery.getResultList();
    }
}

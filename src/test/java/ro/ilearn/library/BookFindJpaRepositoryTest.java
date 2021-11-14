package ro.ilearn.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
class BookFindJpaRepositoryTest {

    private final EntityManager em;
    private final BookFindRepository bookFindRepository;

    @Autowired
    public BookFindJpaRepositoryTest(EntityManager em) {
        this.em = em;
        bookFindRepository = new BookFindJpaRepository(em);
    }

    @Test
    void hql_then_Return() {
        TypedQuery<Book> typedQuery = em.createQuery("SELECT b FROM Book b", Book.class);
        List<Book> books = typedQuery.getResultList();
        // then
        assertEquals(4, books.size());
    }

    @Test
    void hql_fetch_then_Return() {
        TypedQuery<Book> typedQuery = em.createQuery("SELECT DISTINCT b FROM Book b join fetch b.reviews br", Book.class);
        List<Book> books = typedQuery.getResultList();
        // then
        assertEquals(3, books.size());
    }

    @Test
    void hql_left_then_Return() {
        TypedQuery<Book> typedQuery = em.createQuery("SELECT DISTINCT b FROM Book b left join fetch b.reviews br", Book.class);
        List<Book> books = typedQuery.getResultList();
        // then
        assertEquals(4, books.size());
    }


    @Test
    void hql_paginat_then_Return() {
        // given
        Pageable pageable = PageRequest.of(1, 2);
        // when
        TypedQuery<Book> typedQuery = em.createQuery("SELECT DISTINCT  b FROM Book b left join fetch b.reviews br", Book.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize());
        List<Book> books = typedQuery.getResultList();
        // then
        assertEquals(2, books.size());
    }

    @Test
    void search_then_Return() {
        // given
        BookSearchDto searchDto = new BookSearchDto();
        // when
        List<BookDto> books = bookFindRepository.search(searchDto, 0, 0);
        // then
        assertEquals(4, books.size());
    }

    @Test
    void search_paginat_then_Return() {
        // given
        BookSearchDto searchDto = new BookSearchDto();
        Pageable pageable = PageRequest.of(1, 2);
        // when
        List<BookDto> books = bookFindRepository.search(searchDto, 0, 0, pageable);
        // then
        assertEquals(2, books.size());
    }


}

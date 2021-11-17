package ro.ilearn.library;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

@Repository
public class BookFindJpaRepository implements BookFindRepository {
    public static final String ID_BOOK = "idBook";
    private final EntityManager em;

    public BookFindJpaRepository(EntityManager em) {
        this.em = em;
    }

    /**
     * N + 1 nepaginat un singur select
     *
     * @param searchDto     forma de cautare
     * @param sortColumn    0 = title
     * @param sortDirection 0 = asc, 1 = desc
     * @return toata lista de carti cu recenzii
     */
    @Override
    public List<BookDto> search(BookSearchDto searchDto, int sortColumn, int sortDirection) {
        //
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);
        // From
        Root<Book> bookRoot = cq.from(Book.class);
        Join<Book, BookReview> bookReviewJoin = bookRoot.join(Book_.reviews, JoinType.LEFT);
        // Select
        cq.multiselect(
                bookReviewJoin.get(BookReview_.id).alias(BookReview_.ID),
                bookReviewJoin.get(BookReview_.review).alias(BookReview_.REVIEW),
                bookReviewJoin.get(BookReview_.writtenBy).alias(BookReview_.WRITTEN_BY),
                bookRoot.get(Book_.id).alias(ID_BOOK),
                bookRoot.get(Book_.title).alias(Book_.TITLE)
        );
        // WHERE
        cq.where(buildWhere(searchDto, cb, bookRoot, bookReviewJoin));
        // Order By ID Parinte
        cq.orderBy(cb.asc(bookRoot.get(Book_.id)));
        // Execute query
        List<Tuple> tupleLst = em.createQuery(cq).getResultList();
        //
        List<BookDto> list = fromTupleToDto(tupleLst);
        // Order BY
        orderBy(list, sortColumn, sortDirection);
        return list;
    }


    /**
     * N + 1 paginat doua selecturi
     *
     * @param searchDto     forma de cautare
     * @param sortColumn    0 = title
     * @param sortDirection 0 = asc, 1 = desc
     * @param pageable      informati ipaginare
     * @return lista, conform paginarii, de carti cu recenzii
     */
    @Override
    public List<BookDto> search(BookSearchDto searchDto, int sortColumn, int sortDirection, Pageable pageable) {
        // Primul select IDs de parinti
        List<Long> idsParent = firstSelect(searchDto, sortColumn, sortDirection, pageable);
        // Al doilea select
        return secondSelect(idsParent, sortColumn, sortDirection);
    }

    private List<BookDto> secondSelect(List<Long> idsParent, int sortColumn, int sortDirection) {
        // lista parinti de returnat
        List<BookDto> dtoLst = new ArrayList<>();
        if (!idsParent.isEmpty()) {
            //
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);
            // From
            Root<Book> bookRoot = cq.from(Book.class);
            Join<Book, BookReview> bookReviewJoin = bookRoot.join(Book_.reviews, JoinType.LEFT);
            // Select
            cq.multiselect(
                    bookReviewJoin.get(BookReview_.id).alias(BookReview_.ID),
                    bookReviewJoin.get(BookReview_.review).alias(BookReview_.REVIEW),
                    bookReviewJoin.get(BookReview_.writtenBy).alias(BookReview_.WRITTEN_BY),
                    bookRoot.get(Book_.id).alias(ID_BOOK),
                    bookRoot.get(Book_.title).alias(Book_.TITLE)
            );
            // WHERE ids parinti din primul select
            cq.where(bookRoot.get(Book_.id).in(idsParent));
            // Order By ID Parinte
            cq.orderBy(cb.asc(bookRoot.get(Book_.id)));
            // Execute query
            List<Tuple> tupleLst = em.createQuery(cq).getResultList();
            //
            dtoLst.addAll(fromTupleToDto(tupleLst));
            // Order BY
            orderBy(dtoLst, sortColumn, sortDirection);
        }
        return dtoLst;
    }

    private List<Long> firstSelect(BookSearchDto searchDto, int sortColumn, int sortDirection, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);
        // From
        Root<Book> bookRoot = cq.from(Book.class);
        Join<Book, BookReview> bookReviewJoin = bookRoot.join(Book_.reviews, JoinType.LEFT);
        // Select doar ID parinte
        cq.multiselect(bookRoot.get(Book_.id).alias(Book_.ID));
        // WHERE
        cq.where(buildWhere(searchDto, cb, bookRoot, bookReviewJoin));
        // Order By
        cq.orderBy(buildOrderBy(sortColumn, sortDirection, cb, bookRoot));
        // Execute query
        TypedQuery<Tuple> query = em.createQuery(cq)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize());
        List<Tuple> tupleLst = query.getResultList();
        // transform from tuple
        List<Long> idsParent = new ArrayList<>();
        if (!tupleLst.isEmpty()) {
            for (Tuple tuple : tupleLst) {
                Long id = (Long) tuple.get(0);
                idsParent.add(id);
            }
        }
        return idsParent;
    }

    private Predicate[] buildWhere(BookSearchDto searchDto, CriteriaBuilder cb, Root<Book> bookRoot, Join<Book, BookReview> bookReviewJoin) {
        List<Predicate> predicates = new ArrayList<>();
        // Title book
        if (StringUtils.hasText(searchDto.getTitle())) {
            predicates.add(cb.like(cb.upper(bookRoot.get(Book_.title)), "%" + searchDto.getTitle().toUpperCase() + "%"));
        }
        // WrittenBy review
        if (StringUtils.hasText(searchDto.getWrittenBy())) {
            predicates.add(cb.like(cb.upper(bookReviewJoin.get(BookReview_.writtenBy)), "%" + searchDto.getWrittenBy().toUpperCase() + "%"));
        }
        return predicates.toArray(new Predicate[]{});
    }

    private List<BookDto> fromTupleToDto(List<Tuple> tupleLst) {
        // multime id-uri de parinti distincte
        Set<Long> idsParent = new HashSet<>();
        // parinte curent
        BookDto parent = BookDto.builder().build();
        // lista parinti de returnat
        List<BookDto> parents = new ArrayList<>();
        if (!tupleLst.isEmpty()) {
            for (Tuple tuple : tupleLst) {
                // id parinte
                Long idParent = tuple.get(ID_BOOK, Long.class);
                if (idsParent.contains(idParent)) {
                    // am deja parintele ... adaug copilul la parintele curent
                    addCopilLaParinte(tuple, parent);
                } else {
                    // am parinte nou; adaug id parinte curent la multime id-uri parinte
                    idsParent.add(idParent);
                    // creez parinte curent
                    parent = BookDto.builder()
                            .id(tuple.get(ID_BOOK, Long.class))
                            .title(tuple.get(Book_.TITLE, String.class))
                            .build();
                    // adaug parinte curent lista parinti de returnat
                    parents.add(parent);
                    // daca are copil i-l adaug la parinte
                    if (tuple.get(BookReview_.ID, Long.class) != null) {
                        addCopilLaParinte(tuple, parent);
                    }
                }
            }
        }
        return parents;
    }

    private void addCopilLaParinte(Tuple tuple, BookDto parinte) {
        // creez copil curent
        BookReviewDto copil = BookReviewDto.builder()
                .id(tuple.get(BookReview_.ID, Long.class))
                .writtenBy(tuple.get(BookReview_.WRITTEN_BY, String.class))
                .review(tuple.get(BookReview_.REVIEW, String.class))
                .build();
        // adaug copil la parinte curent
        parinte.addBookReview(copil);
    }

    private Order buildOrderBy(Integer sortColumn, Integer sortDirection, CriteriaBuilder cb, Root<Book> bookRoot) {
        switch (sortColumn) {
            case 0:
                if (sortDirection == 0) {
                    return cb.asc(cb.upper(bookRoot.get(Book_.title)));
                } else {
                    return cb.desc(cb.upper(bookRoot.get(Book_.title)));
                }

            default:
                return cb.asc(cb.upper(bookRoot.get(Book_.title)));
        }
    }

    private void orderBy(List<BookDto> list, Integer sortColumn, Integer sortDirection) {
        Comparator<BookDto> title = Comparator.comparing(BookDto::getTitle, String::compareToIgnoreCase);
        switch (sortColumn) {
            case 0:
                if (sortDirection == 0) {
                    list.sort(title);
                } else {
                    list.sort(title.reversed());
                }
                break;
            default:
                list.sort(title);
        }
    }
}

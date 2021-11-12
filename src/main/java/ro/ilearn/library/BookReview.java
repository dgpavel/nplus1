package ro.ilearn.library;

import javax.persistence.*;

@Entity
@Table(name = "book_review")
public class BookReview {
    @Id
    private Long id;
    private String review;
    @Column(name = "written_by")
    private String writtenBy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getWrittenBy() {
        return writtenBy;
    }

    public void setWrittenBy(String writtenBy) {
        this.writtenBy = writtenBy;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "BookReview{" +
                "id=" + id +
                ", review='" + review + '\'' +
                ", writtenBy='" + writtenBy + '\'' +
                ", book=" + book +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookReview)) return false;
        return id != null && id.equals(((BookReview) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}

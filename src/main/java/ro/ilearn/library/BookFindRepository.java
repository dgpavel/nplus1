package ro.ilearn.library;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookFindRepository {

    List<BookDto> search(BookSearchDto searchDto, int sortColumn, int sortDirection);

    List<BookDto> search(BookSearchDto searchDto, int sortColumn, int sortDirection, Pageable pageable);
}

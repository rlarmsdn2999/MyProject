package org.zerock.mreview.repository.searchRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.mreview.entity.Movie;

public interface SearchMovieRepository {
    Page<Object[]> searchPage( String keyword, Pageable pageable);

}

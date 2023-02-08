package org.zerock.mreview.repository.searchRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchDramaRepository {
    Page<Object[]> searchPage(String keyword, Pageable pageable);
}

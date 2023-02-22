package org.zerock.soccer.repository.searchRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchRepository {
    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}

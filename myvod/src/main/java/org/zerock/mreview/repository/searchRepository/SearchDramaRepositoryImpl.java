package org.zerock.mreview.repository.searchRepository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.mreview.entity.*;

import java.util.List;
import java.util.stream.Collectors;

public class SearchDramaRepositoryImpl extends QuerydslRepositorySupport implements SearchDramaRepository {
    public SearchDramaRepositoryImpl() {
        super(Drama.class);
    }
    @Override
    public Page<Object[]> searchPage(String keyword, Pageable pageable) {
        QDrama drama = QDrama.drama;
        QDramaImage dramaImage = QDramaImage.dramaImage;
        QDReview dReview = QDReview.dReview;
        JPQLQuery<Drama> query = from(drama);
        query.leftJoin(dramaImage).on(dramaImage.drama.dno.eq(drama.dno)).groupBy(dramaImage);
        query.leftJoin(dReview).on(dReview.drama.eq(drama));
        if (keyword != null) {
            query.where(drama.title.contains(keyword));
        }
        query.groupBy(drama);
        JPQLQuery<Tuple> tupleJPQLQuery = query.select(drama, dramaImage, dReview.grade.avg().coalesce(0.0), dReview.count());
        getQuerydsl().applyPagination(pageable, tupleJPQLQuery);
        List<Tuple> result = tupleJPQLQuery.fetch();
        long count = tupleJPQLQuery.fetchCount();
        List<Object[]> resultArr = result.stream().map(res -> res.toArray()).collect(Collectors.toList());
        return new PageImpl<>(resultArr, pageable, count);
    }
}

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

public class SearchEnterRepositoryImpl extends QuerydslRepositorySupport implements SearchEnterRepository {
    public SearchEnterRepositoryImpl() {
        super(Enter.class);
    }
    @Override
    public Page<Object[]> searchPage(String keyword, Pageable pageable) {
        QEnter enter = QEnter.enter;
        QEnterImage enterImage = QEnterImage.enterImage;
        QEReview eReview = QEReview.eReview;
        JPQLQuery<Enter> query = from(enter);
        query.leftJoin(enterImage).on(enterImage.enter.mno.eq(enter.mno)).groupBy(enterImage);
        query.leftJoin(eReview).on(eReview.enter.eq(enter));

        if (keyword != null) {
            query.where(enter.title.contains(keyword));
        }
        query.groupBy(enter);
        JPQLQuery<Tuple> tupleJPQLQuery = query.select(enter, enterImage, eReview.grade.avg().coalesce(0.0), eReview.count());
        getQuerydsl().applyPagination(pageable, tupleJPQLQuery);
        List<Tuple> result = tupleJPQLQuery.fetch();
        long count = tupleJPQLQuery.fetchCount();
        List<Object[]> resultArr = result.stream().map(res -> res.toArray()).collect(Collectors.toList());
        return new PageImpl<>(resultArr, pageable, count);
    }
}

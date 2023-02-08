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

public class SearchCultureRepositoryImpl extends QuerydslRepositorySupport implements SearchCultureRepository {
    public SearchCultureRepositoryImpl() {
        super(Culture.class);
    }
    @Override
    public Page<Object[]> searchPage(String keyword, Pageable pageable) {
        QCulture culture = QCulture.culture;
        QCultureImage cultureImage = QCultureImage.cultureImage;
        QCReview cReview = QCReview.cReview;
        JPQLQuery<Culture> query = from(culture);
        query.leftJoin(cultureImage).on(cultureImage.culture.mno.eq(culture.mno)).groupBy(cultureImage);
        query.leftJoin(cReview).on(cReview.culture.eq(culture));
        if (keyword != null) {
            query.where(culture.title.contains(keyword));
        }
        query.groupBy(culture);
        JPQLQuery<Tuple> tupleJPQLQuery = query.select(culture, cultureImage, cReview.grade.avg(), cReview.count());
        getQuerydsl().applyPagination(pageable, tupleJPQLQuery);
        List<Tuple> result = tupleJPQLQuery.fetch();
        long count = tupleJPQLQuery.fetchCount();
        List<Object[]> resultArr = result.stream().map(res -> res.toArray()).collect(Collectors.toList());
        return new PageImpl<>(resultArr, pageable, count);
    }
}

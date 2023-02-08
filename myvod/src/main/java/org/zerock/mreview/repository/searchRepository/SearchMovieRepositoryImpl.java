package org.zerock.mreview.repository.searchRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.mreview.dto.MovieDTO;
import org.zerock.mreview.entity.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.thymeleaf.util.AggregateUtils.avg;
import static org.zerock.mreview.entity.QMember.member;

public class SearchMovieRepositoryImpl extends QuerydslRepositorySupport implements SearchMovieRepository {
    public SearchMovieRepositoryImpl() {
        super(Movie.class);
    }
    @Override
    public Page<Object[]> searchPage(String keyword, Pageable pageable) {
        QMovie movie = QMovie.movie;
        QMovieImage movieImage = QMovieImage.movieImage;
        QReview review = QReview.review;
        JPQLQuery<Movie> query = from(movie);
        query.leftJoin(movieImage).on(movieImage.movie.mno.eq(movie.mno)).groupBy(movieImage);
        query.leftJoin(review).on(review.movie.eq(movie));
        if (keyword != null) {
            query.where(movie.title.contains(keyword));
        }
        query.groupBy(movie);
        JPQLQuery<Tuple> tupleJPQLQuery = query.select(movie, movieImage, review.grade.avg(), review.count());
        getQuerydsl().applyPagination(pageable, tupleJPQLQuery);
        List<Tuple> result = tupleJPQLQuery.fetch();
        long count = tupleJPQLQuery.fetchCount();
        List<Object[]> resultArr = result.stream().map(res -> res.toArray()).collect(Collectors.toList());
        return new PageImpl<>(resultArr, pageable, count);
    }
}

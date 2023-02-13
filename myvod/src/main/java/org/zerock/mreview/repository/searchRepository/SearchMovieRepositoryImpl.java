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

public class SearchMovieRepositoryImpl extends QuerydslRepositorySupport implements SearchMovieRepository {
    public SearchMovieRepositoryImpl() {
        super(Movie.class);
    }
    @Override
    public Page<Object[]> searchPage( String keyword, Pageable pageable) {
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
        JPQLQuery<Tuple> tupleJPQLQuery = query.select(movie, movieImage, review.grade.avg().coalesce(0.0), review.count());
        getQuerydsl().applyPagination(pageable, tupleJPQLQuery);
        List<Tuple> result = tupleJPQLQuery.fetch();
        long count = tupleJPQLQuery.fetchCount();
        List<Object[]> resultArr = result.stream().map(res -> res.toArray()).collect(Collectors.toList());
        return new PageImpl<>(resultArr, pageable, count);
    }
}

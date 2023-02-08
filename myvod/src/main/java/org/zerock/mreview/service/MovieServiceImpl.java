package org.zerock.mreview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview.dto.*;
import org.zerock.mreview.entity.*;
import org.zerock.mreview.repository.MovieImageRepository;
import org.zerock.mreview.repository.MovieRepository;
import org.zerock.mreview.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.zerock.mreview.entity.QMovieImage.movieImage;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{
    private final MovieRepository movieRepository;
    private final MovieImageRepository movieImageRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    @Override
    public Long register(MovieDTO movieDTO){
        Map<String, Object> entityMap = dtoToEntity(movieDTO);
        Movie movie = (Movie) entityMap.get("movie");
        List<MovieImage> movieImageList = (List<MovieImage>) entityMap.get("imgList");
        movieRepository.save(movie);
        movieImageList.forEach(movieImage -> {
            movieImageRepository.save(movieImage);
        });
        return movie.getMno();
    }

    @Override
    public PageResultDTO<MovieDTO,Object[]> getList(PageRequestDTO pageRequestDTO){
        if(pageRequestDTO.getKeyword() == null) {
            Pageable pageable = pageRequestDTO.getPageable(Sort.by("mno").descending());
            Page<Object[]> result = movieRepository.getListPage(pageable);
            Function<Object[], MovieDTO> fn = (arr -> entitiesToDTO(
                    (Movie) arr[0],
                    (List<MovieImage>) (Arrays.asList((MovieImage) arr[1])),
                    (Double) arr[2],
                    (Long) arr[3])
            );
            return new PageResultDTO<>(result, fn);
        }else{
            Function<Object[], MovieDTO> fn = (arr -> entitiesToDTO(
                    (Movie) arr[0],
                    (List<MovieImage>) (Arrays.asList((MovieImage)arr[1])),
                    (Double) arr[2],
                    (Long) arr[3])
            );
            Page<Object[]> result = movieRepository.searchPage(
                    pageRequestDTO.getKeyword(),
                    pageRequestDTO.getPageable(Sort.by("mno").descending())
            );
            for (Object ob : result) {
                System.out.println(ob.toString());
            }
            return new PageResultDTO<>(result, fn);
        }
    }

    @Override
    public MovieDTO getMovie(Long mno){
        List<Object[]> result = movieRepository.getMovieWithAll(mno);
        Movie movie = (Movie) result.get(0)[0];
        List<MovieImage> movieImageList = new ArrayList<>();
        result.forEach(arr -> {
            MovieImage movieImage = (MovieImage) arr[1];
            movieImageList.add(movieImage);
        });
        Double avg = (Double) result.get(0)[2];
        Long reviewCnt = (Long) result.get(0)[3];
        return entitiesToDTO(movie, movieImageList, avg, reviewCnt);
    }

    @Transactional
    @Override
    public void remove(Long mno){
        movieImageRepository.deleteByMno(mno);
        reviewRepository.deleteByMno(mno);
        movieRepository.deleteById(mno);
    }
    @Transactional
    @Override
    public void modify(MovieDTO movieDTO){
        Movie movie = movieRepository.getOne(movieDTO.getMno());
        movie.changeTitle(movieDTO.getTitle());
        movie.changeContent(movieDTO.getContent());
        movie.changeActor(movieDTO.getActor());
        movieRepository.save(movie);
    }
}
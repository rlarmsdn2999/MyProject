package org.zerock.mreview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview.dto.DramaDTO;
import org.zerock.mreview.dto.MovieDTO;
import org.zerock.mreview.dto.PageRequestDTO;
import org.zerock.mreview.dto.PageResultDTO;
import org.zerock.mreview.entity.Drama;
import org.zerock.mreview.entity.DramaImage;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;
import org.zerock.mreview.repository.DramaImageRepository;
import org.zerock.mreview.repository.DramaRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.zerock.mreview.entity.QMovie.movie;

@Service
@RequiredArgsConstructor
public class DramaServiceImpl implements DramaService{
    private final DramaRepository dramaRepository;
    private final DramaImageRepository dramaImageRepository;
    @Transactional
    @Override
    public Long register(DramaDTO dramaDTO){
        Map<String, Object> entityMap = dtoToEntity(dramaDTO);
        Drama drama = (Drama) entityMap.get("drama");
        List<DramaImage> dramaImageList = (List<DramaImage>) entityMap.get("imgList");
        dramaRepository.save(drama);
        dramaImageList.forEach(dramaImage -> {
            dramaImageRepository.save(dramaImage);
        });
        return drama.getDno();
    }

    @Override
    public PageResultDTO<DramaDTO,Object[]> getList(PageRequestDTO pageRequestDTO){
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("dno").descending());
        Page<Object[]> result = dramaRepository.getListPage(pageable);
        Function<Object[], DramaDTO> fn=(arr->entitiesToDTO(
                (Drama)arr[0],
                (List<DramaImage>)(Arrays.asList((DramaImage)arr[1])),
                (Double) arr[2],
                (Long)arr[3]
        ));
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public DramaDTO getDrama(Long dno){
        List<Object[]> result = dramaRepository.getDramaWithAll(dno);
        Drama drama = (Drama) result.get(0)[0];
        List<DramaImage> dramaImageList = new ArrayList<>();
        result.forEach(arr -> {
            DramaImage dramaImage = (DramaImage) arr[1];
            dramaImageList.add(dramaImage);
        });
        Double avg = (Double) result.get(0)[2];
        Long reviewCnt = (Long) result.get(0)[3];
        return entitiesToDTO(drama, dramaImageList, avg, reviewCnt);
    }

}

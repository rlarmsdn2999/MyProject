package org.zerock.mreview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview.dto.*;
import org.zerock.mreview.entity.*;
import org.zerock.mreview.repository.DReviewRepository;
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
    private final DReviewRepository dReviewRepository;

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
        if(pageRequestDTO.getKeyword() == null) {
            Pageable pageable = pageRequestDTO.getPageable(Sort.by("dno").descending());
            Page<Object[]> result = dramaRepository.getListPage(pageable);
            for (Object ob : result) {
                System.out.println(result.toString());
            }
            Function<Object[], DramaDTO> fn = (arr -> entitiesToDTO(
                    (Drama) arr[0],
                    (List<DramaImage>) (Arrays.asList((DramaImage) arr[1])),
                    (Double) arr[2],
                    (Long) arr[3])
            );
            return new PageResultDTO<>(result, fn);
        }else{
            Function<Object[], DramaDTO> fn = (arr -> entitiesToDTO(
                    (Drama) arr[0],
                    (List<DramaImage>) (Arrays.asList((DramaImage)arr[1])),
                    (Double) arr[2],
                    (Long)arr[3])
            );
            Page<Object[]> result = dramaRepository.searchPage(
                    pageRequestDTO.getKeyword(),
                    pageRequestDTO.getPageable(Sort.by("dno").descending())
            );
            for (Object ob : result) {
                System.out.println(ob.toString());
            }
            return new PageResultDTO<>(result, fn);
        }
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
    @Transactional
    @Override
    public void remove(Long dno){
        dramaImageRepository.deleteByDno(dno);
        dReviewRepository.deleteByDno(dno);
        dramaRepository.deleteById(dno);
    }
    @Transactional
    @Override
    public void modify(DramaDTO dramaDTO){
        Drama drama = dramaRepository.getOne(dramaDTO.getDno());
        drama.changeTitle(dramaDTO.getTitle());
        drama.changeContent(dramaDTO.getContent());
        drama.changeActor(dramaDTO.getActor());
        dramaRepository.save(drama);
    }
}

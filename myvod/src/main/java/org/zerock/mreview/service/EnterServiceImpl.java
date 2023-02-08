package org.zerock.mreview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview.dto.EnterDTO;
import org.zerock.mreview.dto.MovieDTO;
import org.zerock.mreview.dto.PageRequestDTO;
import org.zerock.mreview.dto.PageResultDTO;
import org.zerock.mreview.entity.Enter;
import org.zerock.mreview.entity.EnterImage;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;
import org.zerock.mreview.repository.EReviewRepository;
import org.zerock.mreview.repository.EnterImageRepository;
import org.zerock.mreview.repository.EnterRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class EnterServiceImpl implements EnterService{
    private final EnterRepository enterRepository;
    private final EnterImageRepository enterImageRepository;
    private final EReviewRepository eReviewRepository;

    @Transactional
    @Override
    public Long register(EnterDTO enterDTO){
        Map<String, Object> entityMap = dtoToEntity(enterDTO);
        Enter enter = (Enter) entityMap.get("enter");
        List<EnterImage> enterImageList = (List<EnterImage>) entityMap.get("imgList");
        enterRepository.save(enter);
        enterImageList.forEach(enterImage -> {
            enterImageRepository.save(enterImage);
        });
        return enter.getMno();
    }

    @Override
    public PageResultDTO<EnterDTO,Object[]> getList(PageRequestDTO pageRequestDTO){
        if(pageRequestDTO.getKeyword() == null) {
            Pageable pageable = pageRequestDTO.getPageable(Sort.by("mno").descending());
            Page<Object[]> result = enterRepository.getListPage(pageable);
            for (Object ob : result) {
                System.out.println(result.toString());
            }
            Function<Object[], EnterDTO> fn = (arr -> entitiesToDTO(
                    (Enter) arr[0],
                    (List<EnterImage>) (Arrays.asList((EnterImage) arr[1])),
                    (Double) arr[2],
                    (Long) arr[3])
            );
            return new PageResultDTO<>(result, fn);
        }else{
            Function<Object[], EnterDTO> fn = (arr -> entitiesToDTO(
                    (Enter) arr[0],
                    (List<EnterImage>) (Arrays.asList((EnterImage)arr[1])),
                    (Double) arr[2],
                    (Long)arr[3])
            );
            Page<Object[]> result = enterRepository.searchPage(
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
    public EnterDTO getEnter(Long mno){
        List<Object[]> result = enterRepository.getEnterWithAll(mno);
        Enter enter = (Enter) result.get(0)[0];
        List<EnterImage> enterImageList = new ArrayList<>();
        result.forEach(arr -> {
            EnterImage enterImage = (EnterImage) arr[1];
            enterImageList.add(enterImage);
        });
        Double avg = (Double) result.get(0)[2];
        Long reviewCnt = (Long) result.get(0)[3];
        return entitiesToDTO(enter, enterImageList, avg, reviewCnt);
    }

    @Transactional
    @Override
    public void remove(Long mno){
        enterImageRepository.deleteByMno(mno);
        eReviewRepository.deleteByMno(mno);
        enterRepository.deleteById(mno);
    }
    @Transactional
    @Override
    public void modify(EnterDTO enterDTO){
        Enter enter = enterRepository.getOne(enterDTO.getMno());
        enter.changeTitle(enterDTO.getTitle());
        enter.changeContent(enterDTO.getContent());
        enter.changeActor(enterDTO.getActor());
        enterRepository.save(enter);
    }
}

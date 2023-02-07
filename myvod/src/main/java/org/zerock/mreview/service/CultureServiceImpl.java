package org.zerock.mreview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview.dto.CultureDTO;
import org.zerock.mreview.dto.MovieDTO;
import org.zerock.mreview.dto.PageRequestDTO;
import org.zerock.mreview.dto.PageResultDTO;
import org.zerock.mreview.entity.Culture;
import org.zerock.mreview.entity.CultureImage;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;
import org.zerock.mreview.repository.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CultureServiceImpl implements CultureService{
    private final CultureRepository cultureRepository;
    private final CultureImageRepository cultureImageRepository;


    @Transactional
    @Override
    public Long register(CultureDTO cultureDTO){
        Map<String, Object> entityMap = dtoToEntity(cultureDTO);
        Culture culture = (Culture) entityMap.get("culture");
        List<CultureImage> cultureImageList = (List<CultureImage>) entityMap.get("imgList");
        cultureRepository.save(culture);
        cultureImageList.forEach(cultureImage -> {
            cultureImageRepository.save(cultureImage);
        });
        return culture.getMno();
    }

    @Override
    public PageResultDTO<CultureDTO,Object[]> getList(PageRequestDTO pageRequestDTO){
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("mno").descending());
        Page<Object[]> result = cultureRepository.getListPage(pageable);
        Function<Object[], CultureDTO> fn=(arr->entitiesToDTO(
                (Culture)arr[0],
                (List<CultureImage>)(Arrays.asList((CultureImage)arr[1])),
                (Double) arr[2],
                (Long)arr[3]
        ));
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public CultureDTO getCulture(Long mno){
        List<Object[]> result = cultureRepository.getCultureWithAll(mno);
        Culture culture = (Culture) result.get(0)[0];
        List<CultureImage> cultureImageList = new ArrayList<>();
        result.forEach(arr -> {
            CultureImage cultureImage = (CultureImage) arr[1];
            cultureImageList.add(cultureImage);
        });
        Double avg = (Double) result.get(0)[2];
        Long reviewCnt = (Long) result.get(0)[3];
        return entitiesToDTO(culture, cultureImageList, avg, reviewCnt);
    }

}

package org.zerock.mreview.service;

import org.zerock.mreview.dto.*;
import org.zerock.mreview.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface CultureService {
    Long register(CultureDTO cultureDTO);
    PageResultDTO<CultureDTO, Object[]> getList(PageRequestDTO requestDTO);
    CultureDTO getCulture(Long mno);
    void remove(Long mno);

    void modify(CultureDTO cultureDTO);

    default Map<String, Object> dtoToEntity(CultureDTO cultureDTO){
        Map<String, Object> entityMap = new HashMap<>();
        Culture culture = Culture.builder()
                .mno(cultureDTO.getMno())
                .title(cultureDTO.getTitle())
                .content(cultureDTO.getContent())
                .actor(cultureDTO.getActor())
                .build();
        entityMap.put("culture", culture);
        List<CultureImageDTO> imageDTOList = cultureDTO.getImageDTOList();
        if(imageDTOList != null && imageDTOList.size() > 0){
            List<CultureImage> cultureImageList = imageDTOList.stream().map(cultureImageDTO -> {
                CultureImage cultureImage = CultureImage.builder()
                        .path(cultureImageDTO.getPath())
                        .imgName(cultureImageDTO.getImgName())
                        .uuid(cultureImageDTO.getUuid())
                        .culture(culture)
                        .build();
                return cultureImage;
            }).collect(Collectors.toList());
            entityMap.put("imgList", cultureImageList);
        }
        return entityMap;
    }

    default CultureDTO entitiesToDTO(Culture culture, List<CultureImage> cultureImages, Double avg, Long reviewCnt){
        CultureDTO cultureDTO = CultureDTO.builder()
                .mno(culture.getMno())
                .title(culture.getTitle())
                .content(culture.getContent())
                .actor(culture.getActor())
                .regDate(culture.getRegDate())
                .modDate(culture.getModDate())
                .build();
        List<CultureImageDTO> cultureImageDTOList = cultureImages.stream().map(cultureImage -> {
            return CultureImageDTO.builder().imgName(cultureImage.getImgName())
                    .path(cultureImage.getPath())
                    .uuid(cultureImage.getUuid())
                    .build();
        }).collect(Collectors.toList());
        cultureDTO.setImageDTOList(cultureImageDTOList);
        cultureDTO.setAvg(avg);
        cultureDTO.setReviewCnt(reviewCnt.intValue());
        return cultureDTO;
    }
}

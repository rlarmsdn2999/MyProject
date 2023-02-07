package org.zerock.mreview.service;

import org.zerock.mreview.dto.*;
import org.zerock.mreview.entity.Enter;
import org.zerock.mreview.entity.EnterImage;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface EnterService {
    Long register(EnterDTO enterDTO);
    PageResultDTO<EnterDTO, Object[]> getList(PageRequestDTO requestDTO);
    EnterDTO getEnter(Long mno);

    default Map<String, Object> dtoToEntity(EnterDTO enterDTO){
        Map<String, Object> entityMap = new HashMap<>();
        Enter enter = Enter.builder()
                .mno(enterDTO.getMno())
                .title(enterDTO.getTitle())
                .content(enterDTO.getContent())
                .actor(enterDTO.getActor())
                .build();
        entityMap.put("enter", enter);
        List<EnterImageDTO> imageDTOList = enterDTO.getImageDTOList();
        if(imageDTOList != null && imageDTOList.size() > 0){
            List<EnterImage> enterImageList = imageDTOList.stream().map(enterImageDTO -> {
                EnterImage enterImage = EnterImage.builder()
                        .path(enterImageDTO.getPath())
                        .imgName(enterImageDTO.getImgName())
                        .uuid(enterImageDTO.getUuid())
                        .enter(enter)
                        .build();
                return enterImage;
            }).collect(Collectors.toList());
            entityMap.put("imgList", enterImageList);
        }
        return entityMap;
    }

    default EnterDTO entitiesToDTO(Enter enter, List<EnterImage> enterImages, Double avg, Long reviewCnt){
        EnterDTO enterDTO = EnterDTO.builder()
                .mno(enter.getMno())
                .title(enter.getTitle())
                .content(enter.getContent())
                .actor(enter.getActor())
                .regDate(enter.getRegDate())
                .modDate(enter.getModDate())
                .build();
        List<EnterImageDTO> enterImageDTOList = enterImages.stream().map(enterImage -> {
            return EnterImageDTO.builder().imgName(enterImage.getImgName())
                    .path(enterImage.getPath())
                    .uuid(enterImage.getUuid())
                    .build();
        }).collect(Collectors.toList());
        enterDTO.setImageDTOList(enterImageDTOList);
        enterDTO.setAvg(avg);
        enterDTO.setReviewCnt(reviewCnt.intValue());
        return enterDTO;
    }
}

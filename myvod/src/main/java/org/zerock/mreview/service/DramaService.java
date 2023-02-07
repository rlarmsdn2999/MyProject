package org.zerock.mreview.service;

import org.zerock.mreview.dto.*;
import org.zerock.mreview.entity.Drama;
import org.zerock.mreview.entity.DramaImage;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface DramaService {
    Long register(DramaDTO dramaDTO);
    PageResultDTO<DramaDTO, Object[]> getList(PageRequestDTO requestDTO);
    DramaDTO getDrama(Long dno);

    default Map<String, Object> dtoToEntity(DramaDTO dramaDTO){
        Map<String, Object> entityMap = new HashMap<>();
        Drama drama = Drama.builder()
                .dno(dramaDTO.getDno())
                .title(dramaDTO.getTitle())
                .content(dramaDTO.getContent())
                .actor(dramaDTO.getActor())
                .build();
        entityMap.put("drama", drama);
        List<DramaImageDTO> imageDTOList = dramaDTO.getImageDTOList();
        if(imageDTOList != null && imageDTOList.size() > 0){
            List<DramaImage> dramaImageList = imageDTOList.stream().map(dramaImageDTO -> {
                DramaImage dramaImage = DramaImage.builder()
                        .path(dramaImageDTO.getPath())
                        .imgName(dramaImageDTO.getImgName())
                        .uuid(dramaImageDTO.getUuid())
                        .drama(drama)
                        .build();
                return dramaImage;
            }).collect(Collectors.toList());
            entityMap.put("imgList", dramaImageList);
        }
        return entityMap;
    }

    default DramaDTO entitiesToDTO(Drama drama, List<DramaImage> dramaImages, Double avg, Long reviewCnt){
        DramaDTO dramaDTO = DramaDTO.builder()
                .dno(drama.getDno())
                .title(drama.getTitle())
                .content(drama.getContent())
                .actor(drama.getActor())
                .regDate(drama.getRegDate())
                .modDate(drama.getModDate())
                .build();
        List<DramaImageDTO> dramaImageDTOList = dramaImages.stream().map(dramaImage -> {
            return DramaImageDTO.builder().imgName(dramaImage.getImgName())
                    .path(dramaImage.getPath())
                    .uuid(dramaImage.getUuid())
                    .build();
        }).collect(Collectors.toList());
        dramaDTO.setImageDTOList(dramaImageDTOList);
        dramaDTO.setAvg(avg);
        dramaDTO.setReviewCnt(reviewCnt.intValue());
        return dramaDTO;
    }
}

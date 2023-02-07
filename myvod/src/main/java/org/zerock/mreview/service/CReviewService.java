package org.zerock.mreview.service;

import org.zerock.mreview.dto.CReviewDTO;
import org.zerock.mreview.dto.ReviewDTO;
import org.zerock.mreview.entity.*;

import java.util.List;

public interface CReviewService {
    List<CReviewDTO> getListOfCulture(Long mno);
    Long register(CReviewDTO cultureReviewDTO);
    void modify(CReviewDTO cultureReviewDTO);
    void remove(Long reviewnum);
    default CReview dtoToEntity(CReviewDTO cultureReviewDTO){
        CReview cultureReview = CReview.builder()
                .reviewnum(cultureReviewDTO.getReviewnum())
                .culture(Culture.builder().mno(cultureReviewDTO.getMno()).build())
                .cmember(CMember.builder().mid(cultureReviewDTO.getMid()).build())
                .cmember(CMember.builder().nickname(cultureReviewDTO.getNickname()).build())
                .grade(cultureReviewDTO.getGrade())
                .text(cultureReviewDTO.getText())
                .build();
        return cultureReview;
    }
    default CReviewDTO entityToDTO(CReview cultureReview){
        CReviewDTO cultureReviewDTO = CReviewDTO.builder()
                .reviewnum(cultureReview.getReviewnum())
                .mno(cultureReview.getCulture().getMno())
                .mid(cultureReview.getCmember().getMid())
                .nickname(cultureReview.getCmember().getNickname())
                .email(cultureReview.getCmember().getEmail())
                .grade(cultureReview.getGrade())
                .text(cultureReview.getText())
                .regDate(cultureReview.getRegDate())
                .modDate(cultureReview.getModDate())
                .build();
        return cultureReviewDTO;
    }
}

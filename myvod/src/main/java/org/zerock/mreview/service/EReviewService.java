package org.zerock.mreview.service;

import org.zerock.mreview.dto.EReviewDTO;
import org.zerock.mreview.dto.ReviewDTO;
import org.zerock.mreview.entity.*;

import java.util.List;

public interface EReviewService {
    List<EReviewDTO> getListOfEnter(Long mno);
    Long register(EReviewDTO enterReviewDTO);
    void modify(EReviewDTO enterReviewDTO);
    void remove(Long reviewnum);
    default EReview dtoToEntity(EReviewDTO enterReviewDTO){
        EReview enterReview = EReview.builder()
                .reviewnum(enterReviewDTO.getReviewnum())
                .enter(Enter.builder().mno(enterReviewDTO.getMno()).build())
                .emember(EMember.builder().mid(enterReviewDTO.getMid()).build())
                .emember(EMember.builder().nickname(enterReviewDTO.getNickname()).build())
                .grade(enterReviewDTO.getGrade())
                .text(enterReviewDTO.getText())
                .build();
        return enterReview;
    }
    default EReviewDTO entityToDTO(EReview enterReview){
        EReviewDTO enterReviewDTO = EReviewDTO.builder()
                .reviewnum(enterReview.getReviewnum())
                .mno(enterReview.getEnter().getMno())
                .mid(enterReview.getEmember().getMid())
                .nickname(enterReview.getEmember().getNickname())
                .email(enterReview.getEmember().getEmail())
                .grade(enterReview.getGrade())
                .text(enterReview.getText())
                .regDate(enterReview.getRegDate())
                .modDate(enterReview.getModDate())
                .build();
        return enterReviewDTO;
    }
}

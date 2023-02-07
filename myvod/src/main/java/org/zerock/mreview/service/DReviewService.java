package org.zerock.mreview.service;

import org.zerock.mreview.dto.DReviewDTO;
import org.zerock.mreview.dto.ReviewDTO;
import org.zerock.mreview.entity.*;

import java.util.List;

import static org.zerock.mreview.entity.QDrama.drama;

public interface DReviewService {
    List<DReviewDTO> getListOfDrama(Long rno);
    Long register(DReviewDTO dramaReviewDTO);
    void modify(DReviewDTO dramaReviewDTO);
    void remove(Long reviewnum);
    default DReview dtoToEntity(DReviewDTO dramaReviewDTO){
        DReview dramaReview = DReview.builder()
                .reviewnum(dramaReviewDTO.getReviewnum())
                .drama(Drama.builder().dno(dramaReviewDTO.getMno()).build())
                .dmember(DMember.builder().mid(dramaReviewDTO.getMid()).build())
                .dmember(DMember.builder().nickname(dramaReviewDTO.getNickname()).build())
                .grade(dramaReviewDTO.getGrade())
                .text(dramaReviewDTO.getText())
                .build();
        return dramaReview;
    }
    default DReviewDTO entityToDTO(DReview dramaReview){
        DReviewDTO dramaReviewDTO = DReviewDTO.builder()
                .reviewnum(dramaReview.getReviewnum())
                .mno(dramaReview.getDrama().getDno())
                .mid(dramaReview.getDmember().getMid())
                .nickname(dramaReview.getDmember().getNickname())
                .email(dramaReview.getDmember().getEmail())
                .grade(dramaReview.getGrade())
                .text(dramaReview.getText())
                .regDate(dramaReview.getRegDate())
                .modDate(dramaReview.getModDate())
                .build();
        return dramaReviewDTO;
    }
}

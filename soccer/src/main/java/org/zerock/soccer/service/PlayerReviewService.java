package org.zerock.soccer.service;

import org.zerock.soccer.dto.ReviewDTO;
import org.zerock.soccer.entity.Player;
import org.zerock.soccer.entity.PlayerMember;
import org.zerock.soccer.entity.Review;


import java.util.List;

public interface PlayerReviewService {
    List<ReviewDTO> getListOfPlayer(Long pno);
    Long register(ReviewDTO reviewDTO);
    void modify(ReviewDTO ReviewDTO);
    void remove(Long reviewnum);
    default Review dtoToEntity(ReviewDTO playerReviewDTO){
        Review playerReview = Review.builder()
                .reviewnum(playerReviewDTO.getReviewnum())
                .player(Player.builder().pno(playerReviewDTO.getMno()).build())
                .playerMember(PlayerMember.builder().mid(playerReviewDTO.getMid()).build())
                .playerMember(PlayerMember.builder().nickname(playerReviewDTO.getNickname()).build())
                .grade(playerReviewDTO.getGrade())
                .text(playerReviewDTO.getText())
                .build();
        return playerReview;
    }
    default ReviewDTO entityToDTO(Review playerReview){
        ReviewDTO playerReviewDTO = ReviewDTO.builder()
                .reviewnum(playerReview.getReviewnum())
                .mno(playerReview.getPlayer().getPno())
                .mid(playerReview.getPlayerMember().getMid())
                .nickname(playerReview.getPlayerMember().getNickname())
                .email(playerReview.getPlayerMember().getEmail())
                .text(playerReview.getText())
                .grade(playerReview.getGrade())
                .regDate(playerReview.getRegDate())
                .modDate(playerReview.getModDate())
                .build();
        return playerReviewDTO;
    }
}

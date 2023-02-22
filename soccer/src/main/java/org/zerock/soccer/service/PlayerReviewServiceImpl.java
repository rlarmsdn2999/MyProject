package org.zerock.soccer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.soccer.dto.ReviewDTO;
import org.zerock.soccer.entity.Player;
import org.zerock.soccer.entity.Review;
import org.zerock.soccer.repository.ReviewRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerReviewServiceImpl implements PlayerReviewService{
    private final ReviewRepository reviewRepository;


    @Override
    public List<ReviewDTO> getListOfPlayer(Long pno){
        Player player = Player.builder().pno(pno).build();
        List<Review> result = reviewRepository.findByPlayer(player);
        return result.stream().map(Review -> entityToDTO(Review)).collect(Collectors.toList());
    }

    @Override
    public Long register(ReviewDTO reviewDTO){
        Review review = dtoToEntity(reviewDTO);
        reviewRepository.save(review);
        return review.getReviewnum();
    }
    @Override
    public void modify(ReviewDTO reviewDTO){
        Optional<Review> result = reviewRepository.findById(reviewDTO.getReviewnum());
        if(result.isPresent()){
            Review review = result.get();
            review.changeGrade(reviewDTO.getGrade());
            review.changeText(reviewDTO.getText());
            reviewRepository.save(review);
        }
    }
    @Override
    public void remove(Long reviewnum){
        reviewRepository.deleteById(reviewnum);
    }
}

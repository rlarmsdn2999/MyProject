package org.zerock.mreview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.mreview.dto.EReviewDTO;
import org.zerock.mreview.dto.ReviewDTO;
import org.zerock.mreview.entity.EReview;
import org.zerock.mreview.entity.Enter;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;
import org.zerock.mreview.repository.EReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EReviewServiceImpl implements EReviewService{
    private final EReviewRepository eReviewRepository;
    @Override
    public List<EReviewDTO> getListOfEnter(Long mno){
        Enter enter = Enter.builder().mno(mno).build();
        List<EReview> result = eReviewRepository.findByEnter(enter);
        return result.stream().map(enterReview -> entityToDTO(enterReview)).collect(Collectors.toList());
    }

    @Override
    public Long register(EReviewDTO enterReviewDTO){
        EReview enterReview = dtoToEntity(enterReviewDTO);
        eReviewRepository.save(enterReview);
        return enterReview.getReviewnum();
    }
    @Override
    public void modify(EReviewDTO enterReviewDTO){
        Optional<EReview> result = eReviewRepository.findById(enterReviewDTO.getReviewnum());
        if(result.isPresent()){
            EReview enterReview = result.get();
            enterReview.changeGrade(enterReviewDTO.getGrade());
            enterReview.changeText(enterReviewDTO.getText());
            eReviewRepository.save(enterReview);
        }
    }
    @Override
    public void remove(Long reviewnum){
        eReviewRepository.deleteById(reviewnum);
    }
}

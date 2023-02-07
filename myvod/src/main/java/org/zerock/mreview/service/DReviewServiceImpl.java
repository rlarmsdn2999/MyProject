package org.zerock.mreview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.mreview.dto.DReviewDTO;
import org.zerock.mreview.dto.ReviewDTO;
import org.zerock.mreview.entity.DReview;
import org.zerock.mreview.entity.Drama;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;
import org.zerock.mreview.repository.DReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DReviewServiceImpl implements DReviewService{
    private final DReviewRepository dReviewRepository;
    @Override
    public List<DReviewDTO> getListOfDrama(Long dno){
        Drama drama = Drama.builder().dno(dno).build();
        List<DReview> result = dReviewRepository.findByDrama(drama);
        return result.stream().map(dramaReview -> entityToDTO(dramaReview)).collect(Collectors.toList());
    }

    @Override
    public Long register(DReviewDTO dramaReviewDTO){
        DReview dramaReview = dtoToEntity(dramaReviewDTO);
        dReviewRepository.save(dramaReview);
        return dramaReview.getReviewnum();
    }
    @Override
    public void modify(DReviewDTO dramaReviewDTO){
        Optional<DReview> result = dReviewRepository.findById(dramaReviewDTO.getReviewnum());
        if(result.isPresent()){
            DReview dramaReview = result.get();
            dramaReview.changeGrade(dramaReviewDTO.getGrade());
            dramaReview.changeText(dramaReviewDTO.getText());
            dReviewRepository.save(dramaReview);
        }
    }
    @Override
    public void remove(Long reviewnum){
        dReviewRepository.deleteById(reviewnum);
    }
}

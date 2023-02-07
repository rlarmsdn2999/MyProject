package org.zerock.mreview.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.mreview.dto.CReviewDTO;
import org.zerock.mreview.dto.ReviewDTO;
import org.zerock.mreview.entity.CReview;
import org.zerock.mreview.entity.Culture;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;
import org.zerock.mreview.repository.CReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CReviewServiceImpl implements CReviewService{
    private final CReviewRepository cReviewRepository;
    @Override
    public List<CReviewDTO> getListOfCulture(Long mno){
        Culture culture = Culture.builder().mno(mno).build();
        List<CReview> result = cReviewRepository.findByCulture(culture);
        return result.stream().map(cultureReview -> entityToDTO(cultureReview)).collect(Collectors.toList());
    }

    @Override
    public Long register(CReviewDTO cultureReviewDTO){
        CReview cultureReview = dtoToEntity(cultureReviewDTO);
        cReviewRepository.save(cultureReview);
        return cultureReview.getReviewnum();
    }
    @Override
    public void modify(CReviewDTO cultureReviewDTO){
        Optional<CReview> result = cReviewRepository.findById(cultureReviewDTO.getReviewnum());
        if(result.isPresent()){
            CReview cultureReview = result.get();
            cultureReview.changeGrade(cultureReviewDTO.getGrade());
            cultureReview.changeText(cultureReviewDTO.getText());
            cReviewRepository.save(cultureReview);
        }
    }
    @Override
    public void remove(Long reviewnum){
        cReviewRepository.deleteById(reviewnum);
    }
}

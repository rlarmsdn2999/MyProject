package org.zerock.mreview.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.mreview.dto.EReviewDTO;
import org.zerock.mreview.dto.ReviewDTO;
import org.zerock.mreview.repository.EReviewRepository;
import org.zerock.mreview.service.EReviewService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ereviews")
public class EReviewController {
    private final EReviewService eReviewService;
    @GetMapping("/{mno}/all")
    public ResponseEntity<List<EReviewDTO>> getList(@PathVariable("mno") Long mno){
        List<EReviewDTO> reviewDTOList = eReviewService.getListOfEnter(mno);
        return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
    }

    @PostMapping("/{mno}")
    public ResponseEntity<Long> addReview(@RequestBody EReviewDTO enterReviewDTO){
        Long reviewnum = eReviewService.register(enterReviewDTO);
        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }

    @PutMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> modifyReview(@PathVariable Long reviewnum, @RequestBody EReviewDTO enterReviewDTO){
        eReviewService.modify(enterReviewDTO);
        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }

    @DeleteMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> removeReview(@PathVariable Long reviewnum){
        eReviewService.remove(reviewnum);
        return new ResponseEntity<>(reviewnum,HttpStatus.OK);
    }
}

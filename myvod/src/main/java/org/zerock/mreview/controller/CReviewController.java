package org.zerock.mreview.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.mreview.dto.CReviewDTO;
import org.zerock.mreview.dto.ReviewDTO;
import org.zerock.mreview.service.CReviewService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/creviews")
public class CReviewController {
    private final CReviewService cReviewService;
    @GetMapping("/{mno}/all")
    public ResponseEntity<List<CReviewDTO>> getList(@PathVariable("mno") Long mno){
        List<CReviewDTO> reviewDTOList = cReviewService.getListOfCulture(mno);
        return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
    }

    @PostMapping("/{mno}")
    public ResponseEntity<Long> addReview(@RequestBody CReviewDTO cultureReviewDTO){
        Long reviewnum = cReviewService.register(cultureReviewDTO);
        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }

    @PutMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> modifyReview(@PathVariable Long reviewnum, @RequestBody CReviewDTO cultureReviewDTO){
        cReviewService.modify(cultureReviewDTO);
        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }

    @DeleteMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> removeReview(@PathVariable Long reviewnum){
        cReviewService.remove(reviewnum);
        return new ResponseEntity<>(reviewnum,HttpStatus.OK);
    }
}

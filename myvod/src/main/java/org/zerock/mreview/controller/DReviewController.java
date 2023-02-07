package org.zerock.mreview.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.mreview.dto.DReviewDTO;
import org.zerock.mreview.dto.ReviewDTO;
import org.zerock.mreview.service.DReviewService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dreviews")
public class DReviewController {
    private final DReviewService dReviewService;
    @GetMapping("/{mno}/all")
    public ResponseEntity<List<DReviewDTO>> getList(@PathVariable("mno") Long mno){
        List<DReviewDTO> dreviewDTOList = dReviewService.getListOfDrama(mno);
        return new ResponseEntity<>(dreviewDTOList, HttpStatus.OK);
    }

    @PostMapping("/{mno}")
    public ResponseEntity<Long> addReview(@RequestBody DReviewDTO dramaReviewDTO){
        Long reviewnum = dReviewService.register(dramaReviewDTO);
        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }

    @PutMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> modifyReview(@PathVariable Long reviewnum, @RequestBody DReviewDTO dramaReviewDTO){
        dReviewService.modify(dramaReviewDTO);
        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }

    @DeleteMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> removeReview(@PathVariable Long reviewnum){
        dReviewService.remove(reviewnum);
        return new ResponseEntity<>(reviewnum,HttpStatus.OK);
    }
}

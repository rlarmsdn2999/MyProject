package org.zerock.mreview.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnterDTO {
    private Long mno;
    private String title;
    @Builder.Default
    private List<EnterImageDTO> imageDTOList = new ArrayList<>();

    private double avg;
    private int reviewCnt;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private String content;
    private String actor;
}

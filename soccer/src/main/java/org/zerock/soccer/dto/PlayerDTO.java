package org.zerock.soccer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDTO {
    private Long pno;
    private String name;
    private String contury;
    private String team;
    @Builder.Default
    private List<PlayerImageDTO> imageDTOList = new ArrayList<>();
    private double avg;
    private int reviewCnt;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private String history;
}

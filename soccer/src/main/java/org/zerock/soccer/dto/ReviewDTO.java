package org.zerock.soccer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO{
    private Long reviewnum;

    private Long mno;

    private Long mid;
    private int grade;
    private String nickname;

    private String email;

    private String text;

    private LocalDateTime regDate, modDate;
}

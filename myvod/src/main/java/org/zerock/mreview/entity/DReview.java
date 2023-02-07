package org.zerock.mreview.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"drama", "dmember"})
public class DReview extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewnum;
    @ManyToOne(fetch = FetchType.LAZY)
    private Drama drama;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private DMember dmember;
    private int grade;
    private String text;
    public void changeGrade(int grade){
        this.grade = grade;
    }
    public void changeText(String text){
        this.text=text;
    }
}

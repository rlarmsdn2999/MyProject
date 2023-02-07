package org.zerock.mreview.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = {"culture","cmember"})
public class CReview extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewnum;

    @ManyToOne(fetch = FetchType.LAZY)
    private Culture culture;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private CMember cmember;

    private int grade;
    private String text;
    public void changeGrade(int grade){
        this.grade = grade;
    }
    public void changeText(String text){
        this.text=text;
    }
}

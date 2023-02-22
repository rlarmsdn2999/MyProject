package org.zerock.soccer.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"player","playermember"})
@Getter
public class Review extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewnum;

    @ManyToOne(fetch = FetchType.LAZY)
    private Player player;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PlayerMember playerMember;

    private String text;
    private int grade;
    public void changeGrade(int grade){
        this.grade = grade;
    }
    public void changeText(String text) {
        this.text = text;
    }
}

package org.zerock.soccer.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Table(name="athlete")
public class Player extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;
    private String name;
    private String contury;
    private String team;
    private String history;
    public void changeTeam(String team){
        this.team=team;
    }
    public void changeHistory(String history){
        this.history=history;
    }
}

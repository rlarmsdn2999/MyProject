package org.zerock.mreview.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Drama extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dno;
    private String title;
    private String actor;
    private String content;
    public void changeTitle(String title){
        this.title=title;
    }
    public void changeActor(String actor){
        this.actor=actor;
    }
    public void changeContent(String content){
        this.content=content;
    }

}

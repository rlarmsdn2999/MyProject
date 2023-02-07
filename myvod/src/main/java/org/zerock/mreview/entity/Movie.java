package org.zerock.mreview.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Movie extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;
    private String title;
    private String content;
    private String actor;

    public void changeTitle(String title){
        this.title = title;
    }
    public void changeContent(String content){
        this.content=content;
    }
    public void changeActor(String actor){
        this.actor = actor;
    }
}

package com.example.projectx.dto;

import com.example.projectx.domain.Article;
import com.example.projectx.domain.Member;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApplicantsPageDTO {

    public Long id;
    public Member writer;
    public String title;
    public String description;
    public String photo;
    public String position;
    public String endTime;

    public List<Member> applicants;
    public ApplicantsPageDTO(Article article, List<Member> memberList) {
        this.id = article.getId();
        this.writer = article.getWriter();
        this.title = article.getTitle();
        this.description = article.getDescription();
        this.photo = article.getImage();
        this.position = article.getPosition();
        this.endTime = article.getEndTime();
        applicants = memberList;
    }
}

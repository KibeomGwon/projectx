package com.example.projectx.dto;

import com.example.projectx.domain.Article;
import com.example.projectx.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateArticleDTO {
    private String title;
    private String description;
    private String position;
    private String endTime;
    private String name;
    private String phoneNumber;
    @Builder
    public CreateArticleDTO(String title, String description, String position, String endTime,
                            String name, String phoneNumber) {
        this.title = title;
        this.description = description;
        this.position = position;
        this.endTime = endTime;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Article toArticleEntity(String photo, Member member) {
        Article article = new Article();
        article.setDescription(this.description);
        article.setImage(photo);
        article.setTitle(this.title);
        article.setPosition(this.position);
        article.setEndTime(this.endTime);
        article.setWriter(member);
        return article;
    }

    public Member toMemberEntity() {
        Member member = new Member();
        member.setName(this.name);
        member.setPhoneNumber(this.phoneNumber);
        return member;
    }
}

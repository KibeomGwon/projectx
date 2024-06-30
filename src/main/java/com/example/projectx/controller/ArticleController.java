package com.example.projectx.controller;

import com.example.projectx.domain.Article;
import com.example.projectx.domain.Member;
import com.example.projectx.service.*;
import com.example.projectx.dto.CreateArticleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ArticleController {

    private final ArticleService articleService;

    private final MemberService memberService;
    private final S3ImageService imageService;
    @Autowired
    public ArticleController(ArticleService articleService, MemberService memberService, S3ImageService imageService) {
        this.articleService = articleService;
        this.memberService = memberService;
        this.imageService = imageService;
    }

    @GetMapping("/")
    public List<Article> list() {
        List<Article> list = articleService.findAll();
        return list;
    }

    @PostMapping("/new")
    public Article create(@RequestPart(value = "title") String title,
                          @RequestPart(value = "description") String description,
                          @RequestPart(value = "position") String position,
                          @RequestPart(value = "deadline") String deadline,
                          @RequestPart(value = "name") String name,
                          @RequestPart(value = "phoneNumber") String phoneNumber,
                          @RequestPart(value = "image") MultipartFile image
                          ) {
        CreateArticleDTO dto = CreateArticleDTO.builder().title(title)
                .description(description)
                .position(position)
                .deadline(deadline)
                .name(name)
                .phoneNumber(phoneNumber).build();

        Member member = memberService.findByPhoneNumber(dto.toMemberEntity().getPhoneNumber())
                .orElseGet(() -> memberService.save(dto.toMemberEntity()));
        String imageUrl = imageService.saveFile(image);
        Article article = articleService.save(dto.toArticleEntity(imageUrl, member));
        return article;
    }

    @GetMapping("/{id}")
    public Article findById(@PathVariable Long id) {
        Article article = articleService.findById(id).orElseThrow();
        return article;
    }

}

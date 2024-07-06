package com.example.projectx.controller;

import com.example.projectx.domain.Article;
import com.example.projectx.domain.Member;
import com.example.projectx.service.*;
import com.example.projectx.dto.CreateArticleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
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

    // 게시물 전체 조회.
    @GetMapping("/")
    public String list(Model model) {
        List<Article> list = articleService.findAll();
        model.addAttribute("posts", list);
        return "mainPage";
    }

    // 게시물 상세 조회.
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        Article article = articleService.findById(id).orElseThrow();
        model.addAttribute("article", article);
        return "getPost";
    }

    //게시물 생성 페이지로 이동
    @GetMapping("/new")
    public String showCreateForm() {
        return "newPost"; // createArticle.html로 이동
    }

    // 게시물 생성-=> 백엔드 로직
    @PostMapping("/new")
    public String create(@RequestPart(value = "title") String title,
                         @RequestPart(value = "description") String description,
                         @RequestPart(value = "position") String position,
                         @RequestPart(value = "deadline") String deadline,
                         @RequestPart(value = "name") String name,
                         @RequestPart(value = "phoneNumber") String phoneNumber,
                         @RequestPart(value = "image") MultipartFile image,
                         Model model) {
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

        model.addAttribute("article", article);
        return "redirect:/"; // 생성 후 메인 페이지로 리디렉션
    }

    //게시물 업데이트 -> 프론트엔드
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Article article = articleService.findById(id).orElseThrow();
        model.addAttribute("article", article);
        return "updatePost"; // updateArticle.html로 이동
    }

    // 게시물 업데이트 => 백엔드 로직
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @RequestPart(value = "title") String title,
                         @RequestPart(value = "description") String description,
                         @RequestPart(value = "position") String position,
                         @RequestPart(value = "deadline") String deadline,
                         @RequestPart(value = "name") String name,
                         @RequestPart(value = "phoneNumber") String phoneNumber,
                         @RequestPart(value = "image", required = false) MultipartFile image,
                         Model model) {
        Article target = articleService.findById(id).orElseThrow();
        target.setTitle(title);
        target.setDescription(description);
        target.setPosition(position);
        target.setDeadline(deadline);
        target.getWriter().setName(name);
        target.getWriter().setPhoneNumber(phoneNumber);

        if (image != null && !image.isEmpty()) {
            String imageUrl = imageService.saveFile(image);
            target.setImage(imageUrl);
        }

        Article updatedArticle = articleService.save(target);
        model.addAttribute("article", updatedArticle);
        return "redirect:/"; // 업데이트 후 메인 페이지로 리디렉션
    }

    // 게시물 삭제.
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        articleService.deleteById(id);
        return "redirect:/";
    }

}

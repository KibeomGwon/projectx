package com.example.projectx.controller;

import com.example.projectx.domain.Article;
import com.example.projectx.domain.Member;
import com.example.projectx.dto.ApplicantsPageDTO;
import com.example.projectx.dto.MemberDTO;
import com.example.projectx.dto.MessageDTO;
import com.example.projectx.service.ArticleService;
import com.example.projectx.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ApplyController {
    private final ArticleService articleService;
    private final MemberService memberService;

    @Autowired
    public ApplyController(ArticleService articleService, MemberService memberService) {
        this.articleService = articleService;
        this.memberService = memberService;
    }

    @PostMapping("/{id}/apply")
    public String apply(@PathVariable("id") Long id,
                         @RequestParam(value = "applyName") String name,
                         @RequestParam(value = "applyPhoneNumber") String phoneNumber
    ) {

        MemberDTO dto = MemberDTO.builder()
                .name(name)
                .phoneNumber(phoneNumber).build();

        Article article = articleService.findById(id).orElseThrow(RuntimeException::new);


        Member member = memberService.findByPhoneNumber(dto.getPhoneNumber())
                .orElseGet(()-> memberService.save(dto.toEntity()));

        try{
            articleService.apply(article, member);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "redirect:/" + id.toString();
    }

    @GetMapping("/{id}/get-applicants-page")
    public String getApplicants(@PathVariable("id") Long id, Model model) {
        Article article = articleService.findById(id).orElseThrow();

        model.addAttribute("article", article);

        return "getApplicantPost";
    }

    @PostMapping("/{id}/get-applicants")
    public String getApplicants(@PathVariable("id") Long id, @RequestParam(value = "phoneNumber") String phoneNumber) {
        Article article = articleService.findById(id).orElseThrow();

        if(!article.getWriter().getPhoneNumber().equals(phoneNumber))
            return "redirect:/" + id.toString();

        return "redirect:/" + id.toString() + "/get-applicants";
    }

    @GetMapping("/{id}/get-applicants")
    public String getApplicantsPage(@PathVariable("id") Long id, Model model) {
        Article article = articleService.findById(id).orElseThrow();

        List<Member> applicants = articleService.getApplicants(article);

        model.addAttribute("article", article);
        model.addAttribute("applicants", applicants);

        return "applicants";
    }
}

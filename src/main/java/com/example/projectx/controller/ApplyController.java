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

    @ResponseBody
    @PostMapping("/{id}/get-applicants")
    public ApplicantsPageDTO getApplicants(@PathVariable("id") Long id, @RequestBody MemberDTO memberDTO) {
        Article article = articleService.findById(id).orElseThrow();

        if(!article.getWriter().getPhoneNumber().equals(memberDTO.getPhoneNumber()))
            throw new IllegalStateException("작성자 정보와 일치하지 않습니다.");

        List<Member> applicantsInfor = new ArrayList<>();

        if(article.getApplicants() != null && !article.getApplicants().isEmpty()) {
            for(Long memberId : article.getApplicants()) {
                applicantsInfor.add(memberService.findById(memberId).get());
            }
        }

        return new ApplicantsPageDTO(article, applicantsInfor);
    }
}

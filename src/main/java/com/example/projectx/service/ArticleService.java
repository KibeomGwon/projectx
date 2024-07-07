package com.example.projectx.service;

import com.example.projectx.domain.Article;
import com.example.projectx.domain.Member;
import com.example.projectx.dto.ApplicantsPageDTO;
import com.example.projectx.dto.MessageDTO;
import com.example.projectx.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

    public Article save(Article article) {
        return articleRepository.save(article);
    }

    public Article apply(Article article, Member member) throws Exception{
        Optional.ofNullable(article.getApplicants()).ifPresentOrElse((x) -> {},
                () -> {article.setApplicants(new ArrayList<>());});

//        if(article.getApplicants() == null)
//            article.setApplicants(new ArrayList<>());

        if(article.getApplicants().contains(member.getId()))
            throw new IllegalStateException("이미 신청한 유저입니다.");

        article.getApplicants().add(member.getId());

        return articleRepository.save(article);
    }

    public void deleteById(Long id) {
        articleRepository.findById(id).ifPresent(articleRepository::delete);
    }
}

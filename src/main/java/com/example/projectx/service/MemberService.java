package com.example.projectx.service;

import com.example.projectx.domain.Member;
import com.example.projectx.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public Member save(Member member) {
        validationCheck(member);
        return memberRepository.save(member);
    }

    public Optional<Member> findByPhoneNumber(String phoneNumber) {
        return memberRepository.findByPhoneNumber(phoneNumber);
    }

    private void validationCheck(Member member) {
        memberRepository.findByPhoneNumber(member.getPhoneNumber()).ifPresent(m-> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }
}

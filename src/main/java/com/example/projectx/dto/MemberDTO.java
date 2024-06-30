package com.example.projectx.dto;

import com.example.projectx.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
    private String name;
    private String phoneNumber;

    public Member toEntity() {
        Member member = new Member();
        member.setName(name);
        member.setPhoneNumber(phoneNumber);
        return member;
    }
}

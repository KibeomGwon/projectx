package com.example.projectx.dto;

import com.example.projectx.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDTO {
    private String name;
    private String phoneNumber;
    @Builder
    public MemberDTO(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Member toEntity() {
        Member member = new Member();
        member.setName(name);
        member.setPhoneNumber(phoneNumber);
        return member;
    }
}

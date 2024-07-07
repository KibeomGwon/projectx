package com.example.projectx.dto;

import com.example.projectx.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class MessageDTO {
    private String message;
    private String name;
    private String phoneNumber;

    public MessageDTO(String message, Member member) {
        this.message = message;
        this.name = member.getName();
        this.phoneNumber = member.getPhoneNumber();
    }
}

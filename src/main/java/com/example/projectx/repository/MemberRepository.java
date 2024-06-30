package com.example.projectx.repository;

import com.example.projectx.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public Optional<Member> findByPhoneNumber(String phoneNumber);
}

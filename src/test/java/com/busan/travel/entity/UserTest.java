package com.busan.travel.entity;

import com.busan.travel.page.entity.Member;
import com.busan.travel.page.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserTest {

    @Autowired
    private MemberRepository memberRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @WithMockUser(username = "2@1", roles = "USER")
    public void auditingTest(){
        Member user =new Member();

        memberRepository.save(user);

        entityManager.flush();
        entityManager.clear();

    }
}
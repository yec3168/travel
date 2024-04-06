package com.busan.travel.api.repository;

import com.busan.travel.api.dto.KakaoResponseDto;
import com.busan.travel.api.entity.Wish;
import com.busan.travel.page.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {

    List<Wish> findAllByMember(Member member);
    List<Wish> findByMemberAndUid(Member member, String uid);
}

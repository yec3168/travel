package com.busan.travel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class}) // Auditing 적용하는 어노테이션
@MappedSuperclass // 공통 매핑 정보가 필요시 사용하는 어노테이션
@Getter @Setter
public abstract class BaseTimeEntity {
    // 수정시간, 생성시간.

    @CreatedDate //엔티티가 생성되어 저장할때 자동으로 저장.
    @Column(updatable = false) //업데이트를 안함
    private LocalDateTime createDate;

    @LastModifiedDate // 변경할때 시간을 자동으로 저장.
    private LocalDateTime updateDate;
}

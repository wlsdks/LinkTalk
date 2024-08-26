package com.tony.linktalk.adapter.out.persistence.entity.post;

import com.tony.linktalk.adapter.out.persistence.entity.BaseEntity;
import com.tony.linktalk.adapter.out.persistence.entity.member.MemberEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Entity
@Table(name = "post")
public class PostEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PostMetadata metadata;  // 게시글 메타데이터 (제목, 공개 여부 등)

    @Column(name = "content", nullable = false)
    private String content;  // 게시글 내용

    @Embedded
    private PostSettings settings;  // 게시글 설정 (댓글 허용 여부 등)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;  // 작성자 정보

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof PostEntity that)) return false;
        return this.id != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

}

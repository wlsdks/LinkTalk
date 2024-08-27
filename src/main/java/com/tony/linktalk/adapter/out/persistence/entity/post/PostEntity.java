package com.tony.linktalk.adapter.out.persistence.entity.post;

import com.tony.linktalk.adapter.out.persistence.entity.BaseEntity;
import com.tony.linktalk.adapter.out.persistence.entity.constant.post.Visibility;
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

    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    private Visibility visibility = Visibility.PUBLIC;

    @Column(name = "status")
    private String status = "active";

    @Column(name = "is_pinned")
    private Boolean isPinned = false;

    @Column(name = "content", nullable = false)
    private String content;  // 게시글 내용

    @Column(name = "allow_comments")
    private Boolean allowComments = true;

    @Column(name = "is_shareable")
    private Boolean isShareable = true;

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

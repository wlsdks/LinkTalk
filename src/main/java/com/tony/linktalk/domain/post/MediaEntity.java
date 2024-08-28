package com.tony.linktalk.domain.post;

import com.tony.linktalk.adapter.out.persistence.entity.BaseEntity;
import com.tony.linktalk.adapter.out.persistence.entity.constant.post.MediaType;
import com.tony.linktalk.adapter.out.persistence.entity.post.PostEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "media")
public class MediaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", nullable = false)
    private String url; // 미디어 파일의 URL

    @Enumerated(EnumType.STRING)
    @Column(name = "media_type", nullable = false)
    private MediaType mediaType; // 이미지, 비디오 등의 미디어 타입

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity postEntity; // 해당 미디어가 첨부된 게시글

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MediaEntity that)) return false;
        return this.id != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

}

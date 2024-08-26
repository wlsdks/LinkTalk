package com.tony.linktalk.adapter.out.persistence.entity.post;

import com.tony.linktalk.adapter.out.persistence.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Entity
@Table(name = "hashtag")
public class HashtagEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tag", nullable = false, unique = true)
    private String tag; // 해시태그 이름

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HashtagEntity that)) return false;
        return this.id != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

}

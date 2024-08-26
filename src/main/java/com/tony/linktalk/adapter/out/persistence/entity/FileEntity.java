package com.tony.linktalk.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Entity
@Table(name = "file")
public class FileEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", nullable = false)
    private String url; // 파일에 접근할 수 있는 URL

    @Column(name = "path", nullable = false)
    private String path; // 파일이 저장된 서버의 실제 경로

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Column(name = "original_filename", nullable = false)
    private String originalFilename;

    @Column(name = "extension", nullable = false)
    private String extension;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "uploaded_at", nullable = false)
    private LocalDateTime uploadedAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_message_id")
    private ChatMessageEntity chatMessageEntity;

    @PrePersist
    public void prePersist() {
        this.uploadedAt = LocalDateTime.now();
        this.isDeleted = false;
    }

    public void markAsDeleted() {
        this.isDeleted = true;
    }

}

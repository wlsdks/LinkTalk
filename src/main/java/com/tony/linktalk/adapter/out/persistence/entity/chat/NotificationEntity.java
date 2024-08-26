package com.tony.linktalk.adapter.out.persistence.entity.chat;

import com.tony.linktalk.adapter.out.persistence.entity.constant.NotificationType;
import com.tony.linktalk.adapter.out.persistence.entity.member.MemberEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Entity
@Table(name = "notification")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity recipient; // 알림을 받을 사용자

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type", nullable = false)
    private NotificationType notificationType; // 알림의 유형

    @Column(name = "message", nullable = false)
    private String message; // 알림 내용

    @Column(name = "is_read", nullable = false)
    private boolean isRead; // 알림이 읽혔는지 여부

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 알림 생성 시간

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoomEntity chatRoomEntity; // 관련된 채팅방 (선택 사항)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_message_id")
    private ChatMessageEntity chatMessageEntity; // 관련된 메시지 (선택 사항)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationEntity that)) return false;
        return this.id != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.isRead = false; // 기본적으로 알림은 생성 시 읽지 않은 상태로 설정
    }

    public void markAsRead() {
        this.isRead = true; // 알림을 읽음으로 표시하는 메서드
    }

}

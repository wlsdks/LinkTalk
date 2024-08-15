package com.tony.linktalk.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "chat_room")
public class ChatRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @JoinColumn(name = "creator_id")
    private Long creatorId;

    @JoinColumn(name = "sender_id")
    private Long senderId;

}

package com.tony.linktalk.domain;

import com.tony.linktalk.adapter.out.persistence.entity.constant.room.RoomType;
import lombok.*;
import org.springframework.util.ObjectUtils;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class ChatRoom {

    private Long chatRoomId;
    private String name;
    private Long creatorId; // 생성자 Member ID
    private Long invitedMemberId;  // 채팅방에 초대된 Member ID
    private RoomType roomType;


    // factory method
    public static ChatRoom of(String name, Long creatorId) {
        return ChatRoom.builder()
                .name(name)
                .creatorId(creatorId)
                .build();
    }


    /**
     * @param creatorId 생성자 ID
     * @apiNote 채팅방 생성자 ID를 변경한다.
     */
    public void changeCreatorId(long creatorId) {
        if (ObjectUtils.isEmpty(creatorId)) {
            throw new IllegalArgumentException("creatorId is empty");
        }
        this.creatorId = creatorId;
    }

}

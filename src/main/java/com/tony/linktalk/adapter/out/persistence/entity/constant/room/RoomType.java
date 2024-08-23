package com.tony.linktalk.adapter.out.persistence.entity.constant.room;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoomType {

    PUBLIC("PUBLIC", "Public Room"),
    PRIVATE("PRIVATE", "Private Room"),
    DM("DM", "Direct Message"),
    GROUP("GROUP", "Group Room");

    private final String code;
    private final String description;

}

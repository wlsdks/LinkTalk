package com.tony.linktalk.adapter.in.web.dto.response.file;

import com.tony.linktalk.domain.ChatMessage;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FileResponseDto {

    private Long id;
    private String url;
    private String path;
    private String fileType;
    private String originalFilename;
    private String extension;
    private Long size;
    private LocalDateTime uploadedAt;
    private boolean isDeleted;
    private ChatMessage chatMessage;

}

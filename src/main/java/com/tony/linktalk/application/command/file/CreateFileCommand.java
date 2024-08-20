package com.tony.linktalk.application.command.file;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class CreateFileCommand {

    private MultipartFile file;
    private Long chatMessageId;

    // factory method
    public static CreateFileCommand of(MultipartFile file) {
        return CreateFileCommand.builder()
                .file(file)
                .build();
    }

}

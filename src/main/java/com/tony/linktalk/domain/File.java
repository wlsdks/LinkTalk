package com.tony.linktalk.domain;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 파일 정보를 담는 domain 객체
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
public class File {

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

    // factory method
    public static File of(String url) {
        return File.builder()
                .url(url)
                .build();
    }


    // factory method
    public static File of(String fileName, String filePath, String extension) {
        return File.builder()
                .originalFilename(fileName)
                .path(filePath)
                .extension(extension)
                .build();
    }


    // factory method
    public static File of(String fileUrl, String filePath, String fileType, String originalFilename, String extension, long fileSize, LocalDateTime now, boolean isDeleted) {
        return File.builder()
                .url(fileUrl)
                .path(filePath)
                .fileType(fileType)
                .originalFilename(originalFilename)
                .extension(extension)
                .size(fileSize)
                .uploadedAt(now)
                .isDeleted(isDeleted)
                .build();
    }


    /**
     * @return 삭제 처리된 파일 정보
     * @apiNote 파일 정보를 삭제 처리
     */
    public boolean exists() {
        return this.id != null;
    }


    /**
     * @apiNote 파일 정보를 삭제 처리
     */
    public void delete() {
        this.isDeleted = true;
    }


    /**
     * @apiNote 파일 정보를 삭제 처리
     */
    public void markAsDeleted() {
        this.isDeleted = true;
    }

}

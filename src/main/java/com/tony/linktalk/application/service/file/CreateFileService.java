package com.tony.linktalk.application.service.file;

import com.tony.linktalk.adapter.in.web.dto.response.file.FileResponseDto;
import com.tony.linktalk.application.command.file.CreateFileCommand;
import com.tony.linktalk.application.port.in.file.CreateFileUseCase;
import com.tony.linktalk.application.port.out.file.CreateFilePort;
import com.tony.linktalk.application.port.out.file.FindFilePort;
import com.tony.linktalk.application.port.out.file.UpdateFilePort;
import com.tony.linktalk.domain.File;
import com.tony.linktalk.exception.ErrorCode;
import com.tony.linktalk.exception.LinkTalkException;
import com.tony.linktalk.mapper.FileMapper;
import com.tony.linktalk.util.custom.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@UseCase
public class CreateFileService implements CreateFileUseCase {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.url-prefix}")
    private String urlPrefix; // 파일 URL의 prefix를 설정 (예: https://yourdomain.com/files)

    private final CreateFilePort createFilePort;
    private final FindFilePort findFilePort;
    private final UpdateFilePort updateFilePort;
    private final FileMapper fileMapper;

    /**
     * @param command 파일 생성 커맨드 객체
     * @return 저장된 파일
     * @apiNote 파일을 저장합니다.
     */
    @Transactional
    @Override
    public FileResponseDto createFile(CreateFileCommand command) {
        // 파일 정보 추출
        MultipartFile multipartFile = command.getFile();
        String originalFilename = multipartFile.getOriginalFilename();
        String fileName = UUID.randomUUID() + "_" + originalFilename;
        String filePath = Paths.get(uploadDir, fileName).toString();
        String fileUrl = urlPrefix + "/" + fileName;
        String extension = getExtension(originalFilename);
        String fileType = multipartFile.getContentType();
        long fileSize = multipartFile.getSize();

        // 이미지 파일만 저장가능 (추후 고도화)
        validateImageFile(multipartFile);

        // 실제 파일 저장 ( 파일 저장 경로: uploadDir + fileName )
        try {
            multipartFile.transferTo(Paths.get(filePath).toFile());
        } catch (IOException e) {
            throw new LinkTalkException(ErrorCode.FILE_UPLOAD_ERROR, e);
        }

        // 파일을 db에 저장
        File file = File.of(fileUrl, filePath, fileType, originalFilename, extension, fileSize, LocalDateTime.now(), false);
        File savedFile = createFilePort.createFile(file);

        // 파일 저장 후 응답 DTO로 변환
        return fileMapper.domainToResponseDto(savedFile);
    }


    /**
     * @param fileId 파일 ID
     * @apiNote 파일을 삭제합니다.
     */
    @Transactional
    @Override
    public void deleteFile(Long fileId) {
        File findFile = findFilePort.findById(fileId);
        findFile.markAsDeleted();
        File modifiedFile = updateFilePort.updateFile(findFile);
    }


    /**
     * @param filename 파일 이름
     * @return 파일 확장자
     * @apiNote 파일 이름에서 확장자를 추출합니다.
     */
    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }


    /**
     * @param file MultipartFile
     * @apiNote 이미지 파일인지 검증합니다.
     */
    private void validateImageFile(MultipartFile file) {
        String fileType = file.getContentType();

        // 파일 타입이 image가 아니면 예외 발생
        assert fileType != null;
        if (!fileType.startsWith("image/")) {
            throw new LinkTalkException(ErrorCode.INVALID_FILE_TYPE);
        }
    }

}

package com.tony.linktalk.adapter.in.web;

import com.tony.linktalk.adapter.in.web.dto.api.ApiResponse;
import com.tony.linktalk.adapter.in.web.dto.response.file.FileResponseDto;
import com.tony.linktalk.application.command.file.CreateFileCommand;
import com.tony.linktalk.application.port.in.file.CreateFileUseCase;
import com.tony.linktalk.config.websocket.ChatWebSocketHandler;
import com.tony.linktalk.exception.ErrorCode;
import com.tony.linktalk.exception.LinkTalkException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

@RequestMapping("/files")
@RequiredArgsConstructor
@RestController
public class FileController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final CreateFileUseCase createFileUseCase;
    private final ChatWebSocketHandler chatWebSocketHandler;

    /**
     * @param file       업로드할 파일
     * @param chatRoomId 채팅방 ID
     * @return ResponseEntity<ApiResponse < FileResponseDto>>
     * @throws Exception Exception
     * @apiNote 프론트엔드에서는 파일 업로드를 위해 별도의 HTTP 요청을 보내고, 해당 요청이 성공적으로 처리된 후 웹소켓을 통해 파일 URL을 수신하게 된다.
     * 이 과정은 일반적인 파일 업로드-알림 구조와 비슷하다. 예를 들어, 다음과 같은 순서로 처리할 수 있다.
     * 1. 파일 업로드: 파일을 FileController에 업로드.
     * 2. 파일 URL 수신: 업로드 완료 후, 서버에서 웹소켓을 통해 URL 전송.
     * 3. 클라이언트 처리: 클라이언트는 수신한 URL을 사용해 파일 링크 또는 미리보기를 사용자 인터페이스에 표시.
     */
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<FileResponseDto>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("chatRoomId") Long chatRoomId,
            @RequestParam("userId") Long userId // todo: 이거 가능한건지 확인
    ) throws Exception {
        // 파일 업로드 처리
        CreateFileCommand command = CreateFileCommand.of(file);
        FileResponseDto responseDto = createFileUseCase.createFile(command);

        // 업로드된 파일 URL을 채팅방에 반환 (파일 업로드 후 해당 채팅방에 속한 모든 사용자에게 메시지를 전송할 수 있다.)
        String fileUrlMessage = "File uploaded successfully. URL: " + responseDto.getUrl();
        chatWebSocketHandler.sendMessageToReceiver(chatRoomId, fileUrlMessage, userId);

        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }


    /**
     * @param filename 파일 이름
     * @return ResponseEntity<Resource>
     * @apiNote 파일 다운로드 API
     */
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        try {
            // 설정 파일에서 업로드 디렉토리 경로를 가져옴
            Path file = Paths.get(uploadDir).resolve(filename);
            UrlResource urlResource = new UrlResource(file.toUri());

            // 파일이 존재하고 읽을 수 있는 경우 파일을 다운로드
            if (urlResource.exists() && urlResource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + urlResource.getFilename() + "\"")
                        .body(urlResource);
            } else {
                throw new LinkTalkException(ErrorCode.FILE_NOT_FOUND);
            }
        } catch (Exception e) {
            throw new LinkTalkException(ErrorCode.FILE_DOWNLOAD_ERROR, e);
        }
    }

}

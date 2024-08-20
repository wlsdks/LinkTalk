package com.tony.linktalk.application.port.in.file;

import com.tony.linktalk.adapter.in.web.dto.response.file.FileResponseDto;
import com.tony.linktalk.application.command.file.CreateFileCommand;

public interface CreateFileUseCase {

    FileResponseDto createFile(CreateFileCommand command);

    void deleteFile(Long fileId);

}

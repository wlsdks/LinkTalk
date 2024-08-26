package com.tony.linktalk.mapper;

import com.tony.linktalk.adapter.in.web.dto.response.file.FileResponseDto;
import com.tony.linktalk.adapter.out.persistence.entity.chat.FileEntity;
import com.tony.linktalk.domain.File;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface FileMapper {

    FileResponseDto domainToResponseDto(File savedFile);

    FileEntity domainToEntity(File file);

    File entityToDomain(FileEntity savedEntity);

}

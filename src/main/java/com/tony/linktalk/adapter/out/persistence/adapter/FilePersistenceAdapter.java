package com.tony.linktalk.adapter.out.persistence.adapter;

import com.tony.linktalk.adapter.out.persistence.entity.FileEntity;
import com.tony.linktalk.adapter.out.persistence.repository.FileRepository;
import com.tony.linktalk.application.port.out.file.CreateFilePort;
import com.tony.linktalk.application.port.out.file.FindFilePort;
import com.tony.linktalk.application.port.out.file.UpdateFilePort;
import com.tony.linktalk.domain.File;
import com.tony.linktalk.mapper.FileMapper;
import com.tony.linktalk.util.custom.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class FilePersistenceAdapter implements CreateFilePort, FindFilePort, UpdateFilePort {

    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    /**
     * @param file 파일 객체
     * @return 저장된 파일
     * @apiNote 파일을 저장합니다.
     */
    @Override
    public File createFile(File file) {
        FileEntity entity = fileMapper.domainToEntity(file);
        FileEntity savedEntity = fileRepository.save(entity);
        return fileMapper.entityToDomain(savedEntity);
    }


    /**
     * @param fileId 파일 ID
     * @return 파일
     * @apiNote 파일을 조회합니다.
     */
    @Override
    public File findById(Long fileId) {
        FileEntity entity = fileRepository.findById(fileId).orElse(null);
        return fileMapper.entityToDomain(entity);
    }


    /**
     * @param file 수정할 파일
     * @return 수정된 파일
     * @apiNote 파일을 수정합니다.
     */
    @Override
    public File updateFile(File file) {
        FileEntity entity = fileMapper.domainToEntity(file);
        FileEntity savedEntity = fileRepository.save(entity);
        return fileMapper.entityToDomain(savedEntity);
    }

}

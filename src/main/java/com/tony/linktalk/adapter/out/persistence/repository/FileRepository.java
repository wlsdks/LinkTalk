package com.tony.linktalk.adapter.out.persistence.repository;

import com.tony.linktalk.adapter.out.persistence.entity.chat.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}

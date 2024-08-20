package com.tony.linktalk.application.port.out.file;

import com.tony.linktalk.domain.File;

public interface FindFilePort {

    File findById(Long fileId);

}

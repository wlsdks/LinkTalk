package com.tony.linktalk.application.service.post;

import com.tony.linktalk.application.port.in.post.FindPostUseCase;
import com.tony.linktalk.util.custom.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@UseCase
public class FindPostService implements FindPostUseCase {
}

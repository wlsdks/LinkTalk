package com.tony.linktalk.adapter.out.persistence.adapter;

import com.tony.linktalk.application.port.out.post.CreatePostPort;
import com.tony.linktalk.application.port.out.post.DeletePostPort;
import com.tony.linktalk.application.port.out.post.FindPostPort;
import com.tony.linktalk.application.port.out.post.UpdatePostPort;
import com.tony.linktalk.util.custom.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
public class PostPersistenceAdapter implements CreatePostPort, FindPostPort, UpdatePostPort, DeletePostPort {



}

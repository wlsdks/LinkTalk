package com.tony.linktalk.application.service.post;

import com.tony.linktalk.adapter.in.web.dto.response.post.PostResponseDto;
import com.tony.linktalk.application.port.out.post.UpdatePostPort;
import com.tony.linktalk.mapper.PostMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UpdatePostServiceTest {

    @Autowired
    private UpdatePostPort updatePostPort;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UpdatePostService sut;

    @DisplayName("[happy] 게시글 수정 성공")
    @Test
    void updatePost() {
        //given

        //when
        PostResponseDto responseDto = sut.updatePost(null);

        //then

    }

}
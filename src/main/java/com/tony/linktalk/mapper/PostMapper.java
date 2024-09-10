package com.tony.linktalk.mapper;

import com.tony.linktalk.adapter.in.web.dto.response.post.PostResponseDto;
import com.tony.linktalk.adapter.out.persistence.entity.post.PostEntity;
import com.tony.linktalk.application.command.post.CreatePostCommand;
import com.tony.linktalk.application.command.post.UpdatePostCommand;
import com.tony.linktalk.domain.post.Post;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PostMapper {

    Post entityToDomain(PostEntity postEntity);

    PostResponseDto domainToResponseDto(Post post);

    PostEntity domainToEntity(Post post);

    Post commandToDomain(CreatePostCommand command);

    Post commandToDomain(UpdatePostCommand command);

}

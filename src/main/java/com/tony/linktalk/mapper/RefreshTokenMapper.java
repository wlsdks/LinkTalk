package com.tony.linktalk.mapper;

import com.tony.linktalk.adapter.out.persistence.entity.RefreshTokenEntity;
import com.tony.linktalk.domain.RefreshToken;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {MemberMapper.class} // memberMapper를 사용합니다.
)
public interface RefreshTokenMapper {

    @Mapping(target = "memberEntity", source = "member")
    RefreshTokenEntity domainToEntity(RefreshToken refreshToken);

    @Mapping(target = "member", source = "memberEntity")
    RefreshToken entityToDomain(RefreshTokenEntity refreshTokenEntity);

}

package com.tony.linktalk.mapper;

import com.tony.linktalk.adapter.in.web.dto.response.auth.JwtResponseDto;
import com.tony.linktalk.domain.Jwt;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JwtMapper {

    JwtResponseDto domainToResponseDTO(Jwt jwt);

}

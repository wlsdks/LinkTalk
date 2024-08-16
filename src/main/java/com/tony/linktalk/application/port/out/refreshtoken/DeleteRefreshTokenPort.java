package com.tony.linktalk.application.port.out.refreshtoken;


import com.tony.linktalk.domain.Member;

public interface DeleteRefreshTokenPort {

    Boolean deleteRefreshToken(Member member);

}

package com.tony.linktalk.application.port.out.refreshtoken;


import com.tony.linktalk.domain.RefreshToken;

public interface FindRefreshTokenPort {

    RefreshToken findRefreshToken(RefreshToken refreshToken);

}

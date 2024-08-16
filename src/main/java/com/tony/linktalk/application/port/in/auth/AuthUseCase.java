package com.tony.linktalk.application.port.in.auth;


import com.tony.linktalk.adapter.in.web.dto.response.auth.JwtResponseDto;
import com.tony.linktalk.adapter.in.web.dto.response.auth.MemberResponseDto;
import com.tony.linktalk.application.command.auth.token.ReIssueAccessTokenCommand;
import com.tony.linktalk.application.command.auth.SignInCommand;
import com.tony.linktalk.application.command.auth.SignOutCommand;
import com.tony.linktalk.application.command.auth.SignUpCommand;

/**
 * useCase에서는 Command를 받아서 도메인을 반환한다.
 */
public interface AuthUseCase {

    JwtResponseDto signInAndPublishJwt(SignInCommand signInCommand);

    MemberResponseDto signUp(SignUpCommand signUpCommand);

    Long signOut(SignOutCommand signOutCommand);

    JwtResponseDto reIssueAccessToken(ReIssueAccessTokenCommand reIssueAccessTokenCommand);

}

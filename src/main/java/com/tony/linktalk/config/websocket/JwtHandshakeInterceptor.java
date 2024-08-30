package com.tony.linktalk.config.websocket;

import com.tony.linktalk.util.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    // todo: 클라인언트 예시: const socket = new WebSocket('ws://localhost:8080/chat?token=your-jwt-token&chatRoomId=12345');

    /**
     * @param request    WebSocket 연결 요청
     * @param response   WebSocket 연결 응답
     * @param wsHandler  WebSocket 핸들러
     * @param attributes WebSocket 연결이 성공적으로 이루어지면 WebSocketSession 객체의 속성 맵(getAttributes())으로 전달
     *                   즉, attributes 맵에 추가된 값들은 나중에 WebSocket 핸들러에서 WebSocketSession의 getAttributes() 메서드를 통해 접근할 수 있다.
     * @return JWT 토큰이 유효한 경우 true, 그렇지 않은 경우 false
     * @throws Exception 예외
     * @apiNote WebSocket 연결 전 쿼리 파라미터에서 JWT 토큰을 추출하여 유효성을 검사하고 속성에 필요한 사용자 정보를 추가
     */
    @Override
    public boolean beforeHandshake(
            @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response,
            @NonNull WebSocketHandler wsHandler,
            @NonNull Map<String, Object> attributes // WebSocketSession.getAttributes() 으로 접근 가능
    ) throws Exception {
        // WebSocket 연결 시, JWT 토큰을 추출하여 유효성 검사
        String query = request.getURI().getQuery();
        String token = UriComponentsBuilder.fromUriString("?" + query)
                .build()
                .getQueryParams()
                .getFirst("token");

        System.out.println("Extracted token: " + token);

        // JWT 토큰이 유효한 경우, 속성에 사용자 정보를 추가
        if (token != null && jwtTokenProvider.validateJwtToken(token)) {
            Claims claims = jwtTokenProvider.getClaimsFromJwtToken(token);
            Long memberId = jwtTokenProvider.getMemberIdFromJwtToken(token);
            String email = claims.getSubject();

            // 속성에 사용자 정보 추가
            attributes.put("email", email);
            attributes.put("memberId", memberId);
            attributes.put("nickname", claims.get("nickname", String.class));

            // 채팅방 ID가 존재하는 경우 속성에 추가
            String chatRoomId = UriComponentsBuilder.fromUriString("?" + query)
                    .build()
                    .getQueryParams()
                    .getFirst("chatRoomId");

            System.out.println("Extracted chatRoomId: " + chatRoomId);

            // 채팅방 ID가 존재하는 경우 속성에 추가
            if (chatRoomId != null) {
                attributes.put("chatRoomId", Long.parseLong(chatRoomId));
                System.out.println("Stored chatRoomId in attributes: " + attributes.get("chatRoomId"));
            }

            System.out.println("attributes: " + attributes);
            return true;
        }

        // JWT가 유효하지 않으면 핸드셰이크 거부
        return false;
    }


    /**
     * @param request   WebSocket 연결 요청
     * @param response  WebSocket 연결 응답
     * @param wsHandler WebSocket 핸들러
     * @param exception 예외
     * @apiNote WebSocket 연결 후 처리
     */
    @Override
    public void afterHandshake(
            @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response,
            @NonNull WebSocketHandler wsHandler,
            Exception exception
    ) {
        // Do nothing
    }

}

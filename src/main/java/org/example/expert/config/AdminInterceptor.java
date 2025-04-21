package org.example.expert.config;

import java.time.LocalDateTime;

import org.springframework.web.servlet.HandlerInterceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * @packageName    : org.example.expert.config
 * @fileName       : AdminInterceptor
 * @author         : yong
 * @date           : 4/18/25
 * @description    :
 */
@RequiredArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {
	private final JwtUtil jwtUtil;

	/**
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String token = request.getHeader("Authorization");
		Claims claims = jwtUtil.extractClaims(token);
		String userRole = claims.get("userRole", String.class);
		System.out.println(claims);
		if (token == null || userRole.equals("admin")) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 응답
			return false;
		}

		// 인증 성공 시 요청 시각과 URL 로깅
		System.out.println("[인증 성공] " + LocalDateTime.now() + " - " + request.getRequestURI());

		return true;
	}
	*/

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String token = request.getHeader("Authorization");

		if (token != null && token.startsWith("Bearer ")) {
			String accessToken = token.substring(7);

			if (jwtUtil.hasAdminRole(accessToken)) {
				System.out.println("[인증 성공] " + LocalDateTime.now() + " - " + request.getRequestURI());
			} else {
				System.out.println("[인증 실패] : admin 권한 없음!");
			}
		} else {
			System.out.println("Authorization 헤더 없거나 잘못된 요청");
		}

		return true;
	}
}

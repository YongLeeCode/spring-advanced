package org.example.expert.config;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @packageName    : org.example.expert.config
 * @fileName       : LogginAspect
 * @author         : yong
 * @date           : 4/21/25
 * @description    :
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogginAspect {
	private final HttpServletRequest req;

	@Pointcut("execution(* org.example.expert.domain.user.controller.*.changeUserRole(..))")
	public void pointCut() {}

	@Around("pointCut()")
	public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
		long startTime = System.currentTimeMillis();

		String uri = req.getRequestURI();
		String methodName = pjp.getSignature().getName();
		Object[] args = pjp.getArgs();

		// 요청 바디 읽기
		String requestBody = getRequestBody();

		log.info("📥 요청 URL: {}, 호출 메서드: {}, 인자: {}, 요청바디: {}",
			uri, methodName, Arrays.toString(args), requestBody);

		// 실제 메서드 실행
		Object result = pjp.proceed();

		// 응답 바디 읽기
		String responseBody = getResponseBody();

		long endTime = System.currentTimeMillis();

		log.info("📤 응답바디: {}, 처리시간: {}ms", responseBody, (endTime - startTime));

		return result;
	}

	private String getRequestBody() {
		ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) req;
		return new String(wrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
	}

	private String getResponseBody() {
		ContentCachingResponseWrapper wrapper = (ContentCachingResponseWrapper)
			((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
		return new String(wrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
	}


	@Before("pointCut()")
	public void logBefore(JoinPoint joinPoint) {
		System.out.println(joinPoint.getSignature().getName());
		Object[] args = joinPoint.getArgs();
		log.info("Entering method: {} with arguments: {}", joinPoint.getSignature().getName(), Arrays.toString(args));

		log.info("test 중");
		System.out.println("비포");
	}

	@After("pointCut()")
	public void logAfter(JoinPoint joinPoint) {
		System.out.println(joinPoint.getSignature().getName());
		System.out.println(joinPoint.getArgs());

		Object[] args = joinPoint.getArgs();
		log.info("Entering method: {} with arguments: {}", joinPoint.getSignature().getName(), Arrays.toString(args));
		System.out.println("애프터");
	}
}

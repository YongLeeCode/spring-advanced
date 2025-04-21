package org.example.expert.config;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @packageName    : org.example.expert.config
 * @fileName       : CachingRequestResponseFilter
 * @author         : yong
 * @date           : 4/21/25
 * @description    :
 */
@Component
public class CachingRequestResponseFilter implements Filter {
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws
		IOException,
		ServletException {
		ContentCachingRequestWrapper cachingRequest = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
		ContentCachingResponseWrapper cachingResponse = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

		filterChain.doFilter(cachingRequest, cachingResponse);

		cachingResponse.copyBodyToResponse(); // 응답 본문 원복
	}

}

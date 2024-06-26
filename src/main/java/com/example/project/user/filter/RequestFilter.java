package com.example.project.user.filter;

import com.example.project.user.dto.PartResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestFilter implements Filter {

    private static final String REQUEST_ID_HEADER = "RequestID";

    private final RequestIdCache requestIdCache;

    private final ObjectMapper objectMapper;

    public RequestFilter(RequestIdCache requestIdCache, ObjectMapper objectMapper) {
        this.requestIdCache = requestIdCache;
        this.objectMapper = objectMapper;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String requestId = httpServletRequest.getHeader(REQUEST_ID_HEADER);

        if (requestId != null && requestIdCache.isDuplicate(requestId)) {
            PartResponseDto cachedResponse = requestIdCache.getCachedResponse(requestId);
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(cachedResponse));
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No initialization needed
    }

    @Override
    public void destroy() {
        // No cleanup needed
    }
}

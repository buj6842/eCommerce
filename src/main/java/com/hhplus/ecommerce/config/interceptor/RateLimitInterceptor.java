package com.hhplus.ecommerce.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private static final long MAX_REQUESTS_PER_MINUTE = 60; // 분당 최대 60번 호출
    private Map<String, RequestInfo> requestCountsMap = new HashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userIp = request.getRemoteAddr(); //IP를 기준으로 제한

        long currentTime = System.currentTimeMillis();
        RequestInfo requestInfo = requestCountsMap.getOrDefault(userIp, new RequestInfo(0, currentTime));

        if (TimeUnit.MILLISECONDS.toMinutes(currentTime - requestInfo.lastRequestTime) >= 1) {
            requestInfo = new RequestInfo(0, currentTime);
        }

        if (requestInfo.requestCount >= MAX_REQUESTS_PER_MINUTE) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            return false;
        }

        // 요청 횟수 증가 및 시간 업데이트
        requestInfo.requestCount++;
        requestInfo.lastRequestTime = currentTime;
        requestCountsMap.put(userIp, requestInfo);

        return true; // 요청 진행
    }

    private static class RequestInfo {
        long requestCount;
        long lastRequestTime;

        public RequestInfo(long requestCount, long lastRequestTime) {
            this.requestCount = requestCount;
            this.lastRequestTime = lastRequestTime;
        }
    }
}

package io.basic.security.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    //사용자 로그인 성공 전 -> 요청정보 가짐
    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        setDefaultTargetUrl("/");

        //인증 전 사용자 정보 객체
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        //다른 자원에 접근했다가 온 경우 null 일수 있음
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            //targer url이 이전에 가고자 했던 url 이면
            redirectStrategy.sendRedirect(request, response, targetUrl);
        } else {
            //null이면
            redirectStrategy.sendRedirect(request, response, getDefaultTargetUrl());
        }

    }



}

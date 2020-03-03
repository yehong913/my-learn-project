package com.didispace.chapter25.ThreadTest.ThreadLocalExample;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class HttpFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest  httpServletRequest=(HttpServletRequest)request;
        RequestHolder.add(Thread.currentThread().getId());
        chain.doFilter(httpServletRequest,response);

    }

    @Override
    public void destroy() {

    }
}

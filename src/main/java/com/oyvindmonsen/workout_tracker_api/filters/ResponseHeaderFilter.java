package com.oyvindmonsen.workout_tracker_api.filters;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter()
public class ResponseHeaderFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader(
                "Access-Control-Allow-Credentials", "*");
        httpServletResponse.setHeader(
                "Access-Control-Allow-Origin", "*"
        );
        httpServletResponse.setHeader(
                "Access-Control-Allow-Headers", "*"
        );

        System.out.println("Applying filter");
        chain.doFilter(request, response);
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}

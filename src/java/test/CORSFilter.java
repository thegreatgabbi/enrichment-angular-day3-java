/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gabrielwong
 */
@WebFilter(urlPatterns = {"/*"}, dispatcherTypes = DispatcherType.REQUEST)
public class CORSFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        
        HttpServletResponse httpResp = (HttpServletResponse) response;
        
        httpResp.setHeader("Access-Control-Allow-Origin", "*");
        httpResp.setHeader("X-ProcessBy", "CORSFilter");
        
        // Pass on the request
        // Every thing before chain.doFilter is the incoming request
        chain.doFilter(request, response);
        // Everything after chain.doFilter is the outgoing response
        
        
        
    }
    
}

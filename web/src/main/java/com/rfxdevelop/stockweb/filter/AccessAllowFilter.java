package com.rfxdevelop.stockweb.filter;

import com.rfxdevelop.utils.copy.sql.SqlCheckUtils;
import com.rfxdevelop.utils.original.request.RequestParamUtil;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Order(0)
@WebFilter(filterName = "AccessAllowFilter",urlPatterns = {"/basic/*","/baseData/*","/simple/*"})
public class AccessAllowFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //接受跨域请求
        response.setHeader("Access-Control-Allow-Origin","*");
        Map<String, String> params = RequestParamUtil.getParams(servletRequest);
        Set<String> keySet = params.keySet();
        //SQL注入检查
        for(String key:keySet){
            String value = params.get(key);
            if(!SqlCheckUtils.isValid(value)){
                throw new ServletException("参数错误：key:"+key+" value:"+ value );
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}

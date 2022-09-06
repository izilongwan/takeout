package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;

import com.alibaba.fastjson.JSON;
import com.common.BaseContext;
import com.entity.R;

@WebFilter(urlPatterns = "/*", asyncSupported = true)
public class LoginFilter implements Filter {
	@Value("${key.employee}")
	String employeeKey;

	final static AntPathMatcher PATH_MATCHER = new AntPathMatcher();

	final static String[] urls = {
			"/employee/login",
			"/employee/logout",
			"/pages/**",
			"/doc.html",
			"/webjars/**",
			"/v2/api-docs",
			"/swagger-resources",
			"/sse/**",
	};

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String uri = ((HttpServletRequest) request).getRequestURI();
		long ts = System.currentTimeMillis();

		if (checkPath(urls, uri)) {
			chain.doFilter(request, response);
			return;
		}

		Object v = ((HttpServletRequest) request).getSession().getAttribute(employeeKey);

		if (v == null) {
			String json = JSON.toJSONString(R.ERROR("您未登录!", System.currentTimeMillis() - ts));

			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(json);
			return;
		}

		BaseContext.set(employeeKey, v);

		chain.doFilter(request, response);
	}

	final public boolean checkPath(String[] urls, String url) {
		for (String s : urls) {
			boolean rs = PATH_MATCHER.match(s, url);

			if (rs) {
				return rs;
			}
		}

		return false;
	}

}

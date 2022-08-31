package com.advice;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.entity.R;

@RestControllerAdvice
public class catchExceptionControllerAdvice {
	@Value("${share.timestamp}")
	String timestamp;

	@ExceptionHandler
	public R<Exception> catchException(Exception e, ServletRequest req) {
		e.printStackTrace();

		long ts = (long) req.getAttribute(timestamp);
		long timecost = System.currentTimeMillis() - ts;

		return R.ERROR(e, e.getMessage(), timecost);
	}
}

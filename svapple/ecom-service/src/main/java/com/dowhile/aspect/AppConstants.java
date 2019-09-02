package com.dowhile.aspect;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.StaticApplicationContext;

public class AppConstants {

	public static final ApplicationContext APP_CONTEXT = new ClassPathXmlApplicationContext(
			new String[] { "Spring-AOP.xml" });
	public static final StaticApplicationContext INNER_CONTEXT = new StaticApplicationContext(APP_CONTEXT);
	
}

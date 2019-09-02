/**
 * 
 */
package com.dowhile.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @author imran.latif
 *
 */
@Aspect
public class LoggingAspect {
	@Before("execution(* com.ews.advisoray.service.QuestionService.addQuestion(..))")
	public void logBefore(JoinPoint joinPoint) {
 
		System.out.println("logBefore() is running!");
		System.out.println("hijacked : " + joinPoint.getSignature().getName());
		System.out.println("******");
	}
	
	public static void main (String argv[]){
//		ApplicationContext appContext = new ClassPathXmlApplicationContext(
//				new String[] { "Spring-Question.xml" });
//		QuestionService questionService = (QuestionService) appContext.getBean("questionSe");
//		questionService.addQuestion();;
	}
}

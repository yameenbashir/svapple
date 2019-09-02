//package com.dowhile.aspect;
//
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//
////import com.ews.advisoray.model.AdActivityDetail;
////import com.ews.advisoray.service.impl.ActivityLoggingServiceImpl;
//
//@Aspect
//public class ActivityLoggingAspect {
//	
//	
////	@Before("execution(* com.ews.advisoray.service.LoginService.loadUserByEmailAndPassword*(..)) and args(email,password)")
//	public void logActivityDetailBefore(JoinPoint joinPoint, String email,
//			String password) {
//
//		AdActivityDetail activityDetail = AppConstants.INNER_CONTEXT
//				.getBean(AdActivityDetail.class);
//
//		System.out.println(activityDetail);
//
////		ActivityLoggingService service = (ActivityLoggingService) AppConstants.APP_CONTEXT
////				.getBean("activityLoggingService");
//
//// **** ****************
////		ApplicationContext appContext = new ClassPathXmlApplicationContext(
////				new String[] { "applicationContext.xml" });
//		
////		ActivityLoggingService service = (ActivityLoggingService) appContext.getBean("ActivityLoggingService");
////		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");  
////		ActivityLoggingService service = (ActivityLoggingService) ctx.getBean("ActivityLoggingService");
//		
////		ActivityLoggingServiceImpl service = (ActivityLoggingServiceImpl) AppConstants.APP_CONTEXT
////				.getBean("activityLoggingService");
////************************	
////		activityDetail = service.getActivityDetailLog();
//
////		service.saveActivityDetailLog(activityDetail);
//
//		System.out.println("hello");
//
//	}
//
//	
//	// @Before("execution(* com.ews.advisoray.service.LoginService.test*(..)) and args(userName,password)")
//
////	public static void main(String argv[]) {
////
////		AppConstants.INNER_CONTEXT.registerSingleton("beanName",
////				AdActivityDetail.class);
////		AdActivityDetail activityDetail = AppConstants.INNER_CONTEXT
////				.getBean(AdActivityDetail.class);
////		System.out.println(activityDetail);
////
////		// AdActivityDetail activityDetail = new AdActivityDetail();
////
////		LoginService lService = (LoginService) AppConstants.APP_CONTEXT
////				.getBean("lService");
////		lService.loadUserByEmailAndPassword("email", "password");
////
////	}
//
//}

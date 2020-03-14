/**
 * 
 */
package com.dowhile.angualrspringapp.controller;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author HafizYameenBashir
 *
 */
@Component
@Scope("prototype")
public class ThreadController  extends Thread{

	// private static Logger logger = Logger.getLogger(ThreadController.class.getName());
	@Override
	public void run() {
		
		System.out.println(getName() + " is running");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();// logger.error(e.getMessage(),e);
		}
		System.out.println(getName() + " is running");
	}

}

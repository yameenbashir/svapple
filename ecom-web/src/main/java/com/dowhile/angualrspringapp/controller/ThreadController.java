/**
 * 
 */
package com.dowhile.angualrspringapp.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author HafizYameenBashir
 *
 */
@Component
@Scope("prototype")
public class ThreadController  extends Thread{

	@Override
	public void run() {
		
		System.out.println(getName() + " is running");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(getName() + " is running");
	}

}

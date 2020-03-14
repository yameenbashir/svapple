/**
 * 
 */
package com.dowhile.angualrspringapp.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author Hafiz Yameen Bashir
 *
 */

@Component
public class ApplicationInitializeListner implements ApplicationListener<ContextRefreshedEvent> {

	// private static Logger logger = Logger.getLogger(ApplicationInitializeListner.class.getName());
	@Autowired
	private ApplicationCacheController applicationCacheController;
	@Autowired
	SchedulerController schedulerController = new SchedulerController();
	
    public void onApplicationEvent(final ContextRefreshedEvent event) {
   //   ApplicationContext ctx = event.getApplicationContext();
      System.out.println("On server Startup insdie onApplicationEvent");
		try {
			//applicationCacheController.load();
//			schedulerController.schedulerForMVInventoryExecution();
			
		}catch(Exception ex) {
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
    }

	
	
	

}

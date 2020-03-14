/**
 * 
 */
package com.dowhile.angualrspringapp.controller;

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

	@Autowired
	private ApplicationCacheController applicationCacheController;
	
    public void onApplicationEvent(final ContextRefreshedEvent event) {
   //   ApplicationContext ctx = event.getApplicationContext();
      System.out.println("On server Startup insdie onApplicationEvent");
		try {
			//applicationCacheController.load();
			
		}catch(Exception ex) {
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
    }

	
	
	

}

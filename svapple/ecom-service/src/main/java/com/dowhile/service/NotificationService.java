/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.Notification;

/**
 * @author Hafiz Bashir
 *
 */
public interface NotificationService {

	Notification addNotification(Notification notification);
	Notification updateNotification(Notification notification);
	boolean deleteNotification(Notification notification);
	List<Notification> getAllNotificationsByoutletIdToCompanyId(int outletId,int companyId);
	List<Notification> getAllNotificationsByoutletIdFromCompanyId(int outletId,int companyId);
	List<Notification> getAllNotificationsByCompanyId(int companyId);
}

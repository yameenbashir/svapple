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
	Notification getAllNotificationByID(int notificationId,int outletId);
	List<Notification> getAllNotificationsByoutletIdToCompanyId(int outletId,int companyId);
	List<Notification> getAllNotificationsByoutletIdFromCompanyId(int outletId,int companyId);
	List<Notification> getAllNotificationsByCompanyId(int companyId);
	List<Notification> getAllUnReadedNotificationsByCompanyId(int companyId);
	
	boolean markAllAsReadByOutletIdCompanyId(int outletId,int companyId);
	List<Notification> getAllUnReadNotificationsByOutletIdCompanyId(int outletId,int companyId);
	
	List<Notification> getAllReadedNotificationsByOutletIdCompanyId(int outletId,int companyId);
	List<Notification> getTenReadedNotificationsByOutletIdCompanyId(int outletId,int companyId);
	List<Notification> getAllReadedNotificationsByCompanyId(int companyId);
	List<Notification> getTenReadedNotificationsByCompanyId(int companyId);
	
}

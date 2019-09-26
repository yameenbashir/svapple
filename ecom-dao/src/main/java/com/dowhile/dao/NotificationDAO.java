/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.Notification;

/**
 * @author Hafiz Bashir
 *
 */
public interface NotificationDAO {

	Notification addNotification(Notification notification);
	Notification updateNotification(Notification notification);
	boolean deleteNotification(Notification notification);
	List<Notification> getAllNotificationsByoutletIdToCompanyId(int outletId,int companyId);
	List<Notification> getAllNotificationsByoutletIdFromCompanyId(int outletId,int companyId);
	List<Notification> getAllNotificationsByCompanyId(int companyId);
	Notification getAllNotificationByID(int notificationId,int outletId);
	boolean markAllAsReadByOutletIdCompanyId(int outletId,int companyId);
	List<Notification> getAllUnReadNotificationsByOutletIdCompanyId(int outletId,int companyId);
	List<Notification> getAllReadedNotificationsByOutletIdCompanyId(int outletId,int companyId);
	List<Notification> getAllReadedNotificationsByCompanyId(int companyId);
	List<Notification> getAllUnReadedNotificationsByCompanyId(int companyId);
}

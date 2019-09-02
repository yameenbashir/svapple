/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.Notification;
import com.dowhile.dao.NotificationDAO;
import com.dowhile.service.NotificationService;

/**
 * @author Hafiz Bashir
 *
 */
@Transactional(readOnly = false)
public class NotificationServiceImpl implements NotificationService{

	private NotificationDAO notificationDAO;

	/**
	 * @return the notificationDAO
	 */
	public NotificationDAO getNotificationDAO() {
		return notificationDAO;
	}

	/**
	 * @param notificationDAO the notificationDAO to set
	 */
	public void setNotificationDAO(NotificationDAO notificationDAO) {
		this.notificationDAO = notificationDAO;
	}

	@Override
	public Notification addNotification(Notification notification) {
		// TODO Auto-generated method stub
		return getNotificationDAO().addNotification(notification);
	}

	@Override
	public Notification updateNotification(Notification notification) {
		// TODO Auto-generated method stub
		return getNotificationDAO().updateNotification(notification);
	}

	@Override
	public boolean deleteNotification(Notification notification) {
		// TODO Auto-generated method stub
		return getNotificationDAO().deleteNotification(notification);
	}

	@Override
	public List<Notification> getAllNotificationsByoutletIdToCompanyId(
			int outletId, int companyId) {
		// TODO Auto-generated method stub
		return getNotificationDAO().getAllNotificationsByoutletIdToCompanyId(outletId, companyId);
	}

	@Override
	public List<Notification> getAllNotificationsByoutletIdFromCompanyId(
			int outletId, int companyId) {
		// TODO Auto-generated method stub
		return getNotificationDAO().getAllNotificationsByoutletIdFromCompanyId(outletId, companyId);
	}

	@Override
	public List<Notification> getAllNotificationsByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		return getNotificationDAO().getAllNotificationsByCompanyId(companyId);
	}
}

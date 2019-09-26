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

	@Override
	public Notification getAllNotificationByID(int notificationId,int outletId) {
		// TODO Auto-generated method stub
		return (Notification) getNotificationDAO().getAllNotificationByID(notificationId,outletId);
	}

	@Override
	public boolean markAllAsReadByOutletIdCompanyId(int outletId,
			int companyId) {
		// TODO Auto-generated method stub
		return getNotificationDAO().markAllAsReadByOutletIdCompanyId(outletId, companyId);
	}

	@Override
	public List<Notification> getAllUnReadNotificationsByOutletIdCompanyId(
			int outletId, int companyId) {
		// TODO Auto-generated method stub
		return getNotificationDAO().getAllUnReadNotificationsByOutletIdCompanyId(outletId, companyId);
	}

	@Override
	public List<Notification> getAllReadedNotificationsByOutletIdCompanyId(int outletId, int companyId) {
		// TODO Auto-generated method stub
		return getNotificationDAO().getAllReadedNotificationsByOutletIdCompanyId(outletId, companyId);
	}

	@Override
	public List<Notification> getAllReadedNotificationsByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		return getNotificationDAO().getAllReadedNotificationsByCompanyId(companyId);
	}

	@Override
	public List<Notification> getAllUnReadedNotificationsByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		return getNotificationDAO().getAllUnReadedNotificationsByCompanyId(companyId);
	}

	
	
	
	
}

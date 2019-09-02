package com.dowhile.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.ActivityDetail;
import com.dowhile.dao.ActivityDetailDAO;
import com.dowhile.service.ActivityDetailService;
/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class ActivityDetailServiceImpl implements ActivityDetailService{
	
	private ActivityDetailDAO activityDetailDAO;

	/**
	 * @return the activityDetailDAO
	 */
	public ActivityDetailDAO getActivityDetailDAO() {
		return activityDetailDAO;
	}

	/**
	 * @param activityDetailDAO the activityDetailDAO to set
	 */
	public void setActivityDetailDAO(ActivityDetailDAO activityDetailDAO) {
		this.activityDetailDAO = activityDetailDAO;
	}

	@Override
	public ActivityDetail addActivityDetail(ActivityDetail activityDetail, boolean isException, int companyId) {
		// TODO Auto-generated method stub
		return getActivityDetailDAO().addActivityDetail(activityDetail, companyId);
	}

}

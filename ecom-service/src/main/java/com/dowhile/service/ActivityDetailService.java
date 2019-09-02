package com.dowhile.service;

import com.dowhile.ActivityDetail;
/**
 * @author Yameen Bashir
 *
 */
public interface ActivityDetailService {

	public ActivityDetail addActivityDetail(ActivityDetail activityDetail, boolean isException, int companyId);
}

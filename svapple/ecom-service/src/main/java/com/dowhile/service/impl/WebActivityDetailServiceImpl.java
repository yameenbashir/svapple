/**
 * 
 */
package com.dowhile.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.WebActivityDetail;
import com.dowhile.dao.WebActivityDetailDAO;
import com.dowhile.service.WebActivityDetailService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class WebActivityDetailServiceImpl implements WebActivityDetailService{

	private WebActivityDetailDAO webActivityDetailDAO;

	/**
	 * @return the webActivityDetailDAO
	 */
	public WebActivityDetailDAO getWebActivityDetailDAO() {
		return webActivityDetailDAO;
	}

	/**
	 * @param webActivityDetailDAO the webActivityDetailDAO to set
	 */
	public void setWebActivityDetailDAO(WebActivityDetailDAO webActivityDetailDAO) {
		this.webActivityDetailDAO = webActivityDetailDAO;
	}

	@Override
	public WebActivityDetail addWebActivityDetail(
			WebActivityDetail webActivityDetail) {
		// TODO Auto-generated method stub
		return getWebActivityDetailDAO().addWebActivityDetail(webActivityDetail);
	}
}

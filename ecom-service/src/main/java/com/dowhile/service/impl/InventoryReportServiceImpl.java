/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.InventoryReport;
import com.dowhile.beans.TableData;
import com.dowhile.dao.InventoryReportDAO;
import com.dowhile.service.InventoryReportService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class InventoryReportServiceImpl implements InventoryReportService{

	private InventoryReportDAO inventoryReportDAO;
	
	/**
	 * @return the inventoryReportDAO
	 */
	public InventoryReportDAO getInventoryReportDAO() {
		return inventoryReportDAO;
	}

	/**
	 * @param inventoryReportDAO the inventoryReportDAO to set
	 */
	public void setInventoryReportDAO(InventoryReportDAO inventoryReportDAO) {
		this.inventoryReportDAO = inventoryReportDAO;
	}

	@Override
	public List<InventoryReport> getInventoryReportByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		return getInventoryReportDAO().getInventoryReportByCompanyId(companyId);
	}

	@Override
	public List<InventoryReport> getInventoryReportByOutletIdCompanyId(
			int outletId, int companyId) {
		// TODO Auto-generated method stub
		return getInventoryReportDAO().getInventoryReportByOutletIdCompanyId(outletId, companyId);
	}

	@Override
	public TableData getInventoryReportByCompanyIdOutletId(int companyId,
			int outletId,boolean isHeadOffice) {
		// TODO Auto-generated method stub
		return getInventoryReportDAO().getInventoryReportByCompanyIdOutletId(companyId, outletId,isHeadOffice);
	}

}

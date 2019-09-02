/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.InventoryDetailReport;
import com.dowhile.dao.InventoryDetailReportDAO;
import com.dowhile.service.InventoryDetailReportService;

/**
 * @author Hafiz Bashir
 *
 */
@Transactional(readOnly = false)
public class InventoryDetailReportServiceImpl implements InventoryDetailReportService{

	private InventoryDetailReportDAO inventoryDetailReportDAO;
	/**
	 * @return the inventoryDetailReportDAO
	 */
	public InventoryDetailReportDAO getInventoryDetailReportDAO() {
		return inventoryDetailReportDAO;
	}

	/**
	 * @param inventoryDetailReportDAO the inventoryDetailReportDAO to set
	 */
	public void setInventoryDetailReportDAO(
			InventoryDetailReportDAO inventoryDetailReportDAO) {
		this.inventoryDetailReportDAO = inventoryDetailReportDAO;
	}

	@Override
	public List<InventoryDetailReport> getInventoryDetailReportByCompanyId(
			int companyId) {
		// TODO Auto-generated method stub
		return getInventoryDetailReportDAO().getInventoryDetailReportByCompanyId(companyId);
	}

	@Override
	public List<InventoryDetailReport> getInventoryDetailReportByOutletIdCompanyId(
			int outletId, int companyId) {
		// TODO Auto-generated method stub
		return getInventoryDetailReportDAO().getInventoryDetailReportByOutletIdCompanyId(outletId, companyId);
	}

	
	
}

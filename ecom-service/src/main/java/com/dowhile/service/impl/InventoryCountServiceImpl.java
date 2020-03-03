package com.dowhile.service.impl; 

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.Company;
import com.dowhile.InventoryCount;
import com.dowhile.dao.InventoryCountDAO;
import com.dowhile.service.InventoryCountService;
import com.dowhile.wrapper.InventoryCountWrapper;


/**
 * @author Zafar Shakeel
 *
 */
@Transactional(readOnly = false)
public class InventoryCountServiceImpl implements InventoryCountService{
	
	private InventoryCountDAO inventoryCountDAO;

	/**
	 * @return the InventoryCountDAO
	 */
	public InventoryCountDAO getInventoryCountDAO() {
		return inventoryCountDAO;
	}

	/**
	 * @param InventoryCountDAO the InventoryCountDAO to set
	 */
	public void setInventoryCountDAO(InventoryCountDAO inventoryCountDAO) {
		this.inventoryCountDAO = inventoryCountDAO;
	}

	@Override
	public InventoryCount addInventoryCount(InventoryCount inventoryCount,int companyId) {
		// TODO Auto-generated method stub
		return getInventoryCountDAO().addInventoryCount(inventoryCount,companyId);
	}

	@Override
	public InventoryCount updateInventoryCount(InventoryCount inventoryCount,int companyId) {
		// TODO Auto-generated method stub
		return getInventoryCountDAO().updateInventoryCount(inventoryCount,companyId);
	} 

	@Override
	public boolean updateInventoryCountComplete(InventoryCountWrapper inventoryCountWrapper,Company company) {
		// TODO Auto-generated method stub
		return getInventoryCountDAO().updateInventoryCountComplete(inventoryCountWrapper,company);
	}
	
	@Override
	public boolean deleteInventoryCount(InventoryCount inventoryCount,int companyId) {
		// TODO Auto-generated method stub
		return getInventoryCountDAO().deleteInventoryCount(inventoryCount,companyId);
	}

	@Override
	public InventoryCount getInventoryCountByInventoryCountID(int inventoryCountID,int companyId) {
		// TODO Auto-generated method stub
		return getInventoryCountDAO().getInventoryCountByInventoryCountID(inventoryCountID,companyId);
	}
	
	@Override
	public List<InventoryCount> getInventoryCountBySupplierId(int supplierID,int companyId) {
		// TODO Auto-generated method stub
		return getInventoryCountDAO().getInventoryCountBySupplierId(supplierID,companyId);
	}
	
	@Override
	public List<InventoryCount> getInventoryCountByOutletId(int outletID,int companyId) {
		// TODO Auto-generated method stub
		return getInventoryCountDAO().getInventoryCountByOutletId(outletID,companyId);
	}
		
	@Override
	public List<InventoryCount> GetAllInventoryCount(int companyId) {
		// TODO Auto-generated method stub
		return getInventoryCountDAO().GetAllInventoryCount(companyId);
	}
	
}

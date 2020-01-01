package com.dowhile.dao;

import java.util.List;

import com.dowhile.Company;
import com.dowhile.InventoryCount;
import com.dowhile.wrapper.InventoryCountWrapper;

/**
 * @author Zafar Shakeel
 *
 */
public interface InventoryCountDAO {
	
	InventoryCount addInventoryCount(InventoryCount inventoryCount,int companyId);
	InventoryCount updateInventoryCount(InventoryCount inventoryCount,int companyId);
	boolean updateInventoryCountComplete(InventoryCountWrapper inventoryCountWrapper,Company company);
	boolean deleteInventoryCount(InventoryCount inventoryCount,int companyId);
	InventoryCount getInventoryCountByInventoryCountID(int inventoryCountID,int companyId);	
	List<InventoryCount> getInventoryCountBySupplierId(int supplierID,int companyId);
	List<InventoryCount> getInventoryCountByOutletId(int outletID,int companyId);
	List<InventoryCount> GetAllInventoryCount(int companyId);
}

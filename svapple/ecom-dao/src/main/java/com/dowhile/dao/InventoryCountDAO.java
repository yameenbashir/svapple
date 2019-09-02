package com.dowhile.dao;

import java.util.List;

import com.dowhile.InventoryCount;

/**
 * @author Zafar Shakeel
 *
 */
public interface InventoryCountDAO {
	
	InventoryCount addInventoryCount(InventoryCount inventoryCount,int companyId);
	InventoryCount updateInventoryCount(InventoryCount inventoryCount,int companyId);
	boolean deleteInventoryCount(InventoryCount inventoryCount,int companyId);
	InventoryCount getInventoryCountByInventoryCountID(int inventoryCountID,int companyId);	
	List<InventoryCount> getInventoryCountBySupplierId(int supplierID,int companyId);
	List<InventoryCount> getInventoryCountByOutletId(int outletID,int companyId);
	List<InventoryCount> GetAllInventoryCount(int companyId);
}

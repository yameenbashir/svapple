package com.dowhile.dao;

import java.util.List;

import com.dowhile.InventoryCount;
import com.dowhile.InventoryCountDetail;
/**
 * @author Zafar Shakeel
 *
 */
public interface InventoryCountDetailDAO {
	
	InventoryCountDetail addInventoryCountDetail(InventoryCountDetail inventoryCountDetail,int companyId);
	void addInventoryCountDetailsList(List<InventoryCountDetail> inventoryCountDetail,int companyId);
	InventoryCountDetail updateInventoryCountDetail(InventoryCountDetail inventoryCountDetail,int companyId);
	void updateInventoryCountDetailsList(List<InventoryCountDetail> inventoryCountDetailsList,int companyId);
	boolean deleteInventoryCountDetail(InventoryCountDetail inventoryCountDetail,int companyId);
	void deleteInventoryCountDetailsList(List<InventoryCountDetail> inventoryCountDetail,int companyId);
	InventoryCountDetail getInventoryCountDetailByInventoryCountDetailID(int inventoryCountDetailID,int companyId);
	List<InventoryCountDetail> getInventoryCountDetailByInventoryCountId(int inventoryCountID,int companyId);
	List<InventoryCountDetail> getAllInventoryCountDetails(int companyId);
}

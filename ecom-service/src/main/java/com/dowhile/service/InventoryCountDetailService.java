package com.dowhile.service;

import java.util.List;

import com.dowhile.InventoryCountDetail;
import com.dowhile.InventoryCountDetailCustom;
/**
 * @author Zafar Shakeel
 *
 */
public interface InventoryCountDetailService {

	InventoryCountDetail addInventoryCountDetail(InventoryCountDetail inventoryCountDetail,int companyId);
	void addInventoryCountDetailsList(List<InventoryCountDetail> inventoryCountDetail,int companyId);
	void addorUpdateInventoryCountDetailsList(List<InventoryCountDetail> inventoryCountDetail,int companyId);
	InventoryCountDetail updateInventoryCountDetail(InventoryCountDetail inventoryCountDetail,int companyId);
	void updateInventoryCountDetailsList(List<InventoryCountDetail> inventoryCountDetailsList,int companyId);
	boolean deleteInventoryCountDetail(InventoryCountDetail inventoryCountDetail,int companyId);
	void deleteInventoryCountDetailsList(List<InventoryCountDetail> inventoryCountDetail,int companyId);
	InventoryCountDetail getInventoryCountDetailByInventoryCountDetailID(int inventoryCountDetailID,int companyId);
	List<InventoryCountDetail> getInventoryCountDetailByInventoryCountId(int inventoryCountID,int companyId);
	List<InventoryCountDetailCustom> getInventoryCountDetailByInventoryCountIdCustom(int inventoryCountID,int companyId);
	List<InventoryCountDetail> getAllInventoryCountDetails(int companyId);
	List<InventoryCountDetailCustom> createFullInventoryCount(int outletId, int inventory_count_id, int userId, int companyId);
}

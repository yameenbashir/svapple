package com.dowhile.dao;

import java.util.List;

import com.dowhile.InventoryCountType;

/**
 * @author Zafar Shakeel
 *
 */
public interface InventoryCountTypeDAO {

	InventoryCountType addInventoryCountType(InventoryCountType inventoryCountType);
	InventoryCountType updateInventoryCountType(InventoryCountType inventoryCountType);
	boolean deleteInventoryCountType(InventoryCountType inventoryCountType);
	InventoryCountType getInventoryCountTypeByInventoryCountTypeId(int inventoryCountTypeId);
	List<InventoryCountType> getAllInventoryCountType();
}

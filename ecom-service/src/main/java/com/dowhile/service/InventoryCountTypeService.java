package com.dowhile.service; 

import java.util.List;

import com.dowhile.InventoryCountType;

/**
 * @author Zafar Shakeel
 *
 */
public interface InventoryCountTypeService {

	InventoryCountType addInventoryCountType(InventoryCountType inventoryCountType);
	InventoryCountType updateInventoryCountType(InventoryCountType inventoryCountType);
	boolean deleteInventoryCountType(InventoryCountType inventoryCountType);
	InventoryCountType getInventoryCountTypeByInventoryCountTypeId(int inventoryCountTypeId);
	List<InventoryCountType> getAllInventoryCountType();
}

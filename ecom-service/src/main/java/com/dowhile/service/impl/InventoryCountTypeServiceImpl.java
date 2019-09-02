/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.InventoryCountType;
import com.dowhile.dao.InventoryCountTypeDAO;
import com.dowhile.service.InventoryCountTypeService;

/**
 * @author Zafar Shakeel
 *
 */
@Transactional(readOnly = false)
public class InventoryCountTypeServiceImpl implements InventoryCountTypeService{


	private InventoryCountTypeDAO inventoryCountTypeDAO;
	
	/**
	 * @return the InventoryCountTypeDAO
	 */
	public InventoryCountTypeDAO getInventoryCountTypeDAO() {
		return inventoryCountTypeDAO;
	}

	/**
	 * @param inventoryCountTypeDAO the InventoryCountTypeDAO to set
	 */
	public void setInventoryCountTypeDAO(InventoryCountTypeDAO inventoryCountTypeDAO) {
		this.inventoryCountTypeDAO = inventoryCountTypeDAO;
	}

	@Override
	public InventoryCountType addInventoryCountType(InventoryCountType inventoryCountType) {
		// TODO Auto-generated method stub
		return getInventoryCountTypeDAO().addInventoryCountType(inventoryCountType);
	}

	@Override
	public InventoryCountType updateInventoryCountType(InventoryCountType inventoryCountType) {
		// TODO Auto-generated method stub
		return getInventoryCountTypeDAO().updateInventoryCountType(inventoryCountType);
	}

	@Override
	public boolean deleteInventoryCountType(InventoryCountType inventoryCountType) {
		// TODO Auto-generated method stub
		return getInventoryCountTypeDAO().deleteInventoryCountType(inventoryCountType);
	}

	@Override
	public InventoryCountType getInventoryCountTypeByInventoryCountTypeId(int inventoryCountTypeId) {
		// TODO Auto-generated method stub
		return getInventoryCountTypeDAO().getInventoryCountTypeByInventoryCountTypeId(inventoryCountTypeId);
	}

	@Override
	public List<InventoryCountType> getAllInventoryCountType() {
		// TODO Auto-generated method stub
		return getInventoryCountTypeDAO().getAllInventoryCountType();
	}

}

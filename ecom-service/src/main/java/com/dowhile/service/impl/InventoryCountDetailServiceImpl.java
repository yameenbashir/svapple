package com.dowhile.service.impl; 

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.InventoryCountDetail;
import com.dowhile.InventoryCountDetailCustom;
import com.dowhile.dao.InventoryCountDetailDAO;
import com.dowhile.service.InventoryCountDetailService;

/**
 * @author Zafar Shakeel
 *
 */
@Transactional(readOnly = false)
public class InventoryCountDetailServiceImpl implements InventoryCountDetailService{
	
	private InventoryCountDetailDAO inventoryCountDetailDAO;

	/**
	 * @return the InventoryCountDetailDAO
	 */
	public InventoryCountDetailDAO getInventoryCountDetailDAO() {
		return inventoryCountDetailDAO;
	}

	/**
	 * @param InventoryCountDetailDAO the InventoryCountDetailDAO to set
	 */
	public void setInventoryCountDetailDAO(InventoryCountDetailDAO inventoryCountDetailDAO) {
		this.inventoryCountDetailDAO = inventoryCountDetailDAO;
	}

	@Override
	public InventoryCountDetail addInventoryCountDetail(InventoryCountDetail inventoryCountDetail,int companyId) {
		// TODO Auto-generated method stub
		return getInventoryCountDetailDAO().addInventoryCountDetail(inventoryCountDetail,companyId);
	}

	@Override
	public void addInventoryCountDetailsList(List<InventoryCountDetail> inventoryCountDetail,int companyId) {
		// TODO Auto-generated method stub
		getInventoryCountDetailDAO().addInventoryCountDetailsList(inventoryCountDetail,companyId);
	}
	
	@Override
	public void addorUpdateInventoryCountDetailsList(List<InventoryCountDetail> inventoryCountDetail,int companyId) {
		// TODO Auto-generated method stub
		getInventoryCountDetailDAO().addorUpdateInventoryCountDetailsList(inventoryCountDetail,companyId);
	}
	
	@Override
	public InventoryCountDetail updateInventoryCountDetail(InventoryCountDetail inventoryCountDetail,int companyId) {
		// TODO Auto-generated method stub
		return getInventoryCountDetailDAO().updateInventoryCountDetail(inventoryCountDetail,companyId);
	}

	@Override
	public void updateInventoryCountDetailsList(List<InventoryCountDetail> inventoryCountDetailsList,int companyId) {
		// TODO Auto-generated method stub
		 getInventoryCountDetailDAO().updateInventoryCountDetailsList(inventoryCountDetailsList,companyId);
	}
	
	@Override
	public boolean deleteInventoryCountDetail(InventoryCountDetail inventoryCountDetail,int companyId) {
		// TODO Auto-generated method stub
		return getInventoryCountDetailDAO().deleteInventoryCountDetail(inventoryCountDetail,companyId);
	}
	
	@Override
	public void deleteInventoryCountDetailsList(List<InventoryCountDetail> inventoryCountDetail,int companyId) {
		// TODO Auto-generated method stub
		getInventoryCountDetailDAO().deleteInventoryCountDetailsList(inventoryCountDetail,companyId);
	}
	
	@Override
	public InventoryCountDetail getInventoryCountDetailByInventoryCountDetailID(int inventoryCountDetailID,int companyId) {
		// TODO Auto-generated method stub
		return getInventoryCountDetailDAO().getInventoryCountDetailByInventoryCountDetailID(inventoryCountDetailID,companyId);
	}

	@Override
	public List<InventoryCountDetail> getInventoryCountDetailByInventoryCountId(int inventoryCountID,int companyId){
		// TODO Auto-generated method stub
		return getInventoryCountDetailDAO().getInventoryCountDetailByInventoryCountId(inventoryCountID,companyId);
	}
	@Override
	public List<InventoryCountDetailCustom> getInventoryCountDetailByInventoryCountIdCustom(int inventoryCountID,int companyId){
		// TODO Auto-generated method stub
		return getInventoryCountDetailDAO().getInventoryCountDetailByInventoryCountIdCustom(inventoryCountID,companyId);
	}
	@Override
	public List<InventoryCountDetail> getAllInventoryCountDetails(int companyId){
		// TODO Auto-generated method stub
		return getInventoryCountDetailDAO().getAllInventoryCountDetails(companyId);
	}
	
	@Override
	public List<InventoryCountDetailCustom> createFullInventoryCount(int outletId, int inventory_count_id, int userId, int companyId){
		// TODO Auto-generated method stub
		return getInventoryCountDetailDAO().createFullInventoryCount(outletId, inventory_count_id, userId, companyId);
	}
	
}

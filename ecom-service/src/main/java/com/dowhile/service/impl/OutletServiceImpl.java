/**
 * 
 */
package com.dowhile.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.Outlet;
import com.dowhile.dao.OutletDAO;
import com.dowhile.service.OutletService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class OutletServiceImpl implements OutletService{
	
	private OutletDAO outletDAO;
	/**
	 * @return the outletDAO
	 */
	public OutletDAO getOutletDAO() {
		return outletDAO;
	}

	/**
	 * @param outletDAO the outletDAO to set
	 */
	public void setOutletDAO(OutletDAO outletDAO) {
		this.outletDAO = outletDAO;
	}

	@Override
	public Outlet addOutlet(Outlet outlet, int companyId) {
		// TODO Auto-generated method stub
		return getOutletDAO().addOutlet(outlet,companyId);
	}

	@Override
	public Outlet updateOutlet(Outlet outlet, int companyId) {
		// TODO Auto-generated method stub
		return getOutletDAO().updateOutlet(outlet,companyId);
	}

	@Override
	public boolean deleteOutlet(Outlet outlet, int companyId) {
		// TODO Auto-generated method stub
		return getOutletDAO().deleteOutlet(outlet,companyId);
	}

	@Override
	public List<Outlet> getOutlets(int companyId) {
		// TODO Auto-generated method stub
		return getOutletDAO().getOutlets(companyId);
	}
	
	@Override
	public Outlet getOuletByOutletId(int outletId, int companyId) {
		// TODO Auto-generated method stub
		return getOutletDAO().getOuletByOutletId(outletId,companyId);
	}

	@Override
	public Outlet getHeadOfficeOutlet(int companyId) {
		// TODO Auto-generated method stub
		return getOutletDAO().getHeadOfficeOutlet(companyId);
	}

	@Override
	public Map<Integer, Outlet> getAllOutletsMapByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		Map<Integer, Outlet> map = new HashMap<Integer, Outlet>();
		List<Outlet> outlets = getOutletDAO().getOutlets(companyId);
		if(outlets!=null){
			for(Outlet outlet : outlets){
				map.put(outlet.getOutletId(), outlet);
			}
		}
		return map;
	}

	@Override
	public List<Outlet> getAllActiveOutletsByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		return getOutletDAO().getAllActiveOutletsByCompanyId(companyId);
	}
}

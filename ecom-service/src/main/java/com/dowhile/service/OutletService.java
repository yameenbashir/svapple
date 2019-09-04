/**
 * 
 */
package com.dowhile.service;

import java.util.List;
import java.util.Map;

import com.dowhile.Outlet;

/**
 * @author Yameen Bashir
 *
 */
public interface OutletService {

	Outlet addOutlet(Outlet outlet, int companyId);
	Outlet updateOutlet(Outlet outlet, int companyId);
	boolean deleteOutlet(Outlet outlet, int companyId);
	public List<Outlet> getOutlets(int companyId);
	Outlet getOuletByOutletId(int outletId, int companyId);
	Outlet getHeadOfficeOutlet(int companyId);
	Map<Integer,Outlet> getAllOutletsMapByCompanyId(int companyId);
	List<Outlet> getAllActiveOutletsByCompanyId( int companyId);
}

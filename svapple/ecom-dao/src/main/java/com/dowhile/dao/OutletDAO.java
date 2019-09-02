/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.Outlet;

/**
 * @author Yameen Bashir
 *
 */
public interface OutletDAO {	
	Outlet addOutlet(Outlet outlet, int companyId);
	Outlet updateOutlet(Outlet outlet, int companyId);
	boolean deleteOutlet(Outlet outlet, int companyId);
	public List<Outlet> getOutlets( int companyId);
	Outlet getOuletByOutletId(int outletId, int companyId);
	Outlet getHeadOfficeOutlet(int companyId);

}

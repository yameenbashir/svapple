/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.Register;

/**
 * @author Yameen Bashir
 *
 */
public interface RegisterDAO {
	
	Register addRegister(Register register, int companyId);
	Register updateRegister(Register register, int companyId);
	boolean deleteRegister(Register register, int companyId);
	public List<Register> getRegistersByOutletId(int outletId, int companyId,int userId);
	Register getRegisterByRegisterId(int registerId, int companyId);

}

/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.Register;

/**
 * @author Yameen Bashir
 *
 */
public interface RegisterService {

	Register addRegister(Register register, int companyId);
	Register updateRegister(Register register, int companyId);
	boolean deleteRegister(Register register, int companyId);
	public List<Register> getRegestersByOutletId(int outletId, int companyId,int userId);
	Register getRegisterByRegisterId(int registerId, int companyId);


}

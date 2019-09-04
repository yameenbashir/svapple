/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.Register;
import com.dowhile.dao.RegisterDAO;
import com.dowhile.service.RegisterService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class RegisterServiceImpl implements RegisterService{
	
	private RegisterDAO registerDAO;

	
	/**
	 * @return the registerDAO
	 */
	public RegisterDAO getRegisterDAO() {
		return registerDAO;
	}

	/**
	 * @param registerDAO the registerDAO to set
	 */
	public void setRegisterDAO(RegisterDAO registerDAO) {
		this.registerDAO = registerDAO;
	}

	@Override
	public Register addRegister(Register register, int companyId) {
		// TODO Auto-generated method stub
		return getRegisterDAO().addRegister(register,companyId);
	}

	@Override
	public Register updateRegister(Register register, int companyId) {
		// TODO Auto-generated method stub
		return getRegisterDAO().updateRegister(register,companyId);
	}

	@Override
	public boolean deleteRegister(Register register, int companyId) {
		// TODO Auto-generated method stub
		return getRegisterDAO().deleteRegister(register,companyId);
	}

	@Override
	public List<Register> getRegestersByOutletId(int outletId, int companyId,int userId) {
		// TODO Auto-generated method stub
		return getRegisterDAO().getRegistersByOutletId(outletId,companyId,userId);
	}

	@Override
	public Register getRegisterByRegisterId(int registerId, int companyId) {
		// TODO Auto-generated method stub
		return getRegisterDAO().getRegisterByRegisterId(registerId,companyId);
	}





	
	
}

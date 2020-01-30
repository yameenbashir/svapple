/**
 * 
 */
package com.dowhile.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.Address;
import com.dowhile.dao.AddressDAO;
import com.dowhile.service.AddressService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class AddressServiceImpl implements AddressService {

	private AddressDAO addressDAO;
	/**
	 * @return the addressDAO
	 */
	public AddressDAO getAddressDAO() {
		return addressDAO;
	}

	/**
	 * @param addressDAO the addressDAO to set
	 */
	public void setAddressDAO(AddressDAO addressDAO) {
		this.addressDAO = addressDAO;
	}

	@Override
	public Address addAddress(Address address, int companyId) {
		// TODO Auto-generated method stub
		return getAddressDAO().addAddress(address,companyId);
	}

	@Override
	public Address updateAddress(Address address, int companyId) {
		// TODO Auto-generated method stub
		return getAddressDAO().updateAddress(address,companyId);
	}

	@Override
	public boolean deleteAddress(Address address, int companyId) {
		// TODO Auto-generated method stub
		return getAddressDAO().deleteAddress(address,companyId);
	}

	@Override
	public Address getAddressByAddressId(int addressId, int companyId) {
		// TODO Auto-generated method stub
		return getAddressDAO().getAddressByAddressId(addressId,companyId);
	}

	@Override
	public List<Address> getAddressByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		return getAddressDAO().getAddressByCompanyId(companyId);
	}

	@Override
	public List<Address> getAddressBySupplierId(int supplierId, int companyId) {
		// TODO Auto-generated method stub
		return getAddressDAO().getAddressBySupplierId(supplierId,companyId);
	}
	
	@Override
	public List<Address> getAddressByCustomerId(int customerId, int companyId) {
		// TODO Auto-generated method stub
		return getAddressDAO().getAddressByCustomerId(customerId,companyId);
	}
	
	@Override
	public List<Address> getAllAddress(int companyId) {
		// TODO Auto-generated method stub
		return getAddressDAO().getAllAddress(companyId);
	}

	@Override
	public Map<String, Address> getALLAddressByCustomerId(int companyId) {
		Map<String, Address> map = new HashMap<String, Address>();
		List<Address>  addresses = getAddressDAO().getAddressByCompanyId(companyId);
		if(addresses!=null){
			for(Address address : addresses){
				if(address.getAddressType().equalsIgnoreCase("Physical Address") && address.getContact()!=null){
					map.put(address.getContact().getContactId().toString(), address);
				}
			}
		}
		
		return map;
	}

	@Override
	public List<Address> getAddress(String phone, int companyId) {
		// TODO Auto-generated method stub
		return getAddressDAO().getAddress(phone,companyId);
	}
}

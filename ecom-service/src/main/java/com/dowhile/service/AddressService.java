/**
 * 
 */
package com.dowhile.service;

import java.util.List;
import java.util.Map;

import com.dowhile.Address;

/**
 * @author Yameen Bashir
 *
 */
public interface AddressService {

	Address addAddress(Address address, int companyId);
	Address updateAddress(Address address, int companyId);
	boolean deleteAddress(Address address, int companyId);
	Address getAddressByAddressId(int addressId, int companyId);
	List< Address> getAddressByCompanyId(int companyId);
	List< Address> getAddressBySupplierId(int supplierId, int companyId);
	List< Address> getAddressByCustomerId(int customerId, int companyId);
	Map<String, Address> getALLAddressByCustomerId(int companyId);
	List< Address> getAllAddress(int companyId);
}

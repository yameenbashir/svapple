/**
 * 
 */
package com.dowhile.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.VarientValueBean;

/**
 * @author Yameen Bashir
 *
 */
public class ControllerUtil {

	public static int  getTotlalQuantityForAllOutlets(List<OutletBean> outletsist){
		int totalCount = 0;
		try{
			if(outletsist!=null){
				for(OutletBean outletBean:outletsist){
					if(outletBean.getCurrentInventory()!=null && !outletBean.getCurrentInventory().equalsIgnoreCase("")){
						totalCount = Integer.valueOf(outletBean.getCurrentInventory())+totalCount;
					}
					
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return totalCount;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getTotalCurrentInventoryAgainstEeachOutlet(List<VarientValueBean> varientValuesBeanCollection){
		Map outletsCountMap = new HashMap<>();
		try{
			if(varientValuesBeanCollection!=null){
				for(VarientValueBean varientValueBean:varientValuesBeanCollection){
					List<OutletBean> variantOutletBeanList = varientValueBean.getVarientsOutletList();
					if(variantOutletBeanList!=null){
						for(OutletBean variantOutletBean:variantOutletBeanList){
							 if (outletsCountMap.containsKey(variantOutletBean.getOutletId())) {
						            //key exists
								 int currentCount = (Integer)(outletsCountMap.get(variantOutletBean.getOutletId()));
								 currentCount = Integer.valueOf(variantOutletBean.getCurrentInventory())+currentCount;
								 outletsCountMap.put(variantOutletBean.getOutletId(), currentCount);
						            
						        } else {
						            //key does not exists
						        	outletsCountMap.put(variantOutletBean.getOutletId(), Integer.valueOf(variantOutletBean.getCurrentInventory()));
						        }
						}
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return outletsCountMap;
	}
}

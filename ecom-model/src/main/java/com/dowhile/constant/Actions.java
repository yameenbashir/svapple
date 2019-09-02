/**
 * 
 */
package com.dowhile.constant;

/**
 * @author Yameen Bashir
 *
 */
public enum Actions {

	CREATE("Created"),
    UPDATE("Update"),
    DELETE("Deleted"),
    SELL("Sale"),
    INVENTORY_ADD("Inventory Added"),
    INVENTORY_SUBTRACT("Inventory Subtracted"),
    INVENTORY_TRANSFER("Inventory Transfer"),
	INVENTORY_NOCHANGE("No Change"),
	BALANCE_ADD("Balance Add"),
	BALANCE_REMOVE("Balance Remove"),
   
    UNKNOWN("");
	
	String actionName;

	/**
	 * @param actionName
	 */
	private Actions(String actionName) {
		this.actionName = actionName;
	}

	/**
	 * @return the actionName
	 */
	public String getActionName() {
		return actionName;
	}

	/**
	 * @param actionName the actionName to set
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

}

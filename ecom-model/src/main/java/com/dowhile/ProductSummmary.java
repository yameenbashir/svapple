package com.dowhile;
// Generated Aug 17, 2017 1:48:25 PM by Hibernate Tools 3.4.0.CR1


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ProductSummmary generated by hbm2java
 */
@Entity
@Table(name="product_summmary"
    ,catalog="ecom"
)
public class ProductSummmary  implements java.io.Serializable {


    private ProductSummmaryId id;

   public ProductSummmary() {
   }

   public ProductSummmary(ProductSummmaryId id) {
      this.id = id;
   }
  
    @EmbeddedId

   
   @AttributeOverrides( {
       @AttributeOverride(name="id", column=@Column(name="ID", nullable=false) ), 
       @AttributeOverride(name="productName", column=@Column(name="PRODUCT_NAME", nullable=false, length=500) ), 
       @AttributeOverride(name="sku", column=@Column(name="SKU", nullable=false, length=500) ), 
       @AttributeOverride(name="supplyPriceExclTax", column=@Column(name="SUPPLY_PRICE_EXCL_TAX", nullable=false, precision=20) ), 
       @AttributeOverride(name="reorderPoint", column=@Column(name="REORDER_POINT") ), 
       @AttributeOverride(name="reorderAmount", column=@Column(name="REORDER_AMOUNT", precision=20) ), 
       @AttributeOverride(name="currentInventory", column=@Column(name="CURRENT_INVENTORY") ), 
       @AttributeOverride(name="netPrice", column=@Column(name="NET_PRICE", precision=33, scale=11) ), 
       @AttributeOverride(name="outletName", column=@Column(name="OUTLET_NAME", length=100) ), 
       @AttributeOverride(name="outletAssocicationId", column=@Column(name="OUTLET_ASSOCICATION_ID", nullable=false) ), 
       @AttributeOverride(name="createdDate", column=@Column(name="CREATED_DATE", nullable=false, length=19) ), 
       @AttributeOverride(name="brandName", column=@Column(name="BRAND_NAME", length=200) ), 
       @AttributeOverride(name="productType", column=@Column(name="PRODUCT_TYPE", length=200) ), 
       @AttributeOverride(name="contactName", column=@Column(name="CONTACT_NAME", length=200) ), 
       @AttributeOverride(name="variantCount", column=@Column(name="VARIANT_COUNT") ), 
       @AttributeOverride(name="variantCurrentInventory", column=@Column(name="VARIANT_CURRENT_INVENTORY") ), 
       @AttributeOverride(name="variantSku", column=@Column(name="VARIANT_SKU", nullable=false, length=500) ), 
       @AttributeOverride(name="variantSupplyPriceExclTax", column=@Column(name="VARIANT_SUPPLY_PRICE_EXCL_TAX", nullable=false, precision=20) ), 
       @AttributeOverride(name="variantReorderPoint", column=@Column(name="VARIANT_REORDER_POINT") ), 
       @AttributeOverride(name="variantReorderAmount", column=@Column(name="VARIANT_REORDER_AMOUNT", precision=20) ), 
       @AttributeOverride(name="variantNetPrice", column=@Column(name="VARIANT_NET_PRICE", precision=33, scale=11) ), 
       @AttributeOverride(name="variantCompCount", column=@Column(name="VARIANT_COMP_COUNT") ), 
       @AttributeOverride(name="companyAssociationId", column=@Column(name="COMPANY_ASSOCIATION_ID", nullable=false) ), 
       @AttributeOverride(name="imagePath", column=@Column(name="IMAGE_PATH", length=250) ), 
       @AttributeOverride(name="variantProducts", column=@Column(name="VARIANT_PRODUCTS", nullable=false, length=10) ), 
       @AttributeOverride(name="standardProduct", column=@Column(name="STANDARD_PRODUCT", nullable=false, length=10) ), 
       @AttributeOverride(name="isComposite", column=@Column(name="IS_COMPOSITE", length=10) ), 
       @AttributeOverride(name="variantInventoryCount", column=@Column(name="VARIANT_INVENTORY_COUNT", precision=32, scale=0) ) } )
   public ProductSummmaryId getId() {
       return this.id;
   }
   
   public void setId(ProductSummmaryId id) {
       this.id = id;
   }




}


'use strict';

/**
 * SellController
 * 
 * @constructor
 */
var SellController =  ['$scope', '$http', '$window', '$cookieStore', '$rootScope', 'SessionService', '$timeout', '$sce', 'SellControllerPreLoad','$route', function($scope, $http, $window, $cookieStore, $rootScope, SessionService, $timeout, $sce, SellControllerPreLoad,$route) {

	$scope.printReceipt = true;
	$scope.cashloading = false;
	$scope.creditloading = false;
	$scope.laybyloading = false;
	$scope.showPaymentSale = false;
	$scope.readonlyInvoiceDiscount = false;
	$scope.addCustomerProcessingButton = false;
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.makePayment = false;
	$scope.parkDiscard = true;
	$scope.changeamount = 0;
	$scope.parkDiscard = true;
	$scope.showDiscount = false;
	$scope.success = false;
	$scope.error = false;
	$scope.itemDetailDisable = false;
	$scope.standardReceipt = true;
	
	$scope.returnvalue = parseFloat(1);
	$scope.listPayments = [];
	$scope.payment = {};
	$scope.gobackenable = true;
	$scope.InvoiceMainBean = {};  
	$scope.InvoiceMainBean.invoiceDetails = [];
	$scope.InvoiceMainBean.invoiceGivenAmt = parseFloat(0);
	$scope.InvoiceMainBean.invoiceNetAmt=parseFloat(0);
	$scope.InvoiceMainBean.invoiceAmt = parseFloat(0);
	$scope.InvoiceMainBean.invoiceTax = parseFloat(0);
	$scope.InvoiceMainBean.invoiceSubTotal = parseFloat(0);
	$scope.productVaraintDetailBeanList = []; 
	$scope.pricebook = {};
	$scope.isReturnEnableonSell = true;
	$scope.totallineItemDiscount = parseFloat(0);
	$scope.isInvoiceLevelDiscountEnable = false;
	$scope.isInvoiceDetailLevelDiscountEnable = true;
	$scope.isInvoiceTaxAmountEnable = true;
	$rootScope.globalPageLoader = true;
//$scope.totalDiscount = parseFloat(0);
	$scope.InvoiceMainBean.itemsCount = parseFloat(0);
	$scope.isValidInvoice = true;
	$scope.outletId = $cookieStore.get('_s_tk_oId');
	console.log('$scope.outletId'+$scope.outletId);
	Number.prototype.padLeft = function(base,chr){
		var  len = (String(base || 10).length - String(this).length)+1;
		return len > 0? new Array(len).join(chr || '0')+this : this;
	};
	var monthNames = ["January", "February", "March", "April", "May", "June",
	                  "July", "August", "September", "October", "November", "December"
	                  ];
	$scope.sesssionValidation = function() {

		if (true) {
			$scope._s_tk_com = $cookieStore.get('_s_tk_com');

			$scope.sellControllerBean = SellControllerPreLoad.loadControllerData();
			
			if(!$scope.sellControllerBean.receiptConfigurationBean.starndardReceipt){
				$scope.standardReceipt = false;
			}
			$scope.getAllProducts();
			$rootScope.globalPageLoader = false;
			$scope.loadSalesDataAjax();
		}



	};
	$scope.processData = function(isReloadProductsCall) {
		$rootScope.sellLoadedFully = false;
		if($scope.sellControllerBean.isReturnEnableonSell == 'true')
		{$scope.isReturnEnableonSell = true;}
		else
		{$scope.isReturnEnableonSell = false;}
		if($scope.sellControllerBean.isInvoiceLevelDiscountEnable == 'true')
		{$scope.isInvoiceLevelDiscountEnable = true;}
		else
		{$scope.isInvoiceLevelDiscountEnable = false;}
		if($scope.sellControllerBean.isInvoiceDetailLevelDiscountEnable == 'true')
		{$scope.isInvoiceDetailLevelDiscountEnable = true;}
		else
		{$scope.isInvoiceDetailLevelDiscountEnable = false;}
		if($scope.sellControllerBean.isInvoiceTaxAmountEnable == 'true')
		{$scope.isInvoiceTaxAmountEnable = true;}
		else
			{$scope.isInvoiceTaxAmountEnable = false;}
		if($scope.sellControllerBean.priceBookBean != null && $scope.sellControllerBean.priceBookBean.length > 0)
			{
		$scope.pricebook = $scope.sellControllerBean.priceBookBean[0];
			}
		else
			{
			
			$scope.pricebook = null;
			}
		$scope.allProducts = $scope.sellControllerBean.productsBean;
		$scope.allCustomers = $scope.sellControllerBean.customersBean;
		$scope.allUsers = $scope.sellControllerBean.users;
		$scope.displayProductsBean = $scope.sellControllerBean.displayProductsBean;
		$scope.customerGroupBeansList = $scope.sellControllerBean.customerGroupBeansList;
		$scope.companyName = $scope.sellControllerBean.companyName;
		$scope.companyAddress = $scope.sellControllerBean.companyAddress;
		$scope.outletAddress = $scope.sellControllerBean.outletAddress;
		$scope.phoneNumber = $scope.sellControllerBean.phoneNumber;
		
		
		
		for (var i = 0; i < $scope.allProducts.length; i++) {
			if($scope.allProducts[i].productVariantsBeans){
				for (var j = 0; j < $scope.allProducts[i].productVariantsBeans.length; j++) {
					$scope.productVaraintDetailBeanList.push( $scope.allProducts[i].productVariantsBeans[j]);
				}	
			}

		}
		$scope.showProductLayout();
		$scope.invoiceDetails = [];
		
		
		localforage.getItem('_s_tk_sell').then(function(value) {
			if(value){
				$scope.InvoiceMainBean = value;

			}
			localforage.setItem('_s_tk_sell',"");
			if($scope.InvoiceMainBean)
			{
				$scope.InvoiceMainBean.settledAmt = $scope.InvoiceMainBean.settledAmt ? $scope.InvoiceMainBean.settledAmt:0;
				$scope.InvoiceMainBean.invoiceGivenAmt = parseFloat($scope.InvoiceMainBean.invoiceNetAmt) - parseFloat($scope.InvoiceMainBean.settledAmt);
				$scope.invoiceDetails =$scope.InvoiceMainBean.invoiceDetails;
				
				
				if($scope.InvoiceMainBean.customerId)
				{
					$scope.selectCustomer = true;
					$scope.selectCustomerName = $scope.InvoiceMainBean.customername;
					$scope.InvoiceMainBean.laybyamount = parseFloat($scope.InvoiceMainBean.laybyamount);
				}
			}

			if($scope.InvoiceMainBean && $scope.InvoiceMainBean.status == '10' && isReloadProductsCall == 'false') // Complete 
			{
				$scope.returnvalue = parseFloat(-1); // sale return
				$scope.InvoiceMainBean.invoiceGivenAmt = $scope.returnvalue * parseFloat($scope.InvoiceMainBean.invoiceNetAmt);
				$scope.itemDetailDisable = true;
				$scope.readonlyInvoiceDiscount = true;
			}

			if($scope.InvoiceMainBean && $scope.InvoiceMainBean.status == '9') // LayBy
			{
				$scope.itemDetailDisable = true;
				$scope.showPaymentSale = true;
				$scope.readonlyInvoiceDiscount = true;
				$scope.isReturnEnableonSell = false;
			}
			if($scope.returnvalue < 0)
			{
				$scope.isReturnEnableonSell = false;
			}

			if ($scope.InvoiceMainBean == ''
				|| typeof $scope.InvoiceMainBean == 'undefined') {
				$scope.InvoiceMainBean = {};
			}
		});

		$scope.loadingSalePageComplete = false;
		$rootScope.globalPageLoader = false;
		
		
	}
	
	$scope.loadSalesDataAjax = function() {
		$scope.loadingSalePageComplete = true;
		$rootScope.globalPageLoader = true;
		if($rootScope.online){
			$http.get('sell/getAllProductsOnly/' + $scope._s_tk_com).success(function(Response) {
				$scope.sellControllerBean = Response;
//				var restored = JSON.parse(pako.inflate(Response.data, { to: 'string' }));

				localforage.removeItem('allProducts').then(function() {
					localforage.setItem('allProducts', $scope.sellControllerBean);
				    console.log('allProducts is cleared!');
				    $scope.fetchAllCustomers();
				})['catch'](function(err) {
				    // This code runs if there were any errors
				    console.log(err);
				});
				$scope.processData('false');
				//$scope.allCustomers = $scope.sellControllerBean.customersBean;
			}).error(function(Response) {
				$scope.loading = false;
				$scope.error = true;
				$scope.errorMessage = $scope.systemBusy;
			});
		}else{
			$scope.processData('false');
		}
			
	};
	$scope.loadSalesProductDataOnlyAjax = function() {
		console.log("$scope.loadSalesProductDataOnlyAjax");
		//$scope.loadingSalePageComplete = true;
		//** Sales Speed Test **//
		if($rootScope.online){
			$http.get('sell/getAllProductsOnly/' + $scope._s_tk_com).success(function(Response) {
				$scope.sellControllerBean = Response;
//				var restored = JSON.parse(pako.inflate(Response.data, { to: 'string' }));

				localforage.removeItem('allProducts').then(function() {
					localforage.setItem('allProducts', $scope.sellControllerBean);
				    console.log('allProducts is cleared!');
				})['catch'](function(err) {
				    // This code runs if there were any errors
				    console.log(err);
				});
				$scope.processData('true');
				//$scope.allCustomers = $scope.sellControllerBean.customersBean;
			}).error(function(Response) {
				$scope.loading = false;
				$scope.error = true;
				$scope.errorMessage = $scope.systemBusy;
			});
		}else{
			$scope.processData('true');
		}
			
	};
	$scope.systemBusy = 'System is Busy Please try again';

	$scope.parkSale = function() {
		localforage.setItem('_s_tk_sell',"");
		$window.location = "/app/#/salesLedger";

	};
	$scope.showCustomerModal = false;
	$scope.customerModal = function() {
		$scope.showCustomerModal = true;
		$scope.searchCustomer = true;


	};
	$scope.createNewCustomerShow = function() {
		$scope.searchCustomer = false;
	};
	$scope.discounts = function() {
		if ($scope.showDiscount) {
			$scope.showDiscount = false;
		} else {
			$scope.showDiscount = true;
		}
	};

	$scope.layoutProducts = [];
	$scope.layoutProduct = {};
	$scope.showProductLayout = function() {
		$scope.layoutProducts = [];
		$scope.layoutProduct = {};
		for (var i = 0; i < $scope.displayProductsBean.length; i++) {
			$scope.layoutProduct = {};

			$scope.layoutProduct.productName = $scope.displayProductsBean[i].productName;
			$scope.layoutProduct.productId = $scope.displayProductsBean[i].productId;
			$scope.layoutProduct.currentInventory = $scope.displayProductsBean[i].currentInventory;
			$scope.layoutProduct.supplyPriceExclTax = $scope.displayProductsBean[i].supplyPriceExclTax;
			$scope.layoutProduct.retailPriceExclTax = $scope.displayProductsBean[i].retailPriceExclTax;
			$scope.layoutProduct.retailPriceInclTax = $scope.displayProductsBean[i].retailPriceInclTax;
			$scope.layoutProduct.orignalPrice = $scope.displayProductsBean[i].orignalPrice;

			$scope.layoutProduct.taxAmount = $scope.displayProductsBean[i].taxAmount;
			$scope.layoutProduct.varientProducts = $scope.displayProductsBean[i].varientProducts;
			$scope.layoutProduct.css = $scope.displayProductsBean[i].css;
			$scope.layoutProducts.push($scope.layoutProduct);

		}

	};

	$scope.showProductItemDetails = function(invoiceDetail) {
		for (var i = 0; i < $scope.InvoiceMainBean.invoiceDetails.length; i++) {
			if ($scope.InvoiceMainBean.invoiceDetails[i].productVarientName == invoiceDetail.productVarientName) {
				if ($scope.InvoiceMainBean.invoiceDetails[i].showDetails) {
					$scope.InvoiceMainBean.invoiceDetails[i].showDetails = false;
				} else {
					$scope.InvoiceMainBean.invoiceDetails[i].showDetails = true;
				}

			}

		}

	};

	$scope.invoiceDetail = {};
	$scope.selectProductFunc = function(product) {

		for (var i = 0; i < $scope.displayProductsBean.length; i++) {
			if (product.productId == $scope.displayProductsBean[i].productId) {
				$scope.productVaraintDetailBean = $scope.displayProductsBean[i].productVaraintDetailBean;
				$scope.productVariantsBeans = $scope.displayProductsBean[i].productVariantsBeans;

				if($scope.productVariantsBeans)
				{
					//for (var k = 0; k < $scope.productVariantsBeans.length; k++) {
					//if ($scope.productVariantsBeans[k].variantAttributeName == $scope.productVaraintAttrName) {
					//$scope.addProduct($scope.productVariantsBeans[i]);
					//}
					//}
					$scope.selectProduct = true;
				}
				else
				{
					$scope.addProduct(product);
				}

			}
		}
	};
	$scope.addProduct = function(productVarient) {


		$scope.duplicateProduct = false;
		$scope.invoiceDetail = {};
		//	if (productVarient.currentInventory && Number(productVarient.currentInventory) > 0)

		{
			$scope.parkDiscard = false;
			$scope.invoiceDetail = {};
			$scope.invoiceDetail.productName = productVarient.productName;
			$scope.invoiceDetail.productVarientName = productVarient.variantAttributeName;
			$scope.invoiceDetail.productVarientId = productVarient.productVariantId;
			$scope.invoiceDetail.productId = productVarient.productId;
			$scope.invoiceDetail.productInventoryCount = productVarient.currentInventory;
			$scope.invoiceDetail.productQuantity = 1;
			$scope.invoiceDetail.itemDiscountPrct = 0;
			$scope.invoiceDetail.itemNetPrice = 0;
			if(productVarient.varientProducts == 'false')
			{ 
				productVarient.tax = productVarient.taxAmount;
			}
			$scope.invoiceDetail.itemTaxAmount = productVarient.tax;
			$scope.invoiceDetail.itemRetailPrice = productVarient.retailPriceExclTax;
			$scope.invoiceDetail.itemSalePrice = $scope.invoiceDetail.itemRetailPrice;
			$scope.invoiceDetail.itemNetPrice = $scope.invoiceDetail.itemSalePrice;
			$scope.invoiceDetail.orignalPrice = productVarient.orignalPrice;

			/*if($scope.outletId==27){
				console.log('50% flat sale is on outlet id: '+$scope.outletId);
				$scope.invoiceDetail.itemDiscountPrct = parseFloat(50.00).toFixed(2);
			}else{
				$scope.applyPriceBook($scope.invoiceDetail,productVarient );
			}*/
			
			$scope.applyPriceBook($scope.invoiceDetail,productVarient);
			
			//$scope.itemsCount = $scope.itemsCount + 1;
			for (var i = 0; i < $scope.InvoiceMainBean.invoiceDetails.length; i++) {
				if (productVarient.varientProducts == 'false' && 
						$scope.invoiceDetails[i].productId == productVarient.productId) {

					$scope.invoiceDetails[i].productQuantity = parseFloat($scope.invoiceDetails[i].productQuantity) + 1;
					$scope.invoiceDetail = $scope.invoiceDetails[i];
					$scope.duplicateProduct = true;
					break;
				}
				else if (productVarient.varientProducts == 'true' && 
						$scope.invoiceDetails[i].productVarientId == productVarient.productVariantId) {

					$scope.invoiceDetails[i].productQuantity = parseFloat($scope.invoiceDetails[i].productQuantity) + 1;
					$scope.invoiceDetail = $scope.invoiceDetails[i];
					$scope.duplicateProduct = true;
					break;
				}


			}
			if($scope.duplicateProduct == false)
			{
				$scope.invoiceDetails.push($scope.invoiceDetail);
				$scope.InvoiceMainBean.invoiceDetails = $scope.invoiceDetails;

			}
			$scope.calculateBill(true, $scope.invoiceDetail, 'add');
			$scope.productquantityzero = false;
		}

//		else {
//		$scope.productquantityzero = true;
//		$scope.invoiceDetail.productInventoryCount = 0;
//		$scope.produtquantitymsg = 'Product not available in inventory!';
//		$timeout(function() {
//		$scope.productquantityzero = false;
//		}, 1500);
//		}

	};
	$scope.applyPriceBook = function(paramInvoiceDetail, paramProductVarient) {

		if($scope.pricebook != null && $scope.pricebook.priceBookDetailBeans !=null && $scope.pricebook.priceBookDetailBeans.length > 0)
		{	
			$scope.productBookDetail = {};
			$scope.productBookDetail = null;
			for(var i=0;i<$scope.pricebook.priceBookDetailBeans.length;i++){
				if($scope.pricebook.priceBookDetailBeans[i].productVariantId!=null && $scope.pricebook.priceBookDetailBeans[i].productVariantId!=''
					&& $scope.pricebook.priceBookDetailBeans[i].uuId==paramProductVarient.productVariantUuid){
					$scope.productBookDetail = $scope.pricebook.priceBookDetailBeans[i];
					break;
				}else if($scope.pricebook.priceBookDetailBeans[i].uuId==paramProductVarient.productUuid){
					$scope.productBookDetail = $scope.pricebook.priceBookDetailBeans[i];
					break;
				}
			}
			//$scope.productBookDetail = getBookDetailsByProduct($scope.pricebook.priceBookDetailBeans, paramProductVarient);
			if($scope.productBookDetail != null)
			{
				$scope.invoiceDetail.itemDiscountPrct = parseFloat($scope.productBookDetail.discount).toFixed(2);

			}
			
			else if($scope.pricebook.flatSale == 'true')
			{

				$scope.invoiceDetail.itemDiscountPrct = parseFloat($scope.pricebook.flatDiscount).toFixed(2);
			}

		}
		else if($scope.pricebook != null  && $scope.pricebook.flatSale == 'true')
		{

			$scope.invoiceDetail.itemDiscountPrct = parseFloat($scope.pricebook.flatDiscount).toFixed(2);
		}
		
		else
		{

			$scope.invoiceDetail.itemDiscountPrct = 0;

		}
	};

	function getBookDetailsByProduct(arr, productVarient) {

		var result  = arr.filter(function(o){return o.productId == productVarient.productId && o.productVariantId == productVarient.productVariantId;} );

		return result? result[0] : null; // or undefined

	}
	$scope.CalculateReturn = function(paramInvoiceDetail) {

		if(paramInvoiceDetail.isreturn == false){
			$scope.calculateBill(true, paramInvoiceDetail, 'add');
			$scope.InvoiceMainBean.invcTypeCde = '';
		}
		else 
		{
			$scope.calculateBill(false, paramInvoiceDetail,'remove');
			$scope.InvoiceMainBean.invcTypeCde = '00000'; // return invoice in case of return from sale screen
		}
	};

	$scope.calculateBill = function(isDiscountPrctQtyCahnged,paramInvoiceDetail, transactionType) {

		$scope.InvoiceMainBean.itemsCount = parseFloat(0);
		$scope.totallineItemDiscount = parseFloat(0);
		if (Number(paramInvoiceDetail.productInventoryCount) < Number(paramInvoiceDetail.productQuantity) &&  transactionType == 'add' && (paramInvoiceDetail.isreturn === undefined || paramInvoiceDetail.isreturn == false )) {

			paramInvoiceDetail.productQuantity = paramInvoiceDetail.productInventoryCount;
			//$scope.calculateBill(isDiscountPrctQtyCahnged, paramInvoiceDetail,'add');
			$scope.productquantityzero = true;
			$scope.produtquantitymsg = 'Available Product Quantiy is : ' + paramInvoiceDetail.productQuantity;
			$timeout(function() {
				$scope.productquantityzero = false;
			}, 1500);



		}

		else if (Number(paramInvoiceDetail.productInventoryCount) >= Number(paramInvoiceDetail.productQuantity) || transactionType == 'remove' || $scope.returnvalue == -1 || paramInvoiceDetail.isreturn == true) {
			$scope.InvoiceMainBean.invoiceAmt = parseFloat(0);
			$scope.InvoiceMainBean.invoiceDiscountAmt = $scope.InvoiceMainBean.invoiceDiscountAmt ? $scope.InvoiceMainBean.invoiceDiscountAmt : parseFloat(0);
			$scope.InvoiceMainBean.invoiceTax =parseFloat(0);
			$scope.InvoiceMainBean.invoiceSubTotal = parseFloat(0);

			$scope.InvoiceMainBean.invoiceNetAmt = $scope.InvoiceMainBean.invoiceGivenAmt = parseFloat(0);;
			var orignalPrice = 0;
			for (var i = 0; i < $scope.InvoiceMainBean.invoiceDetails.length; i++) {

				/*orignalPrice = orignalPrice + parseFloat($scope.InvoiceMainBean.invoiceDetails[i].orignalPrice);



				if (!$scope.InvoiceMainBean.invoiceDetails[i].itemSalePrice) {
					$scope.InvoiceMainBean.invoiceDetails[i].itemSalePrice = 0;
				}

				if (!$scope.InvoiceMainBean.invoiceDetails[i].itemRetailPrice) {
					$scope.InvoiceMainBean.invoiceDetails[i].itemRetailPrice = 0;
				}

				if (!$scope.InvoiceMainBean.invoiceDetails[i].itemDiscountPrct) {
					$scope.InvoiceMainBean.invoiceDetails[i].itemDiscountPrct = 0;
				}

				if (!$scope.InvoiceMainBean.invoiceDetails[i].productQuantity
						|| $scope.InvoiceMainBean.invoiceDetails[i].productQuantity == 0) {
					$scope.InvoiceMainBean.invoiceDetails[i].productQuantity = 1;
				}*/


				if ($scope.InvoiceMainBean.invoiceDetails[i].productVarientName == paramInvoiceDetail.productVarientName) {

					// Fault fixing Start
					orignalPrice = orignalPrice + parseFloat($scope.InvoiceMainBean.invoiceDetails[i].orignalPrice);



					if (!$scope.InvoiceMainBean.invoiceDetails[i].itemSalePrice) {
						$scope.InvoiceMainBean.invoiceDetails[i].itemSalePrice = 0;
					}

					if (!$scope.InvoiceMainBean.invoiceDetails[i].itemRetailPrice) {
						$scope.InvoiceMainBean.invoiceDetails[i].itemRetailPrice = 0;
					}

					if (!$scope.InvoiceMainBean.invoiceDetails[i].itemDiscountPrct) {
						$scope.InvoiceMainBean.invoiceDetails[i].itemDiscountPrct = 0;
					}

					if (!$scope.InvoiceMainBean.invoiceDetails[i].productQuantity
							|| $scope.InvoiceMainBean.invoiceDetails[i].productQuantity == 0) {
						$scope.InvoiceMainBean.invoiceDetails[i].productQuantity = 1;
					}
					
					//Fault Fixing End
					
					if (isDiscountPrctQtyCahnged == true || $scope.InvoiceMainBean.invoiceDetails[i].isreturn == true) {


						if ($scope.InvoiceMainBean.invoiceDetails[i].itemDiscountPrct) {

							$scope.InvoiceMainBean.invoiceDetails[i].itemSalePrice = parseFloat($scope.InvoiceMainBean.invoiceDetails[i].itemRetailPrice)
							- ((parseFloat($scope.InvoiceMainBean.invoiceDetails[i].itemRetailPrice) * parseFloat($scope.InvoiceMainBean.invoiceDetails[i].itemDiscountPrct)) / 100)
							.toFixed(2);

							$scope.InvoiceMainBean.invoiceDetails[i].itemDiscountAmount = 
								parseFloat( ((parseFloat($scope.InvoiceMainBean.invoiceDetails[i].itemRetailPrice) * parseFloat($scope.InvoiceMainBean.invoiceDetails[i].itemDiscountPrct)) / 100)
										.toFixed(2))* parseFloat($scope.InvoiceMainBean.invoiceDetails[i].productQuantity);
						} else {

							$scope.InvoiceMainBean.invoiceDetails[i].itemSalePrice = parseFloat(
									$scope.InvoiceMainBean.invoiceDetails[i].itemRetailPrice)
									.toFixed(2);

						}
					} else {

						$scope.InvoiceMainBean.invoiceDetails[i].itemDiscountPrct = (100 - ((parseFloat($scope.InvoiceMainBean.invoiceDetails[i].itemSalePrice) * 100) / parseFloat($scope.InvoiceMainBean.invoiceDetails[i].itemRetailPrice))
								.toFixed(2)).toFixed(2);

					}

					$scope.InvoiceMainBean.invoiceDetails[i].itemNetPrice = (parseFloat($scope.InvoiceMainBean.invoiceDetails[i].itemSalePrice) * parseFloat($scope.InvoiceMainBean.invoiceDetails[i].productQuantity))
					.toFixed(2);
					if($scope.InvoiceMainBean.invoiceDetails[i].isreturn == true)
					{
						$scope.InvoiceMainBean.invoiceDetails[i].itemNetPrice = -1* $scope.InvoiceMainBean.invoiceDetails[i].itemNetPrice;
						$scope.InvoiceMainBean.invoiceDetails[i].itemSalePrice = -1* $scope.InvoiceMainBean.invoiceDetails[i].itemSalePrice;
					}



				}

				if(parseFloat($scope.InvoiceMainBean.invoiceDetails[i].productQuantity) > 0)
					{
				$scope.InvoiceMainBean.invoiceAmt = (parseFloat($scope.InvoiceMainBean.invoiceAmt) + parseFloat($scope.InvoiceMainBean.invoiceDetails[i].itemNetPrice) )
				.toFixed(2);
					}
				$scope.InvoiceMainBean.invoiceSubTotal = ((parseFloat($scope.InvoiceMainBean.invoiceSubTotal) + parseFloat($scope.InvoiceMainBean.invoiceDetails[i].itemRetailPrice))* parseFloat($scope.InvoiceMainBean.invoiceDetails[i].productQuantity))
				.toFixed(2);
				$scope.InvoiceMainBean.invoiceTax = ((parseFloat($scope.InvoiceMainBean.invoiceTax) + parseFloat($scope.InvoiceMainBean.invoiceDetails[i].itemTaxAmount)) * parseFloat($scope.InvoiceMainBean.invoiceDetails[i].productQuantity))
				.toFixed(2);
				

				$scope.InvoiceMainBean.invoiceNetAmt = (parseFloat($scope.InvoiceMainBean.invoiceAmt) 
						- parseFloat($scope.InvoiceMainBean.invoiceDiscountAmt) + parseFloat($scope.InvoiceMainBean.invoiceTax))
						.toFixed(2);

				$scope.InvoiceMainBean.settledAmt = $scope.InvoiceMainBean.settledAmt ? $scope.InvoiceMainBean.settledAmt : 0;

				$scope.InvoiceMainBean.invoiceGivenAmt = parseFloat($scope.InvoiceMainBean.invoiceNetAmt) - parseFloat( $scope.InvoiceMainBean.settledAmt);

				if(transactionType == 'remove' || $scope.returnvalue == -1)   //returnvalue -1 means return case
				{
					$scope.InvoiceMainBean.invoiceGivenAmt = $scope.returnvalue * parseFloat($scope.InvoiceMainBean.invoiceNetAmt);
					if (Number(paramInvoiceDetail.productQuantity) > Number(paramInvoiceDetail.orignalProductQuantity))
					{
						$scope.returnerrormsg = 'Cannot return more than purchased items.';
						$scope.returnerrormsgvisible = true;
						$timeout(function() {
							$scope.returnerrormsgvisible = false;
						}, 1500);

						paramInvoiceDetail.productQuantity = paramInvoiceDetail.orignalProductQuantity;
					}
					else
					{

						$scope.returnerrormsgvisible = false;
					}
				}
			}

			for (var i = 0; i < $scope.InvoiceMainBean.invoiceDetails.length; i++) {

				if($scope.InvoiceMainBean.invoiceDetails[i].itemDiscountAmount != undefined)
				{
					$scope.totallineItemDiscount  = parseFloat($scope.totallineItemDiscount) + parseFloat($scope.InvoiceMainBean.invoiceDetails[i].itemDiscountAmount);
				}
				
			
			}
			$scope.InvoiceMainBean.orignalPrice = orignalPrice ;
			if(!$scope.InvoiceMainBean.invoiceDiscountAmt)
				$scope.InvoiceMainBean.invoiceDiscountAmt = 0;
			if(!$scope.totallineItemDiscount)
				$scope.totallineItemDiscount = 0;
			//$scope.totalDiscount = (parseFloat($scope.InvoiceMainBean.invoiceDiscountAmt) + parseFloat($scope.totallineItemDiscount)).toFixed(2);
		}
		for (var i = 0; i < $scope.InvoiceMainBean.invoiceDetails.length; i++) {

			
			 if($scope.InvoiceMainBean.invoiceDetails[i].isreturn != true)
				{
				$scope.InvoiceMainBean.itemsCount = parseFloat($scope.InvoiceMainBean.itemsCount) + parseFloat($scope.InvoiceMainBean.invoiceDetails[i].productQuantity);
				}
			
		}
	};

	$scope.pay = function() {
		if ($scope.makePayment) {
			$scope.makePayment = false;
		} else {
			$scope.ValidateInvoiceItems();
			if($scope.isValidInvoice){
				$scope.makePayment = true;
			}
			
		}
	};
	$scope.ResetSale = function() {
		if ($scope.InvoiceMainBean) {

			$scope.InvoiceMainBean = {};
			$scope.InvoiceMainBean.invoiceDetails = [];
			$scope.listPayments = [];
			$scope.previousBalance = 0;
			$scope.totalBalance = 0;
			$scope.balance = 0;
			
			$scope.InvoiceMainBean.invoiceNetAmt=0;
			$scope.InvoiceMainBean.invoiceAmt = 0;
			$scope.InvoiceMainBean.invoiceGivenAmt = 0;
			$scope.InvoiceMainBean.settledAmt = 0;
			$scope.InvoiceMainBean.invoiceTax = 0;
			$scope.InvoiceMainBean.invoiceSubTotal = parseFloat(0);
		}
		//$scope.totalDiscount= parseFloat(0);
		$scope.InvoiceMainBean.itemsCount = parseFloat(0);
		$scope.invoiceDetails = [];
		$scope.airportName = [];
		localforage.setItem('_s_tk_sell',"");
		$scope.returnvalue = parseFloat(1);
		$scope.itemDetailDisable = false;
		$scope.gobackenable = true;
		$scope.searchCustomerSelected = false;
		$scope.selectCustomer = false;
		$scope.searchCustomer = true;
		$scope.selectCustomerName ="";
		$scope.InvoiceMainBean.customerId = "";
		$scope.readonlyInvoiceDiscount=false;
		if($scope.sellControllerBean.isReturnEnableonSell == 'true')
		{$scope.isReturnEnableonSell = true;}
		else
		{$scope.isReturnEnableonSell = false;}

	};
	$scope.ReceiptingDone = function() {

		$scope.ResetSale();
		$scope.makePayment = false;
		$scope.salesComplete = false;
		$scope.error = false;
		$scope.success = false;
		$scope.showDiscount = false;
		$scope.invoiceGenerationDteFE = "";
		$scope.fetchAllCustomers();
		//$route.reload();
	};
	$scope.calculateDiscount = function(isDiscountPrctCahnged) {

		if (isDiscountPrctCahnged == true) {


			if ($scope.InvoiceMainBean.invoiceDiscount === undefined || !$scope.InvoiceMainBean.invoiceDiscount ) {
				$scope.InvoiceMainBean.invoiceDiscount = parseFloat(0);
				$scope.InvoiceMainBean.invoiceDiscountAmt = parseFloat(0);
			} else {
				$scope.InvoiceMainBean.invoiceDiscountAmt = ((parseFloat($scope.InvoiceMainBean.invoiceAmt) * parseFloat($scope.InvoiceMainBean.invoiceDiscount)) / 100)
				.toFixed(2);
			}
		}

		
		else if (isDiscountPrctCahnged == false) // Discount amount changed
		{
			if (!$scope.InvoiceMainBean.invoiceDiscountAmt || $scope.InvoiceMainBean.invoiceDiscountAmt === undefined  ) {

				$scope.InvoiceMainBean.invoiceDiscountAmt = parseFloat(0);
			}
			$scope.InvoiceMainBean.invoiceDiscount = (((parseFloat($scope.InvoiceMainBean.invoiceDiscountAmt) / parseFloat($scope.InvoiceMainBean.invoiceAmt)) * 100)).toFixed(2);

		}

		$scope.InvoiceMainBean.invoiceNetAmt = (parseFloat($scope.InvoiceMainBean.invoiceAmt)
				- parseFloat($scope.InvoiceMainBean.invoiceDiscountAmt) + parseFloat($scope.InvoiceMainBean.invoiceTax))
				.toFixed(2);
		$scope.InvoiceMainBean.invoiceGivenAmt = $scope.InvoiceMainBean.invoiceNetAmt - parseFloat($scope.InvoiceMainBean.settledAmt) ;

	};

	$scope.removeProduct = function(invoiceDetail) {
		if (invoiceDetail) {

			//if((typeof(invoiceDetail.isreturn) == 'undefined') || invoiceDetail.isreturn == false)
			{
				$scope.InvoiceMainBean.invoiceDetails = $scope.InvoiceMainBean.invoiceDetails
				.filter(function(item) {
					return item !== invoiceDetail;
				});

				$scope.invoiceDetails = $scope.invoiceDetails
				.filter(function(itemdet) {
					return itemdet !== invoiceDetail;
				});
				
			//	$scope.itemsCount = $scope.itemsCount - invoiceDetail.productQuantity;
			}
			$scope.calculateBill(false, invoiceDetail,'remove');
			// $scope.InvoiceMainBean.invoiceAmt = 0;
			// $scope.InvoiceMainBean.invoiceDiscountAmt = 0;
			// $scope.InvoiceMainBean.invoiceTax =
			// $scope.InvoiceMainBean.invoiceNetAmt =
			// $scope.InvoiceMainBean.invoiceGivenAmt = 0;

		}
	};

	$scope.salesComplete = false;
	$scope.barcodeValues = {
			"selectproduct" : ""
	};
	$scope.InvoiceMainBeanList = [];
	$scope.payCash = function(paymentmethod) {
		$scope.success = false;
		$scope.error = false;
		$scope.loading = true;
		$scope.InvoiceMainBean.dailyRegesterId = $scope.dailyRegesterId;
		$scope.InvoiceMainBean.settledAmt = $scope.InvoiceMainBean.settledAmt ? $scope.InvoiceMainBean.settledAmt : 0;

		if (parseFloat($scope.InvoiceMainBean.invoiceGivenAmt) > (parseFloat($scope.InvoiceMainBean.invoiceNetAmt) - parseFloat($scope.InvoiceMainBean.settledAmt))) {

			$scope.InvoiceMainBean.settledAmt = parseFloat($scope.InvoiceMainBean.invoiceNetAmt) - parseFloat($scope.InvoiceMainBean.settledAmt);
			$scope.balance = 0;
		}
		
		
		else if (parseFloat($scope.InvoiceMainBean.invoiceNetAmt) != 0 && ($scope.InvoiceMainBean.invoiceGivenAmt == '' || $scope.InvoiceMainBean.invoiceGivenAmt == undefined ||$scope.InvoiceMainBean.invoiceGivenAmt == 'undefined' || parseFloat($scope.InvoiceMainBean.invoiceGivenAmt) == 0 ))
		{
		$scope.error = true;
		$scope.errorMessage = 'Sale with 0 Amount is not allowed.';
		$timeout(function() {
			$scope.error = false;
			$scope.errorMessage = false;
		}, 1500);
		return;
		
		}
		else if (parseFloat($scope.InvoiceMainBean.invoiceGivenAmt) <= (parseFloat($scope.InvoiceMainBean.invoiceNetAmt) - parseFloat($scope.InvoiceMainBean.settledAmt))) {
			$scope.InvoiceMainBean.settledAmt = parseFloat($scope.InvoiceMainBean.invoiceGivenAmt);


		}
		$scope.payment = {};
		if(!$scope.InvoiceMainBean.invoiceRefNbr)
		{
			var currentdate = new Date(); 
			var invoiceRefNbr = "SAL-" + currentdate.getDate() + 
			+ (currentdate.getMonth()+1)  +  
			+ currentdate.getFullYear() + 
			+ currentdate.getHours() +   
			+ currentdate.getMinutes()+  
			+ currentdate.getSeconds(); 
			$scope.InvoiceMainBean.invoiceRefNbr = invoiceRefNbr;
			//June 7, 2017 10:28 PM
			var d = new Date,
			dformat = [ monthNames[(d.getMonth())],
			            d.getDate().padLeft(),
			            d.getFullYear()].join(', ')+
			            ' ' +
			            [ d.getHours()% 12 == 0 ?  12 : d.getHours()% 12,
			              d.getMinutes().padLeft(),
			              d.getSeconds().padLeft(),
			              d.getHours() >= 12 ? 'PM' : 'AM'].join(':');
			$scope.invoiceGenerationDteFE = dformat;
		}
		if(paymentmethod == 'cash') 
		{

			$scope.payment.type = 'Payment (Cash)';
			$scope.InvoiceMainBean.paymentTypeId = 1;
			$scope.cashloading = true;

		}
		else  if(paymentmethod == 'creditcard') 
		{

			$scope.payment.type = 'Payment (Credit Card)';
			$scope.InvoiceMainBean.paymentTypeId = 2;
			$scope.creditloading = true;

		}


		$scope.payment.amount = parseFloat($scope.InvoiceMainBean.invoiceGivenAmt);

//		if($scope.returnvalue <= 0)
//		{

//		var d = new Date,
//		dformat = [ monthNames[(d.getMonth()+1)],
//		d.getDate().padLeft(),
//		d.getFullYear()].join(', ')+
//		' ' +
//		[ d.getHours()% 12,
//		d.getMinutes().padLeft(),
//		d.getSeconds().padLeft(),
//		d.getHours() >= 12 ? 'PM' : 'AM'].join(':');
//		$scope.invoiceGenerationDteFE = dformat;
//		}


		$scope.listPayments.push($scope.payment);

		if($scope.returnvalue <= 0) //return sale
		{
			$scope.InvoiceMainBean.returnvalue = 'returnsale';
			$scope.salesComplete = true;
			$scope.InvoiceMainBean.invoiceNetAmt = $scope.InvoiceMainBean.invoiceGivenAmt;
			$scope.InvoiceMainBean.settledAmt = $scope.InvoiceMainBean.invoiceNetAmt;
			$scope.InvoiceMainBean.invoiceGivenAmt = $scope.InvoiceMainBean.invoiceGivenAmt;
			$scope.InvoiceMainBean.invoiceAmt  = $scope.InvoiceMainBean.invoiceGivenAmt;
		}
		else
		{$scope.InvoiceMainBean.returnvalue = 'sale';}

		$http.post('sell/payCash/' + $scope._s_tk_com,$scope.InvoiceMainBean).success(function(Response) {
			$scope.loading = false;

			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {

				$scope.payCashSuccess(Response);
				$scope.loadSalesProductDataOnlyAjax();
				if(paymentmethod == 'cash') 
				{
					$scope.cashloading = false;	
				}
				else  if(paymentmethod == 'creditcard') 
				{
					$scope.creditloading = false;
				}

				$scope.sendMessage($scope.InvoiceMainBean.invoiceNetAmt);
			} else if ($scope.responseStatus == 'INVALIDSESSION'
				|| $scope.responseStatus == 'SYSTEMBUSY' || $scope.responseStatus == 'WARNING') {
				
				
				$scope.error = true;
				$scope.errorMessage = Response.data;
				$timeout(function(){
					$scope.error = false;
					$window.location = Response.layOutPath;
					$scope.cashloading = false;
					$scope.creditloading = false;
				    }, 1500);
				
			} else {
				$scope.error = true;
				$scope.errorMessage = Response.data;
				$timeout(function(){
					$scope.error = false;
					$window.location = Response.layOutPath;
					$scope.cashloading = false;
					$scope.creditloading = false;
									
				    }, 1500);
			}
		}).error(function(Response) {
			$rootScope.online = false;
			$scope.InvoiceMainBean.status = 'Completed';
			var date;
			date = new Date();
			date = date.getUTCFullYear() + '-' +
			('00' + (date.getUTCMonth()+1)).slice(-2) + '-' +
			('00' + date.getUTCDate()).slice(-2) + ' ' + 
			('00' + date.getUTCHours()).slice(-2) + ':' + 
			('00' + date.getUTCMinutes()).slice(-2) + ':' + 
			('00' + date.getUTCSeconds()).slice(-2);
			$scope.InvoiceMainBean.invoiceGenerationDte = date;

			$scope.invoiceGenerationDteFE = date;
			localforage.getItem('InvoiceMainBeanList').then(function(value) {
				if(value==null){
					$scope.InvoiceMainBeanList = [];
					$scope.InvoiceMainBeanList.push($scope.InvoiceMainBean);
					localforage.setItem('InvoiceMainBeanList', $scope.InvoiceMainBeanList);
					localforage.setItem('invoiceMainBeanNewList', $scope.InvoiceMainBeanList);

				}else{
					$scope.InvoiceMainBeanList = value;
					$scope.InvoiceMainBeanList.push($scope.InvoiceMainBean);
					localforage.setItem('InvoiceMainBeanList', $scope.InvoiceMainBeanList);
					localforage.setItem('invoiceMainBeanNewList', $scope.InvoiceMainBeanList);

				}
				$rootScope.InvoiceMainBeanList = value;
			});
			$scope.payCashSuccess(Response);
			$scope.cashloading = false;
			$scope.creditloading = false;

			$scope.loading = false;
			$scope.error = true;
			$scope.errorMessage = $scope.systemBusy;
		});
	};
	$scope.payCashSuccess = function(response){
		$scope.gobackenable = false;
		if(response != null)
		{
			$scope.successMessage = response.data;
			$scope.InvoiceMainBean = response.data;
		}
		localforage.setItem('_s_tk_sell',"");
		$scope.balance = parseFloat($scope.InvoiceMainBean.invoiceNetAmt) - parseFloat($scope.InvoiceMainBean.settledAmt);

		$scope.change = parseFloat(	$scope.InvoiceMainBean.invoiceGivenAmt ) - parseFloat($scope.InvoiceMainBean.settledAmt);
		$scope.InvoiceMainBean.invoiceGivenAmt =  parseFloat($scope.balance);
		if($scope.balance == 0)
		{
			$scope.salesComplete = true;

		}

		else
		{$scope.salesComplete = false;}
//		var barcode = new bytescoutbarcode128();
//		var space = "  ";
//		var value = "1";
//		var value1 = "2";
//		var value2 = "3";
		$scope.barcodeValues.selectproduct = $scope.InvoiceMainBean.invoiceRefNbr;
		//	JsBarcode("#barcode", "Hi world!");
		//	$("#barcode").JsBarcode($scope.barcodeValues.selectproduct);
		JsBarcode(".barcode").init();
//		if($scope.barcodeValues.selectproduct)
//		{
//		barcode
//		.valueSet($scope.barcodeValues.selectproduct);
//		barcode.setMargins(0, 0, 0, 0);
//		barcode.setBarWidth(6);

//		var width = barcode.getMinWidth();

//		barcode.setSize(width, 200);
//		barcode.setCaption("okokok", "Arial", 25);
//		var barcodeImage = document
//		.getElementById('barcodeImage');
//		barcodeImage.src = barcode.exportToBase64(width, 100, 0);
//		}

	};
	$scope.salenoncash = function(transactionType) {
		$scope.success = false;
		$scope.error = false;
		$scope.laybyloading = true;
		$scope.InvoiceMainBean.dailyRegesterId = $scope.dailyRegesterId;
		$scope.InvoiceMainBean.transactionType = transactionType;

		$scope.InvoiceMainBean.laybyamount =  parseFloat($scope.InvoiceMainBean.invoiceNetAmt) - parseFloat($scope.InvoiceMainBean.settledAmt);
		if($scope.InvoiceMainBean.laybyamount!=null || $scope.selectedItem.item.customerBalance>0){
			$scope.totalBalance = parseFloat($scope.InvoiceMainBean.laybyamount) + parseFloat($scope.selectedItem.item.customerBalance);
		}else if($scope.InvoiceMainBean.laybyamount==null){
			$scope.totalBalance = $scope.selectedItem.item.customerBalance
		}else if($scope.selectedItem.item.customerBalance==null){
			$scope.selectedItem.item.customerBalance = 0;
		}
		$scope.InvoiceMainBean.settledAmt = 0;

		if(!$scope.InvoiceMainBean.invoiceRefNbr)
		{
			var currentdate = new Date(); 
			var invoiceRefNbr = "SAL-" + currentdate.getDate() + 
			+ (currentdate.getMonth()+1)  +  
			+ currentdate.getFullYear() + 
			+ currentdate.getHours() +   
			+ currentdate.getMinutes()+  
			+ currentdate.getSeconds(); 
			$scope.InvoiceMainBean.invoiceRefNbr = invoiceRefNbr;
			var d = new Date,
			dformat = [ monthNames[(d.getMonth()+1)],
			            d.getDate().padLeft(),
			            d.getFullYear()].join(', ')+
			            ' ' +
			            [ d.getHours()% 12,
			              d.getMinutes().padLeft(),
			              d.getSeconds().padLeft(),
			              d.getHours() >= 12 ? 'PM' : 'AM'].join(':');
			$scope.invoiceGenerationDteFE = dformat;
		}
		$http.post('sell/salenoncash/' + $scope._s_tk_com,$scope.InvoiceMainBean).success(function(Response) {


			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				// $scope.InvoiceMainBean = {};
				//$scope.success = true;
				$scope.salenoncashSuccess(Response);
				$scope.loadSalesProductDataOnlyAjax();
				$scope.laybyloading = false;
				$scope.sendMessage($scope.InvoiceMainBean.invoiceNetAmt);

			} else if ($scope.responseStatus == 'INVALIDSESSION'
				|| $scope.responseStatus == 'SYSTEMBUSY') {
				$scope.error = true;
				$scope.errorMessage = Response.data;
				$window.location = Response.layOutPath;
			} else {
				$scope.error = true;
				$scope.errorMessage = Response.data;
			}
			
		}).error(function(Response) {
			if($rootScope.online){
				$scope.InvoiceMainBeanList.push($scope.InvoiceMainBean);
				localforage.setItem('InvoiceMainBeanList', $scope.InvoiceMainBeanList);
				localforage.setItem('invoiceMainBeanNewList', $scope.InvoiceMainBeanList);
				$scope.salenoncashSuccess(Response);
			}
			$scope.loading = false;
			$scope.error = true;
			$scope.errorMessage = $scope.systemBusy;
		});
	};
	$scope.salenoncashSuccess = function(response){
		$scope.gobackenable = false;

//		$scope.successMessage = response.data;
		localforage.setItem('_s_tk_sell',"");
		$scope.salesComplete = true;
		$scope.balance = parseFloat($scope.InvoiceMainBean.invoiceGivenAmt );

		if(response !=null)
		{
			$scope.InvoiceMainBean = response.data;
		}
		// $timeout(function() {
		// $scope.success = false;
		// $window.location = Response.layOutPath;
		// }, 1000);
		$scope.listPayments = [];
//		var barcode = new bytescoutbarcode128();
//		var space = "  ";
//		var value = "1";
//		var value1 = "2";
//		var value2 = "3";
		$scope.barcodeValues.selectproduct = $scope.InvoiceMainBean.invoiceMainId;
		JsBarcode(".barcode").init();
//		if($scope.barcodeValues.selectproduct)
//		{
//		barcode
//		.valueSet($scope.barcodeValues.selectproduct);
//		barcode.setMargins(4, 4, 4, 4);
//		barcode.setBarWidth(2);

//		var width = barcode.getMinWidth();

//		barcode.setSize(width, 100);

//		var barcodeImage = document
//		.getElementById('barcodeImage');
//		barcodeImage.src = barcode.exportToBase64(
//		width, 100, 0);
//		}

	};




	$scope.parkSale = function() {
		$scope.success = false;
		$scope.error = false;
		$scope.loading = true;
		$scope.InvoiceMainBean.dailyRegesterId = $scope.dailyRegesterId;
		if(!$scope.InvoiceMainBean.invoiceRefNbr)
		{
			var currentdate = new Date(); 
			var invoiceRefNbr = "SAL-" + currentdate.getDate() + 
			+ (currentdate.getMonth()+1)  +  
			+ currentdate.getFullYear() + 
			+ currentdate.getHours() +   
			+ currentdate.getMinutes()+  
			+ currentdate.getSeconds(); 
			$scope.InvoiceMainBean.invoiceRefNbr = invoiceRefNbr;
		}
		$http.post('sell/parkSale/' + $scope._s_tk_com,$scope.InvoiceMainBean).success(function(Response) {
			$scope.loading = false;

			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				// $scope.InvoiceMainBean = {};
				$scope.success = true;
				$scope.successMessage = Response.data;
				// $scope.salesComplete = true;
				$scope.ResetSale();
				$timeout(function() {
					$scope.success = false;
					$window.location = Response.layOutPath;
				}, 1500);

			} else if ($scope.responseStatus == 'INVALIDSESSION'
				|| $scope.responseStatus == 'SYSTEMBUSY') {
				$scope.error = true;
				$scope.errorMessage = Response.data;
				$window.location = Response.layOutPath;
			} else {
				$scope.error = true;
				$scope.errorMessage = Response.data;
			}
		}).error(function() {
			if($rootScope.online){
				$scope.InvoiceMainBeanList.push($scope.InvoiceMainBean);
				localforage.setItem('InvoiceMainBeanList', $scope.InvoiceMainBeanList);
				localforage.setItem('invoiceMainBeanNewList', $scope.InvoiceMainBeanList);
				$scope.success = true;
				$scope.successMessage = Response.data;
				// $scope.salesComplete = true;
				$scope.ResetSale();
				$timeout(function() {
					$scope.success = false;
					$window.location = Response.layOutPath;
				}, 1500);
			}
			$scope.loading = false;
			$scope.error = true;
			$scope.errorMessage = $scope.systemBusy;
		});
	};

	$scope.openRegister = function() {
		$scope.success = false;
		$scope.error = false;
		$scope.loadingOpenRegister = true;
		$scope.showCashModal = false;
		$http.post('sell/openRegister/' + $scope._s_tk_com).success(
				function(Response) {
					$scope.loading = false;
					$scope.responseData = Response.data;
					$scope.responseStatus = Response.status;
					if ($scope.responseStatus == 'SUCCESSFUL') {
						$scope.getAllProducts();
						$scope.registerOpen = true;
						$scope.loadingOpenRegister = false;
						localforage.setItem('_s_tk_sell',"");
						//$scope.InvoiceMainBean = {};
						if($scope.responseData != "You don't have rights to perform current operation!"){
							$scope.showCashModal = true;
						}
					} else if ($scope.responseStatus == 'INVALIDSESSION'
						|| $scope.responseStatus == 'SYSTEMBUSY') {
						$scope.error = true;
						$scope.errorMessage = Response.data;
						$window.location = Response.layOutPath;
					} else {
						$scope.error = true;
						$scope.errorMessage = Response.data;
					}
				}).error(function() {
					$scope.loading = false;
					$scope.error = true;
					$scope.errorMessage = $scope.systemBusy;
				});
	};
	$scope.allProducts = [];
	$scope.registerOpen = false;
	$scope.dailyRegesterId = "";
	$scope.getAllProducts = function() {
		/*if(!$rootScope.online){
			$scope.registerOpen = true;
			return;
		}*/
		if (typeof $scope.sellControllerBean.registerStatus != 'undefined') {
			if ($scope.sellControllerBean.registerStatus == 'true') {
				$scope.dailyRegesterId = $scope.sellControllerBean.dailyRegisterId;
				$scope.registerOpen = true;
			} else {
				$scope.registerOpen = false;
			}
		}
	};
	String.prototype.replaceAll = function(search, replacement) {
		var target = this;
		return target.replace(new RegExp(search, 'g'), replacement);
	};
	$scope.productVaraintDetailBean = {};
	$scope.productVaraintAttrName = "";
	$scope.attribute1Level = false;
	$scope.attribute2Level = true;
	$scope.attribute3Level = true;
	$scope.attribute1LevelFunc = function(data) {
		$scope.productVaraintAttrName = data + "/";
		$scope.attribute1Level = true;
		$scope.attribute2Level = false;
		$scope.attribute3Level = true;
		if($scope.productVaraintDetailBean.arrtibute2Values==null || $scope.productVaraintDetailBean.arrtibute2Values.length==0){
			for (var i = 0; i < $scope.productVariantsBeans.length; i++) {
				if ($scope.productVariantsBeans[i].variantAttributeName.replaceAll("/","").replaceAll(" ","") == $scope.productVaraintAttrName.replaceAll("/","").replaceAll(" ","")) {
					$scope.addProduct($scope.productVariantsBeans[i]);
					$scope.attribute2Level = true;
					$scope.attribute1Level = false;
				}
				$scope.selectProduct = false;
			}
			if(!$scope.attribute2Level){
				$scope.attribute2Level = true;
				$scope.attribute1Level = false;
				$scope.produtquantitymsg = 'Product not avilable, please contact Admin!';
				$scope.productquantityzero = true;
				$timeout(function() {
					$scope.productquantityzero = false;
				}, 2000);
			}
		}
	};
	$scope.attribute1Leve2Func = function(data) {
		$scope.productVaraintAttrName = $scope.productVaraintAttrName + data
		+ "/";
		$scope.attribute1Level = true;
		$scope.attribute2Level = true;
		$scope.attribute3Level = false;
		if($scope.productVaraintDetailBean.arrtibute3Values==null || $scope.productVaraintDetailBean.arrtibute3Values.length==0){
			for (var i = 0; i < $scope.productVariantsBeans.length; i++) {
				if ($scope.productVariantsBeans[i].variantAttributeName.replaceAll("/","").replaceAll(" ","") == $scope.productVaraintAttrName.replaceAll("/","").replaceAll(" ","")) {
					$scope.addProduct($scope.productVariantsBeans[i]);
					$scope.attribute3Level = true;
					$scope.attribute1Level = false;
				}
				$scope.selectProduct = false;
			}
			if(!$scope.attribute3Level){
				$scope.attribute3Level = true;
				$scope.attribute1Level = false;
				$scope.produtquantitymsg = 'Product not avilable, please contact Admin!';
				$scope.productquantityzero = true;
				$timeout(function() {
					$scope.productquantityzero = false;
				}, 2000);
			}
		}
	};
	$scope.aattribute1Leve3Func = function(data) {
		$scope.attribute1Level = false;
		$scope.attribute2Level = true;
		$scope.attribute3Level = true;
		$scope.productNotFound = true;
		$scope.productVaraintAttrName = $scope.productVaraintAttrName + data;
		for (var i = 0; i < $scope.productVariantsBeans.length; i++) {
			if ($scope.productVariantsBeans[i].variantAttributeName.replaceAll("/","").replaceAll(" ","") == $scope.productVaraintAttrName.replaceAll("/","").replaceAll(" ","")) {
				$scope.addProduct($scope.productVariantsBeans[i]);
				$scope.productNotFound = false;
			}
			$scope.selectProduct = false;
		}
		if($scope.productNotFound){
			$scope.produtquantitymsg = 'Product not avilable, please contact Admin!';
			$scope.productquantityzero = true;
			$timeout(function() {
				$scope.productquantityzero = false;
			}, 2000);
		}
	};
	$scope.productVariantsBeans = [];
	$scope.variantSkuFound =  false;
	$scope.autoCompleteOptions = {
			minimumChars : 1,
			dropdownHeight : '200px',
			data : function(term) {
				term = term.toLowerCase();
				$scope.productVariantsBeans = [];
				var productResults = _.filter($scope.allProducts, function(val) {
					return val.productName.toLowerCase().includes(term) || val.sku.toLowerCase().includes(term);

				});
				var customerResults = _.filter($scope.allCustomers, function(val) {

					if(val.phoneNumber){
						return val.firstName.toLowerCase().includes(term) || val.phoneNumber.toLowerCase().includes(term);
					}else{
						if(val.firstName)
						{
							return val.firstName.toLowerCase().includes(term);
						}
					}

				});
				var productVariantResults = _.filter($scope.productVaraintDetailBeanList, function(val) {
					var skuLowercase =  val.sku.toLowerCase();
					return skuLowercase == term;

				});
				if(productVariantResults && productVariantResults.length>0){
					$scope.addProduct(productVariantResults[0]);
					$scope.variantSkuFound =  true;
					$scope.airportName = [];
				}
				var list = productResults.concat(customerResults);
				return list.concat(productVariantResults);
			},
			renderItem : function(item) {
				if (typeof item.firstName == 'undefined') {
					if(!$scope.variantSkuFound){
						var result = {
								value : item.productName,
								label : $sce.trustAsHtml("<table class='auto-complete'>"
										+ "<tbody>" + "<tr>" + "<td style='width: 90%'>"
										+ item.productName + "</td>"
										+ "<td style='width: 10%'>"
										+ item.retailPriceExclTax + "</td>" + "</tr>"
										+ "</tbody>" + "</table>")
						};}
					else{

						$scope.variantSkuFound = false;
						return;
					}
				} else  {
					if(item.firstName && item.lastName && item.phoneNumber){
						var result = {
								value : item.firstName,
								label : $sce.trustAsHtml("<table class='auto-complete'>"
										+ "<tbody>" + "<tr>" + "<td style='width: 60%'>"
										+ item.firstName +' '+ item.lastName + "</td> <td style='width: 30%'> Mob:   "+item.phoneNumber+"</td>"
										+ "<td style='width: 10%'>" + 'Customer' + "</td>"
										+ "</tr>" + "</tbody>" + "</table>")
						};
					}
					else if(item.firstName && item.lastName){
						var result = {
								value : item.firstName,
								label : $sce.trustAsHtml("<table class='auto-complete'>"
										+ "<tbody>" + "<tr>" + "<td style='width: 90%'>"
										+ item.firstName +' '+ item.lastName + "</td>"
										+ "<td style='width: 10%'>" + 'Customer' + "</td>"
										+ "</tr>" + "</tbody>" + "</table>")
						};
					}else{
						var result = {
								value : item.firstName,
								label : $sce.trustAsHtml("<table class='auto-complete'>"
										+ "<tbody>" + "<tr>" + "<td style='width: 60%'>"
										+ item.firstName + "</td> <td style='width: 30%'> Mob:   "+item.phoneNumber+"</td>"
										+ "<td style='width: 10%'>" + 'Customer' + "</td>"
										+ "</tr>" + "</tbody>" + "</table>")
						};
					}
					
				}

				return result;
			},
			itemSelected : function(item) {
				$scope.selectedItem = item;
				if (typeof $scope.selectedItem.item.firstName == 'undefined') {

					for (var i = 0; i < $scope.allProducts.length; i++) {
						if ($scope.selectedItem.item.productId == $scope.allProducts[i].productId) {
							$scope.productVaraintDetailBean = $scope.allProducts[i].productVaraintDetailBean;
							$scope.productVariantsBeans = $scope.allProducts[i].productVariantsBeans;
						}
					}

					$scope.airport = item;
					if($scope.productVaraintDetailBean.arrtibute1Values==null || $scope.productVaraintDetailBean.arrtibute1Values.length==0){
						for (var i = 0; i < $scope.allProducts.length; i++) {
							if ($scope.allProducts[i].productName.replaceAll(" ","") == $scope.selectedItem.item.productName.replaceAll(" ","")) {
								$scope.addProduct($scope.allProducts[i]);

							}
						}
					}else{
						
						if($scope.sellControllerBean.autoCreateStandardVariant=='true'
							&& $scope.productVaraintDetailBean.arrtibute1Values[0]==$scope.sellControllerBean.defaultVariantName){
							$scope.addProduct($scope.productVariantsBeans[0]);
						}else{
							$scope.selectProduct = true;
						}
						
					}

				} else {
					$scope.selectCustomer = true;
					$scope.selectCustomerName = $scope.selectedItem.item.firstName
					+ ' ' + $scope.selectedItem.item.lastName;
					$scope.selectCustomerPhoneNumber = $scope.selectedItem.item.phoneNumber;
					$scope.InvoiceMainBean.customerId = $scope.selectedItem.item.customerId;
				}
				$scope.airportName = [];
			}
	};
	$scope.selectCustomer = false;
	$scope.autoCompleteOptionsCustomers = {
			minimumChars : 1,
			dropdownHeight : '200px',
			data : function(term) {
				term = term.toLowerCase();
				$scope.productVariantsBeans = [];

				var customerResults = _.filter($scope.allCustomers, function(val) {
					if(val.phoneNumber){
						return val.firstName.toLowerCase().includes(term) || val.phoneNumber.toLowerCase().includes(term);
					}else{
						return val.firstName.toLowerCase().includes(term);
					}


				});
				return customerResults;
			},
			renderItem : function(item) {

				if(item.firstName && item.lastName && item.phoneNumber){
					var result = {
							value : item.firstName,
							label : $sce.trustAsHtml("<table class='auto-complete'>"
									+ "<tbody>" + "<tr>" + "<td style='width: 60%'>"
									+ item.firstName +' '+ item.lastName + "</td> <td style='width: 30%'> Mob:   "+item.phoneNumber+"</td>"
									+ "<td style='width: 10%'>" + 'Customer' + "</td>"
									+ "</tr>" + "</tbody>" + "</table>")
					};
				}
				else if(item.firstName && item.lastName){
					
					var result = {
							value : item.firstName,
							label : $sce.trustAsHtml("<table class='auto-complete'>"
									+ "<tbody>" + "<tr>" + "<td style='width: 90%'>"
									+ item.firstName +' '+ item.lastName + "</td>"
									+ "<td style='width: 10%'>" + 'Customer' + "</td>"
									+ "</tr>" + "</tbody>" + "</table>")
					};
				}else{
					var result = {
							value : item.firstName,
							label : $sce.trustAsHtml("<table class='auto-complete'>"
									+ "<tbody>" + "<tr>" + "<td style='width: 60%'>"
									+ item.firstName + "</td> <td style='width: 30%'> Mob:   "+item.phoneNumber+"</td>"
									+ "<td style='width: 10%'>" + 'Customer' + "</td>"
									+ "</tr>" + "</tbody>" + "</table>")
					};
				}
				return result;
			},
			itemSelected : function(item) {
				$scope.selectedItem = item;
				$scope.CustomerName = "";
				$scope.selectCustomer = true;
				$scope.selectCustomerName = $scope.selectedItem.item.firstName + ' ' + $scope.selectedItem.item.lastName;
				$scope.selectCustomerPhoneNumber = $scope.selectedItem.item.phoneNumber;
				$scope.InvoiceMainBean.customerId = $scope.selectedItem.item.customerId;
				$scope.customerBalance=$scope.selectedItem.item.customerBalance;
				$scope.customerLayBy= $scope.selectedItem.item.customerlaybyAmount;
				if($scope.selectedItem.item.customerBalance!=null && $scope.selectedItem.item.customerBalance>0 && $scope.InvoiceMainBean.settledAmt>0){
					/*$scope.previousBalance =  parseFloat($scope.InvoiceMainBean.invoiceNetAmt)+ parseFloat($scope.selectedItem.item.customerBalance)-parseFloat($scope.InvoiceMainBean.settledAmt);*/

					  $scope.previousBalance = parseFloat($scope.selectedItem.item.customerBalance);
				}else if($scope.selectedItem.item.customerBalance!=null && $scope.selectedItem.item.customerBalance>0){
						$scope.previousBalance = parseFloat($scope.selectedItem.item.customerBalance);
						}else{
							$scope.previousBalance = 0;
						}

			}
	};
	$scope.salesUserBean = [];
	$scope.autoCompleteOptionsUsers = {
			minimumChars : 1,
			dropdownHeight : '200px',
			data : function(term) {
				term = term.toLowerCase();
				var userResults = _.filter($scope.allUsers, function(val) {
					return val.firstName.toLowerCase().includes(term) || val.lastName.toLowerCase().includes(term);
				});
				return userResults;
			},
			renderItem : function(item) {

				if(item.firstName && item.lastName && item.phoneNumber){
					var result = {
							value : item.firstName,
							label : $sce.trustAsHtml("<table class='auto-complete'>"
									+ "<tbody>" + "<tr>" + "<td style='width: 60%'>"
									+ item.firstName +' '+ item.lastName + "</td> <td style='width: 30%'> Mob:   "+item.phoneNumber+"</td>"
									+ "<td style='width: 10%'>" + 'Customer' + "</td>"
									+ "</tr>" + "</tbody>" + "</table>")
					};
				}
				else if(item.firstName && item.lastName){
					var result = {
							value : item.firstName,
							label : $sce.trustAsHtml("<table class='auto-complete'>"
									+ "<tbody>" + "<tr>" + "<td style='width: 90%'>"
									+ item.firstName +' '+ item.lastName + "</td>"
									+ "<td style='width: 10%'>" + 'User' + "</td>"
									+ "</tr>" + "</tbody>" + "</table>")
					};
				}else{
					var result = {
							value : item.firstName,
							label : $sce.trustAsHtml("<table class='auto-complete'>"
									+ "<tbody>" + "<tr>" + "<td style='width: 60%'>"
									+ item.firstName + "</td> <td style='width: 30%'> Mob:   "+item.phoneNumber+"</td>"
									+ "<td style='width: 10%'>" + 'User' + "</td>"
									+ "</tr>" + "</tbody>" + "</table>")
					};
				}

				return result;
			},
			itemSelected : function(item) {
				$scope.selectedItem = item;
				$scope.selectUserName = $scope.selectedItem.item.firstName + ' ' + $scope.selectedItem.item.lastName;
				$scope.InvoiceMainBean.salesUser = $scope.selectedItem.item.userId;
				$scope.salesUserBean = [];
			}
	};
	$scope.cancelCustomer = function() {
		$scope.showCustomerModal = false;
	};
	$scope.cancelUserAdd = function() {
		$scope.showUserModal = false;
	};
	$scope.sesssionValidation();

	$scope.newCustomerSuccessMessage = "Customer Added Successfuly!";
	$scope.customerBean = {};
	$scope.customerBean.addressBeanList = [];
	$scope.customerBean.addressBeanList[0] = {};
	$scope.customerBean.addressBeanList[1] = {};
	$scope.searchCustomerSelected = true;

	$scope.addNewUserSelected = function() {
		$scope.showUserModal = false;
	};
	$scope.addNewCustomerSelected = function() {

		$scope.customerBean = {};
		$scope.customerBean.addressBeanList = [];
		$scope.customerBean.addressBeanList[0] = {};
		$scope.customerBean.addressBeanList[1] = {};
		$scope.newCustomerSuccess = true;
		$scope.showCustomerModal = false;

		$timeout(function() {
			$scope.newCustomerSuccess = false;
		}, 2000);
	};
	
	$scope.addNewCustomer = function() {
		$scope.newCustomerSuccess = false;
		$scope.newCustomerError = false;
		$scope.loading = true;
		$scope.customerFound=false;
//		$scope.customerBean.companyId = "1";
		$scope.addCustomerProcessingButton = true;
		for (var i = 0; i < $scope.allCustomers.length; i++) {
			if($scope.allCustomers[i].phoneNumber==$scope.customerBean.addressBeanList[0].phone){
				$scope.customerFound=true;
			}
		}
		if($scope.customerFound){
			$scope.newCustomerError = true;
			$scope.newCustomerErrorMessage = "Customer already exist with same Phone Number!";
			
			$timeout(function() {
				$scope.newCustomerError = false;
				$scope.searchCustomer = true;
				$scope.addCustomerProcessingButton = false;
				$scope.loading = false;
				$scope.customerBean = {};
				$scope.customerBean.addressBeanList = [];
				$scope.customerBean.addressBeanList[0] = {};
				$scope.customerBean.addressBeanList[1] = {};
				
			}, 2000);
		
		}else{
			
			$scope.customerBean.addressBeanList[0].addressType = "Physical Address";
			$scope.customerBean.addressBeanList[1].addressType = "Postal Address";
				
			$http.post('newCustomer/addNewCustomer/' + $scope._s_tk_com,$scope.customerBean).success(
					function(Response) {
						$scope.loading = false;
						$scope.responseStatus = Response.status;
						if ($scope.responseStatus == 'SUCCESSFUL') {

							if ($scope.InvoiceMainBean == ''
								|| typeof $scope.InvoiceMainBean == 'undefined') {
								$scope.InvoiceMainBean = {};
							}
							$scope.newCustomerSuccess = true;
							$scope.fetchAllCustomers();
							$scope.selectCustomer = true;
							$scope.selectCustomerName = $scope.customerBean.firstName + ' ' + $scope.customerBean.lastName;
							$scope.selectCustomerPhoneNumber = $scope.customerBean.addressBeanList[0].phone;
							if($scope.selectCustomerName == 'undefined undefined' ){
								$scope.selectCustomerName = "Customer";
								
							}
							$scope.InvoiceMainBean.customerId = Response.data.customerId;
							$scope.customerBean = {};
							$scope.customerBean.addressBeanList = [];
							$scope.customerBean.addressBeanList[0] = {};
							$scope.customerBean.addressBeanList[1] = {};
							$timeout(function() {
								$scope.newCustomerSuccess = false;
								$scope.showCustomerModal = false;
								$scope.searchCustomerSelected = false;
								$scope.addCustomerProcessingButton = false;
								$scope.searchCustomer = true;

							}, 2000);

						} else if ($scope.responseStatus == 'INVALIDSESSION'
							|| $scope.responseStatus == 'SYSTEMBUSY') {
							$scope.newCustomerError = true;
							$scope.addCustomerProcessingButton = false;
							$scope.newCustomerErrorMessage = Response.data;
							$window.location = Response.layOutPath;
						} else {
							$scope.newCustomerError = true;
							$scope.addCustomerProcessingButton = false;
							$scope.newCustomerErrorMessage = Response.data;
						}
					}).error(function() {
						$scope.loading = false;
						$scope.addCustomerProcessingButton = false;
						$scope.newCustomerError = true;
						$scope.newCustomerErrorMessage = $scope.systemBusy;
					});
		}
	
	};
	$scope.fetchAllCustomers = function() {		
		$http.post('customers/getAllCustomersOrderBYPhone/' + $scope._s_tk_com).success(function(Response) {
			$scope.allCustomers = Response.data;
			localforage.getItem('allProducts').then(function(value) {
				var controllerData  = value;
				controllerData.customersBean = $scope.allCustomers;
				localforage.removeItem('allProducts').then(function() {
					localforage.setItem('allProducts', controllerData);
				    console.log('allProducts customer added!');
				})['catch'](function(err) {
				    // This code runs if there were any errors
				    console.log(err);
				});
				
				
				
				$rootScope.InvoiceMainBeanList = value;
			});
		}).error(function() {

		});
	};
	$scope.selectCustomerOrCreate = function() {
		//$scope.selectCustomer = true;
		$scope.searchCustomer = true;
		$scope.showCustomerModal = true;
		$scope.fetchAllCustomers();
	};
	
	
	$scope.selectSalesUser = function() {
		//$scope.selectCustomer = true;
		$scope.showUserModal = true;
	};
	$scope.cashManagmentBean = {};
	$scope.cashManagmentBean.cashType = "Cash";
	$scope.cashManagmentBean.cashManagmentType = "IN";
	$scope.cashManagmentBean.notes = "Opening float";

	$scope.cashInOut = function() {
		$http.post('cashManagement/cashInOut/' + $scope._s_tk_com,$scope.cashManagmentBean).success(
				function(Response) {
					$scope.responseStatus = Response.status;
					if ($scope.responseStatus == 'SUCCESSFUL') {
						$scope.showCashModal = false;
						$scope.cashManagmentList = Response.data;
						$scope.addCashProcessingButton = false;
						$scope.removeCashProcessingButton = false;
						$timeout(function(){
							$route.reload();
						}, 1000);
					} else if ($scope.responseStatus == 'SYSTEMBUSY'
						|| $scope.responseStatus == 'INVALIDUSER'
							|| $scope.responseStatus == 'ERROR'
								|| $scope.responseStatus == 'INVALIDSESSION') {
						$scope.error = true;
						$scope.errorMessage = Response.data;
						$window.location = Response.layOutPath;
					} else {
						$scope.error = true;
						$scope.errorMessage = Response.data;
					}

				}).error(function() {
					$scope.error = true;
					$scope.errorMessage = $scope.systemBusy;
				});

	};

$scope.ValidateInvoiceItems = function() {		
	$scope.isValidInvoice = true;
	for (var i = 0; i < $scope.InvoiceMainBean.invoiceDetails.length; i++) {

		if ($scope.InvoiceMainBean.invoiceDetails[i].productQuantity == 0) {

			$scope.isValidInvoice = false;
			break;
		}

	}
	if(!$scope.isValidInvoice)
	{
		$scope.productquantityzero = true;
		$scope.produtquantitymsg = 'Sale of Product with Zero Quantity Not Allowed';
		$timeout(function() {
			$scope.productquantityzero = false;
		}, 1500);
	}

};

	$scope.sendMessage = function(amount) {
		$scope.loading = true;
		$scope.sendSMSBean = {}; 
		$scope.sendSMSBean.customerSmsBeans = $scope.selectedCustomers;
		$scope.sendSMSBean.message = $scope.message;
		$scope.sendSMSBean.sendAllSms = $scope.sendAllSms;
		$scope.sendSingleSmsBean = {};
		$scope.sendSingleSmsBean.customerId = $scope.InvoiceMainBean.customerId;
		$scope.sendSingleSmsBean.amount = amount;
		$scope.sendSingleSmsBean.user = $rootScope.clientName;
		if($scope.InvoiceMainBean.customerId){
			$http.post('sendSms/sendSingleMessage/' + $scope._s_tk_com,$scope.sendSingleSmsBean).success(
					function(Response) {
						$scope.responseStatus = Response.status;
						if ($scope.responseStatus == 'SUCCESSFUL') {
						} else if ($scope.responseStatus == 'INVALIDSESSION'
							|| $scope.responseStatus == 'SYSTEMBUSY') {
							$window.location = Response.layOutPath;
						} else {
							$scope.newCustomerErrorMessage = Response.data;
						}
					}).error(function() {
						$scope.newCustomerErrorMessage = $scope.systemBusy;
					});
		}
		
	};
	
	$scope.deleteCustomerOrCreate = function() {
		//$scope.selectCustomer = true;
		$scope.selectCustomer = false;
		$scope.CustomerName = [];
		$scope.selectCustomerName = "";
	};

}];

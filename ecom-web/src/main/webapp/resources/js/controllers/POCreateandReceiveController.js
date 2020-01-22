'use strict';

/**
 * POCreateandReceiveController
 * @constructor
 */
var POCreateandReceiveController = ['$sce', '$filter','$scope', '$http', '$timeout', '$window','$route','$cookieStore','$rootScope','SessionService','POCreateandReceiveControllerPreLoad',function($sce, $filter,$scope, $http, $timeout, $window,$route,$cookieStore,$rootScope,SessionService,POCreateandReceiveControllerPreLoad) {
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.stockOrderId = "";
	$scope.grandTotal = "0";
	$scope.stockOrderBean = {};
	$scope.stockOrderBean.diliveryDueDate = new Date();
	$scope.HHMM = $filter('date')(new Date(), 'HH:mm');
	$scope.stockOrderBean.orderNo = "PO-"+String(parseInt(new Date().getMonth())+1)+"/"+new Date().getDate()+"/"+new Date().getFullYear()+ " " + $scope.HHMM;
	$scope.stockOrderBean.stockRefNo = "PO-"+String(parseInt(new Date().getMonth())+1)+"/"+new Date().getDate()+"/"+new Date().getFullYear()+ " " + $scope.HHMM;
	$scope.showConfirmDeletePopup = false;
	$scope.productVariantBean = {};
	$scope.productVariantBeansList = [];
	$scope.stockOrderDetailBean = {};
	$scope.delStockOrderDetailBean = {};
	$scope.stockOrderDetailBeansList = [];
	$scope.counter = 1;
	// product info declarions start
	$scope.gui = {};
	$scope.gui.trackingProduct = true;
	$scope.gui.varientProducts = false;
	$scope.gui.standardProduct = true;
	$scope.hideRefValues = false;
	$scope.productVariantMap = [];
	$scope.productMap = [];
	$scope.productSKU = '';
	$scope.skudisable = false;
	$scope.inputTypeScan = true;
	$scope.update = false;

	// product info declarions end
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.roleId = $cookieStore.get('_s_tk_rId');
			$scope.userOutletId = $cookieStore.get('_s_tk_oId');
			$scope.data = POCreateandReceiveControllerPreLoad.loadControllerData();
			$scope.fetchData();
			$scope.stockOrderBean.outletId= $scope.userOutletId;
			$scope.stockOrderBean.itemCount = 0;
			$scope.itemCountTotal = 0;
			$scope.stockOrderBean.recItemCount = 0;
			$scope.AllInOne();
		}
	};

	$scope.fetchData = function() {
		$rootScope.purchaseOrderDetailsLoadedFully = false;
		if($scope.data == 'NORECORDFOUND'){

			$scope.error = true;
			$scope.errorMessage = "No record found";
		}
		else if($scope.data == 'SYSTEMBUSY'){
			$scope.error = true;
			$scope.errorMessage = $scope.systemBusy;
		}
		else if($scope.data == 'INVALIDSESSION'){
			$scope.error = true;
			$scope.errorMessage = 'An exception occured while validating session !';
			$window.location = '/app/#/login';

		}
		else{

			if($scope.data.productVariantBeansList!=null){
				$scope.productVariantBeansList = $scope.data.productVariantBeansList;
			}
			if($scope.data.productBeansList != null){
				$scope.productBeansList = $scope.data.productBeansList;
				for (var i = 0; i < $scope.productVariantBeansList.length; i++) {
					$scope.productBeansList.push($scope.productVariantBeansList[i]);
				}
			}
			if($scope.data.productVariantMap!=null){
				$scope.productVariantMap = $scope.data.productVariantMap;
			}
			if($scope.data.productMap!=null){
				$scope.productMap = $scope.data.productMap;
			}
			if($scope.data.outletBeansList!=null){
				$scope.outletList = $scope.data.outletBeansList;
			}
			if($scope.data.stockOrderTypeBeansList!=null){
				$scope.stockOrderTypeBeansList = $scope.data.stockOrderTypeBeansList;
			}
			if($scope.data.supplierBeansList!=null){
				$scope.supplierList = $scope.data.supplierBeansList;
			}
			/*$scope.calculateItemCount();
			$scope.calculateRecItemCount();*/
		}
		$rootScope.globalPageLoader = false;
	};


	/*$scope.populateProducts = function(supplierId){
		if($scope.stockOrderBean.supplierId != "-1"){			
			$http.post('poCreateandReceive/getAllProducts/'+$scope._s_tk_com, supplierId)
			.success(function(Response) {
				$scope.responseStatus = Response.status;
				if ($scope.responseStatus == 'SUCCESSFUL') {
					$scope.productBeansList = Response.data;
				}
				else if($scope.responseStatus == 'SYSTEMBUSY'
					||$scope.responseStatus=='INVALIDUSER'
						||$scope.responseStatus =='ERROR'
							||$scope.responseStatus =='INVALIDSESSION'){
					$scope.error = true;
					$scope.errorMessage = Response.data;
					$window.location = Response.layOutPath;
				} else {
					$scope.error = true;
					$scope.errorMessage = Response.data;
				}

			}).error(function() {
				$rootScope.emergencyInfoLoadedFully = false;
				$scope.error = true;
				$scope.errorMessage  = $scope.systemBusy;
			});
		}	
	};

	 */
	/*$scope.selectFirst = function(){
		//$scope.productList = $scope.productVariantBeansList[0];
		$scope.productVariantBean = $scope.productVariantBeansList[0];	
	}*/


//	PurchaseOrder Create Start	




	$scope.addStockOrderUpdate = function() {		
		$scope.success = false;
		$scope.error = false;
		$scope.loading = true;
		$scope.stockOrderBean.statusId = "1"; // Initiated status at Stock Order Creation page
		$scope.stockOrderBean.stockOrderTypeId = "1"; // Supplier Order
		if ($scope.update == false){
			$http.post('purchaseOrder/addStockOrder/'+$scope._s_tk_com, $scope.stockOrderBean)
			.success(function(Response) {
				$scope.loading = false;
				$scope.responseStatus = Response.status;
				if ($scope.responseStatus == 'SUCCESSFUL') {
					$scope.productTypeBean = {};
					$scope.success = true;
					$scope.successMessage = "Request Processed successfully!";
					$scope.stockOrderId = Response.data;
					$scope.stockOrderBean.stockOrderId = angular.copy($scope.stockOrderId);
					$cookieStore.put('_ct_bl_ost',$scope.stockOrderBean);
					$scope.update = true;
					$scope.updateStockOrderDetail();
					/*$timeout(function(){
					$scope.success = false;
					angular.forEach($scope.supplierList, function(value,key){
						if(value.supplierId == $scope.stockOrderBean.supplierId){
							$scope.stockOrderBean.supplierName = value.supplierName;
						}
					});
					angular.forEach($scope.outletList, function(value,key){
						if(value.outletId == $scope.stockOrderBean.outletId){
							$scope.stockOrderBean.outletName = value.outletName;
						}
					});
					angular.forEach($scope.stockOrderTypeBeansList, function(value,key){
						if(value.stockOrderTypeId == $scope.stockOrderBean.stockOrderTypeId){
							$scope.stockOrderBean.stockOrderTypeDesc = value.stockOrderTypeDesc;
						}
					});
					$cookieStore.put('_ct_bl_ost',$scope.stockOrderBean);
					$window.location = Response.layOutPath;
				}, 1000);*/
				}
				else if($scope.responseStatus == 'SYSTEMBUSY'
					||$scope.responseStatus=='INVALIDUSER'
						||$scope.responseStatus =='ERROR'
							||$scope.responseStatus =='INVALIDSESSION'){
					$scope.error = true;
					$scope.errorMessage = Response.data;
					$window.location = Response.layOutPath;
				} else {
					$scope.error = true;
					$scope.errorMessage = Response.data;
				}

			}).error(function() {
				$rootScope.emergencyInfoLoadedFully = false;
				$scope.error = true;
				$scope.errorMessage  = $scope.systemBusy;
			});
		}else{
			$http.post('purchaseOrder/updateStockOrder/'+$scope._s_tk_com, $scope.stockOrderBean)
			.success(function(Response) {
				$scope.loading = false;
				$scope.responseStatus = Response.status;
				if ($scope.responseStatus == 'SUCCESSFUL') {
					$scope.productTypeBean = {};
					$scope.success = true;
					$scope.successMessage = "Request Processed successfully!";
					$scope.stockOrderId = Response.data;
					$scope.stockOrderBean.stockOrderId = angular.copy($scope.stockOrderId);
					$cookieStore.put('_ct_bl_ost',$scope.stockOrderBean);
					$scope.updateStockOrderDetail();
					/*$timeout(function(){
					$scope.success = false;
					angular.forEach($scope.supplierList, function(value,key){
						if(value.supplierId == $scope.stockOrderBean.supplierId){
							$scope.stockOrderBean.supplierName = value.supplierName;
						}
					});
					angular.forEach($scope.outletList, function(value,key){
						if(value.outletId == $scope.stockOrderBean.outletId){
							$scope.stockOrderBean.outletName = value.outletName;
						}
					});
					angular.forEach($scope.stockOrderTypeBeansList, function(value,key){
						if(value.stockOrderTypeId == $scope.stockOrderBean.stockOrderTypeId){
							$scope.stockOrderBean.stockOrderTypeDesc = value.stockOrderTypeDesc;
						}
					});
					$cookieStore.put('_ct_bl_ost',$scope.stockOrderBean);
					$window.location = Response.layOutPath;
				}, 1000);*/
				}
				else if($scope.responseStatus == 'SYSTEMBUSY'
					||$scope.responseStatus=='INVALIDUSER'
						||$scope.responseStatus =='ERROR'
							||$scope.responseStatus =='INVALIDSESSION'){
					$scope.error = true;
					$scope.errorMessage = Response.data;
					$window.location = Response.layOutPath;
				} else {
					$scope.error = true;
					$scope.errorMessage = Response.data;
				}

			}).error(function() {
				$rootScope.emergencyInfoLoadedFully = false;
				$scope.error = true;
				$scope.errorMessage  = $scope.systemBusy;
			});
		}
	};

	$scope.showConfirmReceiveStockOrder = function(){
		$scope.showConfirmReceivePopup = true;
	};	

	$scope.addStockOrderUpdateandReceive = function() {		
		$scope.success = false;
		$scope.error = false;
		$scope.loading = true;
		$scope.stockOrderBean.statusId = "1"; // Initiated status at Stock Order Creation page
		$scope.stockOrderBean.stockOrderTypeId = "1"; // Supplier Order
		$http.post('purchaseOrder/addStockOrder/'+$scope._s_tk_com, $scope.stockOrderBean)
		.success(function(Response) {
			$scope.loading = false;

			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.productTypeBean = {};
				$scope.success = true;
				$scope.successMessage = "Request Processed successfully!";
				$scope.stockOrderId = Response.data;
				$scope.showConfirmReceivePopup = false;
				$scope.stockOrderBean.stockOrderId = angular.copy($scope.stockOrderId);
				$cookieStore.put('_ct_bl_ost',$scope.stockOrderBean);
				$scope.updateAndReceiveStockOrderDetails();
				/*$timeout(function(){
					$scope.success = false;
					angular.forEach($scope.supplierList, function(value,key){
						if(value.supplierId == $scope.stockOrderBean.supplierId){
							$scope.stockOrderBean.supplierName = value.supplierName;
						}
					});
					angular.forEach($scope.outletList, function(value,key){
						if(value.outletId == $scope.stockOrderBean.outletId){
							$scope.stockOrderBean.outletName = value.outletName;
						}
					});
					angular.forEach($scope.stockOrderTypeBeansList, function(value,key){
						if(value.stockOrderTypeId == $scope.stockOrderBean.stockOrderTypeId){
							$scope.stockOrderBean.stockOrderTypeDesc = value.stockOrderTypeDesc;
						}
					});
					$cookieStore.put('_ct_bl_ost',$scope.stockOrderBean);
					$window.location = Response.layOutPath;
				}, 1000);*/
			}
			else if($scope.responseStatus == 'SYSTEMBUSY'
				||$scope.responseStatus=='INVALIDUSER'
					||$scope.responseStatus =='ERROR'
						||$scope.responseStatus =='INVALIDSESSION'){
				$scope.error = true;
				$scope.errorMessage = Response.data;
				$window.location = Response.layOutPath;
			} else {
				$scope.error = true;
				$scope.errorMessage = Response.data;
			}

		}).error(function() {
			$rootScope.emergencyInfoLoadedFully = false;
			$scope.error = true;
			$scope.errorMessage  = $scope.systemBusy;
		});

	};


//	Purchase Order Creation finished
	//$scope.productVariantBeansList;
	//$scope.productBeansList;	
	$scope.checkProductStatus = function(){
		var quantity = $scope.stockOrderDetailBean.orderProdQty;
		if($scope.productVariantBean.isVariant.toString() == "true"){
			$scope.addProduct();
		}
		else{
			$scope.hit = 0;
			if($scope.productVariantBean.isProduct.toString() == "true"){
				$scope.addProduct();
				$scope.hit++;
			}
			else{
				for (var i = 0; i < $scope.productVariantBeansList.length; i++) {
					var productVariant = null;
					if($scope.productVariantBean.productVariantId == $scope.productVariantBeansList[i].productId){
						productVariant = angular.copy($scope.productVariantBean);
						$scope.productVariantBean = angular.copy($scope.productVariantBeansList[i]);
						$scope.stockOrderDetailBean.orderProdQty = angular.copy(quantity);
						$scope.addProduct();
						$scope.productVariantBean = angular.copy(productVariant);
						$scope.hit++;
					}
				}
			}
			if($scope.hit < 1){
				$scope.productVariantBean.isProduct = "true";
				$scope.addProduct();
			}
		}
		$scope.AllInOne();
	};



	$scope.addProduct  = function() {
		$scope.dualEntry = false;
		var obj = $scope.productVariantBean;
		var productVariantBeantoReplace = {};		
		if($scope.stockOrderDetailBeansList.length > 0){
			if(obj.isProduct != "true"){
				angular.forEach($scope.stockOrderDetailBeansList, function(value,key){
					if(value.productVariantId == obj.productVariantId && value.isProduct != "true" && obj.isProduct != "true"){
						if(isNaN(value.orderProdQty)){
							value.orderProdQty = "0";
						}
						value.orderProdQty = parseInt(value.orderProdQty) + parseInt($scope.stockOrderDetailBean.orderProdQty);
						if(value.ordrSupplyPrice > 0){
							value.total = value.ordrSupplyPrice * value.orderProdQty;
							if(isNaN(value.total)){
								value.total = "0";
							}
						}
						else{
							value.total = "0";
						}
						productVariantBeantoReplace =angular.copy(value);;
						var index = $scope.stockOrderDetailBeansList.indexOf(value);
						$scope.stockOrderDetailBeansList.splice(index, 1);
						productVariantBeantoReplace.isDirty = true;
						$scope.stockOrderDetailBeansList.unshift(productVariantBeantoReplace);
						//$scope.arrangeOrder();
						$scope.dualEntry = true;
					}
				});
				if($scope.dualEntry != true){
					$scope.stockOrderDetailBean.productVariantId = obj.productVariantId;
					$scope.stockOrderDetailBean.variantAttributeName = obj.variantAttributeName;
					$scope.stockOrderDetailBean.productVariantCurrInventory = obj.currentInventory;
					$scope.stockOrderDetailBean.isProduct = obj.isProduct;
					if(obj.supplyPriceExclTax != null){
						$scope.stockOrderDetailBean.ordrSupplyPrice = ""; 
						$scope.stockOrderDetailBean.ordrSupplyPrice = angular.copy(obj.supplyPriceExclTax);
					}
					$scope.stockOrderDetailBean.total = $scope.stockOrderDetailBean.ordrSupplyPrice * $scope.stockOrderDetailBean.orderProdQty;
					if(isNaN($scope.stockOrderDetailBean.total)){
						$scope.stockOrderDetailBean.total = "0";
					}
					$scope.stockOrderDetailBean.order = $scope.counter;
					$scope.counter++;
					$scope.stockOrderDetailBean.stockOrderId = $scope.stockOrderBean.stockOrderId;
					$scope.stockOrderDetailBean.isDirty = true;
					$scope.stockOrderDetailBeansList.unshift($scope.stockOrderDetailBean);
					$scope.dualEntry = false;
				}
			}
			else{
				angular.forEach($scope.stockOrderDetailBeansList, function(value,key){
					if(value.productVariantId == obj.productVariantId && value.isProduct == "true"){
						if(isNaN(value.orderProdQty)){
							value.orderProdQty = "0";
						}
						value.orderProdQty = parseInt(value.orderProdQty) + parseInt($scope.stockOrderDetailBean.orderProdQty);
						if(value.ordrSupplyPrice > 0){
							value.total = value.ordrSupplyPrice * value.orderProdQty;
							if(isNaN(value.total)){
								value.total = "0";
							}
						}
						else{
							value.total = "0";
						}
						productVariantBeantoReplace =angular.copy(value);;
						var index = $scope.stockOrderDetailBeansList.indexOf(value);
						$scope.stockOrderDetailBeansList.splice(index, 1);
						productVariantBeantoReplace.isDirty = true;
						$scope.stockOrderDetailBeansList.unshift(productVariantBeantoReplace);
						//$scope.arrangeOrder();
						$scope.dualEntry = true;
					}
				});
				if($scope.dualEntry != true){
					$scope.stockOrderDetailBean.productVariantId = obj.productVariantId;
					$scope.stockOrderDetailBean.variantAttributeName = obj.variantAttributeName;
					$scope.stockOrderDetailBean.productVariantCurrInventory = obj.currentInventory;
					$scope.stockOrderDetailBean.isProduct = obj.isProduct;
					if(obj.supplyPriceExclTax != null){
						$scope.stockOrderDetailBean.ordrSupplyPrice = ""; 
						$scope.stockOrderDetailBean.ordrSupplyPrice = angular.copy(obj.supplyPriceExclTax);
					}
					$scope.stockOrderDetailBean.total = $scope.stockOrderDetailBean.ordrSupplyPrice * $scope.stockOrderDetailBean.orderProdQty;
					if(isNaN($scope.stockOrderDetailBean.total)){
						$scope.stockOrderDetailBean.total = "0";
					}
					$scope.stockOrderDetailBean.order = $scope.counter;
					$scope.counter++;
					$scope.stockOrderDetailBean.stockOrderId = $scope.stockOrderBean.stockOrderId;
					$scope.stockOrderDetailBean.isDirty = true;
					$scope.stockOrderDetailBeansList.unshift($scope.stockOrderDetailBean);
					$scope.dualEntry = false;
				}
			}
		}
		else{
			$scope.stockOrderDetailBean.productVariantId = obj.productVariantId;		
			$scope.stockOrderDetailBean.variantAttributeName = obj.variantAttributeName;
			$scope.stockOrderDetailBean.productVariantCurrInventory = obj.currentInventory;
			$scope.stockOrderDetailBean.isProduct = obj.isProduct;
			if(obj.supplyPriceExclTax != null){
				$scope.stockOrderDetailBean.ordrSupplyPrice = ""; 
				$scope.stockOrderDetailBean.ordrSupplyPrice = angular.copy(obj.supplyPriceExclTax);
			}
			$scope.stockOrderDetailBean.total = $scope.stockOrderDetailBean.ordrSupplyPrice * $scope.stockOrderDetailBean.orderProdQty;
			$scope.stockOrderDetailBean.order = $scope.counter;
			$scope.counter++;
			$scope.stockOrderDetailBean.stockOrderId = $scope.stockOrderBean.stockOrderId;
			$scope.stockOrderDetailBean.isDirty = true;
			$scope.stockOrderDetailBeansList.unshift($scope.stockOrderDetailBean);
		}
		$scope.stockOrderDetailBean = {};
		/*$scope.calculateItemCount(); */
		$scope.airportName = [];
	};


	$scope.showConfirmDelete = function(stockOrderDetailBean){
		$scope.delStockOrderDetailBean = {};
		$scope.delStockOrderDetailBean = angular.copy(stockOrderDetailBean);
		$scope.showConfirmDeletePopup = true;
	};

	$scope.delStockOrderDetail = function(){
		if (typeof $scope.delStockOrderDetailBean.stockOrderDetailId != 'undefined') {
			$scope.error = false;
			$scope.loading = true;
			$http.post('purchaseOrderDetails/deleteStockOrderDetail/'+$scope._s_tk_com, $scope.delStockOrderDetailBean)
			.success(function(Response) {
				$scope.loading = false;					
				$scope.responseStatus = Response.status;
				if ($scope.responseStatus == 'SUCCESSFUL') {		
					$scope.loading = false;
				}
				else if($scope.responseStatus == 'SYSTEMBUSY'
					||$scope.responseStatus=='INVALIDUSER'
						||$scope.responseStatus =='ERROR'
							||$scope.responseStatus =='INVALIDSESSION'){
					$scope.error = true;
					$scope.errorMessage = Response.data;
					$window.location = Response.layOutPath;
				} else {
					$scope.error = true;
					$scope.errorMessage = Response.data;
				}
			}).error(function() {
				$rootScope.emergencyInfoLoadedFully = false;
				$scope.error = true;
				$scope.errorMessage  = $scope.systemBusy;
			});
		}
		angular.forEach($scope.stockOrderDetailBeansList, function(value,key){
			if(value.productVariantId == $scope.delStockOrderDetailBean.productVariantId && value.isProduct == $scope.delStockOrderDetailBean.isProduct){
				var index = $scope.stockOrderDetailBeansList.indexOf(value);
				$scope.stockOrderDetailBeansList.splice(index, 1);
			}
		});
		$scope.showConfirmDeletePopup = false; 
		$scope.delStockOrderDetailBean = {};
		$scope.AllInOne();
		/*$scope.arrangeOrder();
		$scope.calculateGrandTotal();
		$scope.calculateItemCount();
		$scope.calculateRecItemCount();*/
	};

	$scope.AllInOne = function(){
		$scope.counter = 1;
		$scope.stockOrderBean.itemCount = 0;
		$scope.grandTotal = "0";
		$scope.stockOrderBean.recItemCount = 0;
		$scope.grandTotal = "0";				
		if ($scope.stockOrderDetailBeansList.length > 0) {
			for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
				//arrange Order
				$scope.stockOrderDetailBeansList[i].order = $scope.counter; 
				$scope.counter++;
				//Calculate Total for Item
				$scope.stockOrderDetailBeansList[i].total = $scope.stockOrderDetailBeansList[i].ordrSupplyPrice * $scope.stockOrderDetailBeansList[i].orderProdQty;			
				if(isNaN($scope.stockOrderDetailBeansList[i].total)){
					$scope.stockOrderDetailBeansList[i].total = "0";
				}
				//Calculate Total Items Count
				$scope.stockOrderBean.itemCount = parseInt($scope.stockOrderBean.itemCount) + parseInt($scope.stockOrderDetailBeansList[i].orderProdQty);
				if(isNaN($scope.stockOrderBean.itemCount)){
					$scope.stockOrderBean.itemCount = "0";
				}				
				//Calculate Total Recv for each Item
				$scope.stockOrderDetailBeansList[i].recvTotal = $scope.stockOrderDetailBeansList[i].recvSupplyPrice * $scope.stockOrderDetailBeansList[i].recvProdQty;
				if(isNaN($scope.stockOrderDetailBeansList[i].recvTotal)){
					$scope.stockOrderDetailBeansList[i].recvTotal = "0";
				}
				//Calculate Total Recv Items
				$scope.stockOrderBean.recItemCount = parseInt($scope.stockOrderBean.recItemCount) + parseInt($scope.stockOrderDetailBeansList[i].recvProdQty);
				if(isNaN($scope.stockOrderBean.recItemCount) || $scope.stockOrderBean.recItemCount == ""){
					$scope.stockOrderBean.recItemCount = "0";
				}
				//Calculate Total Grand Total
				$scope.grandTotal = parseFloat($scope.grandTotal) + parseFloat($scope.stockOrderDetailBeansList[i].recvTotal);
				if(isNaN($scope.grandTotal)){
					$scope.grandTotal = "0";
				}
			}
		}
	};

	/*$scope.arrangeOrder = function(){
		$scope.counter = 1;
		if ($scope.stockOrderDetailBeansList.length > 0) {
			for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
				$scope.stockOrderDetailBeansList[i].order = $scope.counter;
				$scope.counter++;
			}
		}
	};


	$scope.calculateTotal = function(productVariantId){
		angular.forEach($scope.stockOrderDetailBeansList, function(value,key){
			if(value.productVariantId == productVariantId){
				value.total = value.ordrSupplyPrice * value.orderProdQty;
				if(isNaN(value.total)){
					value.total = "0";
				}
			}
		});	
		$scope.calculateItemCount();
	}; */

	$scope.calculateTotalforItem = function(stockOrderDetailBean){
		angular.forEach($scope.stockOrderDetailBeansList, function(value,key){
			if(value.productVariantId == stockOrderDetailBean.productVariantId && value.isProduct == stockOrderDetailBean.isProduct){
				value.total = value.ordrSupplyPrice * value.orderProdQty;
				if(isNaN(value.total)){
					value.total = "0";
				}
				value.isDirty = true;
			}
		});	
		$scope.AllInOne();
	}; 

	/*$scope.calculateItemCount = function(){
		$scope.stockOrderBean.itemCount = 0;
		$scope.itemCountTotal = "0";
		if($scope.stockOrderDetailBeansList != null){
			for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
				$scope.stockOrderBean.itemCount = parseInt($scope.stockOrderBean.itemCount) + parseInt($scope.stockOrderDetailBeansList[i].orderProdQty);
				if(isNaN($scope.stockOrderBean.itemCount)){
					$scope.stockOrderBean.itemCount = "0";
				}
				if(isNaN($scope.stockOrderDetailBeansList[i].total)){
					$scope.stockOrderDetailBeansList[i].total = "0";
				}
				$scope.itemCountTotal = parseFloat($scope.itemCountTotal) + parseFloat($scope.stockOrderDetailBeansList[i].total);
				if(isNaN($scope.itemCountTotal)){
					$scope.itemCountTotal = "0";
				}
			}
		}
	};

	$scope.calculateRecItemCount = function(){
		$scope.stockOrderBean.recItemCount = 0;
		if($scope.stockOrderDetailBeansList != null){
			for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
				$scope.stockOrderBean.recItemCount = parseInt($scope.stockOrderBean.recItemCount) + parseInt($scope.stockOrderDetailBeansList[i].recvProdQty);
				if(isNaN($scope.stockOrderBean.recItemCount)){
					$scope.stockOrderBean.recItemCount = "0";
				}
			}
		}
	};

	$scope.calculateRecvTotal = function(productVariantId){
		angular.forEach($scope.stockOrderDetailBeansList, function(value,key){
			if(value.productVariantId == productVariantId){
				value.recvTotal = value.recvSupplyPrice * value.recvProdQty;
				if(isNaN(value.recvTotal)){
					value.recvTotal = "0";
				}
			}
		});
		$scope.calculateGrandTotal();
		$scope.calculateRecItemCount();
	}; */

	$scope.calculateRecvTotalforItem = function(stockOrderDetailBean){
		angular.forEach($scope.stockOrderDetailBeansList, function(value,key){
			if(value.productVariantId == stockOrderDetailBean.productVariantId && value.isProduct == stockOrderDetailBean.isProduct){
				value.recvTotal = value.recvSupplyPrice * value.recvProdQty;
				if(isNaN(value.recvTotal)){
					value.recvTotal = "0";
				}
				value.isDirty = true;
			}
		});
		$scope.AllInOne();
	};

	/*$scope.calculateGrandTotal = function(){
		$scope.grandTotal = "0";
		if ($scope.stockOrderDetailBeansList.length > 0) {
			for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
				if(isNaN($scope.stockOrderDetailBeansList[i].recvTotal)){
					$scope.stockOrderDetailBeansList[i].recvTotal = "0";
				}
				$scope.grandTotal = parseFloat($scope.grandTotal) + parseFloat($scope.stockOrderDetailBeansList[i].recvTotal);
				if(isNaN($scope.grandTotal)){
					$scope.grandTotal = "0";
				}
			}
		}
		$scope.calculateRecItemCount();
	}; */

	$scope.checkStockOrderDetailListforRec = function() {
		if ($scope.stockOrderDetailBeansList.length > 0) { // your question said "more than one element"
			for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
				if($scope.stockOrderDetailBeansList[i].recvProdQty == "" || $scope.stockOrderDetailBeansList[i].recvProdQty == null){
					return false;
				}
				else if($scope.stockOrderDetailBeansList[i].recvSupplyPrice == "" || $scope.stockOrderDetailBeansList[i].recvSupplyPrice == null){
					return false;
				}		   
			}
			return true;
			//$scope.calculateGrandTotal();
		}
		else {
			return false;
		}
	};

	$scope.checkStockOrderDetailList = function() {
		if ($scope.stockOrderDetailBeansList.length > 0) { // your question said "more than one element"
			for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
				if($scope.stockOrderDetailBeansList[i].orderProdQty == "" || $scope.stockOrderDetailBeansList[i].orderProdQty == null){
					return false;
				}
				else if($scope.stockOrderDetailBeansList[i].ordrSupplyPrice == "" || $scope.stockOrderDetailBeansList[i].ordrSupplyPrice == null){
					return false;
				}		   
			}
			return true;
		}
		else {
			return false;
		}
	};	

	$scope.markAllReceive = function(){
		if ($scope.stockOrderDetailBeansList.length > 0) {
			for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
				$scope.stockOrderDetailBeansList[i].recvProdQty = "";
				$scope.stockOrderDetailBeansList[i].recvProdQty = angular.copy($scope.stockOrderDetailBeansList[i].orderProdQty);
				$scope.stockOrderDetailBeansList[i].recvSupplyPrice = "";
				$scope.stockOrderDetailBeansList[i].recvSupplyPrice = angular.copy($scope.stockOrderDetailBeansList[i].ordrSupplyPrice);
				$scope.stockOrderDetailBeansList[i].recvTotal = $scope.stockOrderDetailBeansList[i].recvSupplyPrice * $scope.stockOrderDetailBeansList[i].recvProdQty;
				$scope.stockOrderDetailBeansList[i].isDirty = true;
				if(isNaN($scope.stockOrderDetailBeansList[i].total)){
					$scope.stockOrderDetailBeansList[i].total = "0";
				}
			}

		}
		//$scope.calculateGrandTotal();
		$scope.AllInOne();
	};


	$scope.updateStockOrderDetail = function(){
		if ($scope.stockOrderDetailBeansList.length > 0) {
			for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
				$scope.stockOrderDetailBeansList[i].stockOrderId = $scope.stockOrderId;
			}
			$scope.success = false;
			$scope.error = false;
			$scope.loading = true;
			$http.post('purchaseOrderDetails/updateStockOrderDetail/'+$scope._s_tk_com+'/'+$scope.grandTotal+'/'+$scope.stockOrderBean.itemCount, $scope.stockOrderDetailBeansList)
			.success(function(Response) {
				$scope.loading = false;					
				$scope.responseStatus = Response.status;
				if ($scope.responseStatus == 'SUCCESSFUL') {
					$scope.success = true;
					$scope.stockOrderDetailBeansList = Response.data;
					if ($scope.stockOrderDetailBeansList.length > 0) {
						for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
							$scope.stockOrderDetailBeansList[i].isDirty = false; 
						}
					}
					$scope.AllInOne();
					$cookieStore.put('_e_cOt_pio',"") ;
					$timeout(function(){
						$scope.success = false;
						//$window.location = Response.layOutPath;
					}, 2000);
				}
				else if($scope.responseStatus == 'SYSTEMBUSY'
					||$scope.responseStatus=='INVALIDUSER'
						||$scope.responseStatus =='ERROR'
							||$scope.responseStatus =='INVALIDSESSION'){
					$scope.error = true;
					$scope.errorMessage = Response.data;
					//$window.location = Response.layOutPath;
				} else {
					$scope.error = true;
					$scope.errorMessage = Response.data;
				}

			}).error(function() {
				$rootScope.emergencyInfoLoadedFully = false;
				$scope.error = true;
				$scope.errorMessage  = $scope.systemBusy;
			});
		}

	};


	$scope.updateAndReceiveStockOrderDetails = function(){
		if ($scope.stockOrderDetailBeansList.length > 0) {
			for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
				$scope.stockOrderDetailBeansList[i].stockOrderId = $scope.stockOrderId;
			}
			$scope.success = false;
			$scope.error = false;
			$scope.loading = true;
			$http.post('purchaseOrderDetails/updateAndReceiveStockOrderDetails/'+$scope._s_tk_com+'/'+$scope.grandTotal+'/'+$scope.stockOrderBean.recItemCount, $scope.stockOrderDetailBeansList)
			.success(function(Response) {
				$scope.loading = false;					
				$scope.responseStatus = Response.status;
				if ($scope.responseStatus == 'SUCCESSFUL') {
					$scope.success = true;
					$scope.successMessage = Response.data;
					$cookieStore.put('_e_cOt_pio',"") ;
					$timeout(function(){
						$scope.success = false;
						$window.location = Response.layOutPath;
					}, 2000);
				}
				else if($scope.responseStatus == 'SYSTEMBUSY'
					||$scope.responseStatus=='INVALIDUSER'
						||$scope.responseStatus =='ERROR'
							||$scope.responseStatus =='INVALIDSESSION'){
					$scope.error = true;
					$scope.errorMessage = Response.data;
					//$window.location = Response.layOutPath;
				} else {
					$scope.error = true;
					$scope.errorMessage = Response.data;
				}

			}).error(function() {
				$rootScope.emergencyInfoLoadedFully = false;
				$scope.error = true;
				$scope.errorMessage  = $scope.systemBusy;
			});
		}

	};


	$scope.autoCompleteOptions = {
			minimumChars : 1,
			dropdownHeight : '105px',
			data : function(term) {
				term = term.toLowerCase();
				if(term.length>15){
					$scope.airportName = [];
					document.getElementById("lov").disabled = false;
					document.getElementById("lov").focus();
					return
				}
				$scope.productVariantsBeans = [];
				document.getElementById("lov").disabled = true;
				var customerResults = _.filter($scope.productBeansList, function(val) {
					return val.variantAttributeName.toLowerCase().includes(term) || val.sku.toLowerCase().includes(term);
				});				
				var customerVariantResults = _.filter($scope.productBeansList, function(val) {
					var skuLowercase =  val.sku.toLowerCase();
					return skuLowercase == term;

				});
				if(customerResults.length == 0){
					customerResults = _.filter($scope.allProductBeansList, function(val) {
						return val.variantAttributeName.toLowerCase().includes(term) || val.sku.toLowerCase().includes(term);
					});				
					customerVariantResults = _.filter($scope.allProductBeansList, function(val) {
						var skuLowercase =  val.sku.toLowerCase();
						return skuLowercase == term;

					});	
				}
				if(customerVariantResults && customerVariantResults.length>0){
					$scope.hideRefValues = true;
					$scope.stockOrderDetailBean.orderProdQty = 1;
					$scope.productVariantBean = customerVariantResults[0];
					$scope.checkProductStatus();
					//document.getElementById("lov").disabled = false;
					$scope.selectedItem = {};
					$scope.selectedItem.item = customerVariantResults[0];
//					$scope.variantSkuFound =  true;
					$scope.airportName = [];
					document.getElementById("lov").disabled = false;
					document.getElementById("lov").focus();
				}else{
					if(term.length>15){
						$scope.airportName = [];
					}
					//$scope.airportName = [];
					document.getElementById("lov").disabled = false;
					document.getElementById("lov").focus();
					$scope.hideRefValues = false;
				}
				////document.getElementById("lov").disabled = false;
				return customerResults;
			},
			renderItem : function(item) {
				var result = [];
				if($scope.hideRefValues == false){
					result = {
							value : item.variantAttributeName,
							label : $sce.trustAsHtml("<table class='auto-complete'>"
									+ "<tbody>" + "<tr>" + "<td style='width: 90%'>"
									+ item.variantAttributeName + "</td>"
									+ "<td style='width: 10%'>" + "</td>"
									+ "</tr>" + "</tbody>" + "</table>")
					};
				}
				else{

					result = null;
				}
				return result;
			},
			itemSelected : function(item) {
				//document.getElementById("lov").disabled = false;
				$scope.productVariantBean = item.item;
				//	$scope.airportName = [];

			}
	};

//	Add Variants code start
	$scope.editVariant = function(){
		console.log($scope.productVariantBean);
		if($scope.productVariantBean==null || $scope.productVariantBean.productId==null||$scope.productVariantBean.outletId==null){
			return;
		}
		$scope.loading = true;	
		$http.post('newProduct/getNewProductControllerData/'+$cookieStore.get('_s_tk_com')+'/'+$scope.productVariantBean.productId+'/'+$scope.productVariantBean.outletId)
		.success(function(Response) {
			$scope.loading = false;					
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.response = Response.data;
				$scope.productVariantsCollection = [];
				$scope.productVariantValuesCollection = [];
				$scope.productVariantValuesCollectionOne = [];
				$scope.productVariantValuesCollectionTwo = [];
				$scope.productVariantValuesCollectionThree = [];
				$scope.valueCollectionOne = [];
				$scope.valueCollectionTwo = [];
				$scope.valueCollectionThree = [];
				$scope.productVariantValuesCollectionOldOne = [];
				$scope.productVariantValuesCollectionOldTwo = [];
				$scope.productVariantValuesCollectionOldThree = [];

				$scope.myObj = {};
				$scope.myObj.id = "1";
				$scope.myObj.value = "";
				$scope.myObj.varientAttributeId = "";
				$scope.addVariantsEntertained = false;
				$scope.productVariantsCollection.push($scope.myObj);
				if($scope.response!=null){


					if($scope.response.supplierBeans!=null){
						$scope.supplierList = $scope.response.supplierBeans;
					}
					if($scope.response.productTypeBeanList!=null){
						$scope.productTypeList = $scope.response.productTypeBeanList;
					}
					if($scope.response.brandBeanList!=null){
						$scope.brandList = $scope.response.brandBeanList;
					}
					if($scope.response.outletBeans!=null){
						if($scope.response.productBean.outletBeans!=null){
							$scope.outletList = $scope.response.productBean.outletBeans;
						}else{
							$scope.outletList = $scope.response.outletBeans;
						}
						for(var i=0;i<$scope.outletList.length;i++){
							$scope.outletList[i].taxAmount = 0.0;
							$scope.outletList[i].retailPrice = 0.0;
						}

						$scope.verientsOutletList = $scope.response.outletBeans;
					}
					if($scope.response.variantAttributeBeanList!=null){
						$scope.varientAttributesList = null;
						$scope.varientAttributesList = $scope.response.variantAttributeBeanList;
					}

					/*if($scope.data.productVariantBeanList!=null){
						$scope.productVariantBeanList = null;
						$scope.productVariantBeanList = $scope.data.productVariantBeanList;
					}*/
					if($scope.response.productBean!=null){
						$scope.productBean = {};
						$scope.tempproductBean = {};
						$scope.productBean = $scope.response.productBean;
						$scope.tempproductBean = angular.copy($scope.response.productBean);
						$scope.compositProductCollection = angular.copy($scope.productBean.compositProductCollection);
						if($scope.productBean.productVariantsBeans.length>0){
							//$scope.hideAddVariantAttributesPanel = true;
							var count = count_letters($scope.productBean.productVariantsBeans[0].variantAttributeName,'/');
							if(count==1){
								$scope.addAttributeDynamically();
								$scope.productVariantsCollection[0].varientAttributeId = $scope.productBean.productVariantsBeans[0].variantAttributeId1; 
								$scope.productVariantsCollection[1].varientAttributeId = $scope.productBean.productVariantsBeans[0].variantAttributeId2; 
								for(var i=0;i<$scope.productBean.productVariantsBeans.length;i++){
									var attributeList = $scope.productBean.productVariantsBeans[i].variantAttributeName.split('/');
									if(!varifyValueExist($scope.productVariantValuesCollectionOne,attributeList[0])){
										$scope.tempObject = {};
										$scope.tempObject.value = attributeList[0];
										$scope.tempObject.varientAttributeValueId = $scope.productVariantValuesCollectionOne.length+1;
										$scope.productVariantValuesCollectionOne.push($scope.tempObject);
										$scope.valueCollectionOne.push($scope.tempObject.value);
										$scope.tempObject = {};
									}
									if(!varifyValueExist($scope.productVariantValuesCollectionTwo,attributeList[1])){
										$scope.tempObject.value = attributeList[1];
										$scope.tempObject.varientAttributeValueId = $scope.productVariantValuesCollectionTwo.length+1;
										$scope.productVariantValuesCollectionTwo.push($scope.tempObject);
										$scope.valueCollectionTwo.push($scope.tempObject.value);
										$scope.tempObject = {};
									}
								}


							}else if(count==2){
								$scope.addAttributeDynamically();
								$scope.addAttributeDynamically();
								$scope.productVariantsCollection[0].varientAttributeId = $scope.productBean.productVariantsBeans[0].variantAttributeId1; 
								$scope.productVariantsCollection[1].varientAttributeId = $scope.productBean.productVariantsBeans[0].variantAttributeId2;
								$scope.productVariantsCollection[2].varientAttributeId = $scope.productBean.productVariantsBeans[0].variantAttributeId3;
								for(var i=0;i<$scope.productBean.productVariantsBeans.length;i++){
									var attributeList = $scope.productBean.productVariantsBeans[i].variantAttributeName.split('/');
									if(!varifyValueExist($scope.productVariantValuesCollectionOne,attributeList[0])){
										$scope.tempObject = {};
										$scope.tempObject.value = attributeList[0];
										$scope.tempObject.varientAttributeValueId = $scope.productVariantValuesCollectionOne.length+1;
										$scope.productVariantValuesCollectionOne.push($scope.tempObject);
										$scope.valueCollectionOne.push($scope.tempObject.value);
										$scope.tempObject = {};
									}
									if(!varifyValueExist($scope.productVariantValuesCollectionTwo,attributeList[1])){
										$scope.tempObject.value = attributeList[1];
										$scope.tempObject.varientAttributeValueId = $scope.productVariantValuesCollectionTwo.length+1;
										$scope.productVariantValuesCollectionTwo.push($scope.tempObject);
										$scope.valueCollectionTwo.push($scope.tempObject.value);
										$scope.tempObject = {};
									}
									if(!varifyValueExist($scope.productVariantValuesCollectionThree,attributeList[2])){
										$scope.tempObject.value = attributeList[2];
										$scope.tempObject.varientAttributeValueId = $scope.productVariantValuesCollectionThree.length+1;
										$scope.productVariantValuesCollectionThree.push($scope.tempObject);
										$scope.valueCollectionThree.push($scope.tempObject.value);
										$scope.tempObject = {};
									}
								}
							}else{
								$scope.productVariantsCollection[0].varientAttributeId = $scope.productBean.productVariantsBeans[0].variantAttributeId1; 
								for(var i=0;i<$scope.productBean.productVariantsBeans.length;i++){

									$scope.tempObject = {};
									$scope.tempObject.value = $scope.productBean.productVariantsBeans[i].variantAttributeName;
									$scope.tempObject.varientAttributeValueId = $scope.productVariantValuesCollectionOne.length+1;
									$scope.productVariantValuesCollectionOne.push($scope.tempObject);
									$scope.valueCollectionOne.push($scope.tempObject.value);
									$scope.tempObject = {};
								}

							}
						}

						$scope.productVariantValuesCollectionOldOne = angular.copy($scope.productVariantValuesCollectionOne);
						$scope.productVariantValuesCollectionOldTwo = angular.copy($scope.productVariantValuesCollectionTwo);
						$scope.productVariantValuesCollectionOldThree = angular.copy($scope.productVariantValuesCollectionThree);
						if($scope.productBean.productCanBeSold=="true"){
							$scope.gui.productCanBeSold = true;
						}else{

							$scope.gui.productCanBeSold = false;
						}
						if($scope.productBean.trackingProduct=="true"){
							$scope.gui.trackingProduct = true;
						}else{

							$scope.gui.trackingProduct = false;
						}
						if($scope.productBean.varientProducts=="true"){
							$scope.gui.varientProducts = true;
						}else{

							$scope.gui.varientProducts = false;
						}
						if($scope.productBean.standardProduct=="true"){
							$scope.gui.standardProduct = true;
						}else{

							$scope.gui.standardProduct = false;
						}
						if($scope.data.productBarCodeMap!=null){
							$scope.productBarCodeMap = $scope.data.productBarCodeMap;
						}
					}
				}
				$scope.evaluateRetailPrice();
				$scope.variantError = false;
				$scope.showAddVariantsModal = true;
			}
			else if($scope.responseStatus == 'SYSTEMBUSY'
				||$scope.responseStatus=='INVALIDUSER'
					||$scope.responseStatus =='ERROR'
						||$scope.responseStatus =='INVALIDSESSION'){
				$scope.error = true;
				$scope.errorMessage = Response.data;
				$window.location = Response.layOutPath;
			} else {
				$scope.error = true;
				$scope.errorMessage = Response.data;
			}

		}).error(function() {
			$rootScope.emergencyInfoLoadedFully = false;
			$scope.error = true;
			$scope.errorMessage  = $scope.systemBusy;
		});

	};

	function count_letters(input,input_letter) {
		var counter = 0;
		for (var i = 0; i < input.length; i++) {
			var index_of_sub = input.indexOf(input_letter, i);
			if (index_of_sub > -1) {
				counter++;
				i = index_of_sub;
			}
		}
		return counter;
	};

	function varifyValueExist(varientValuescollection,value){
		$scope.variantError = false;
		for (var index = 0; index <varientValuescollection.length; index++) {
			if((varientValuescollection[index].value).toLowerCase() ==  value.toLowerCase()){
				$scope.variantError = true;
				$scope.variantErrorMessage = "Value "+value+" alreday exist";
				$timeout(function(){
					$scope.variantError = false;
				}, 1000);
				return true;
			}
		}
		return false;
	};

	$scope.addAttributeDynamically = function(){
		var count = $scope.productVariantsCollection.length;
		$scope.tempObj = {};
		$scope.tempObj.value = "";
		$scope.tempObj.varientAttributeId = "";
		$scope.tempObj.id = (count+1).toString();
		$scope.productVariantsCollection.push($scope.tempObj);
		$scope.tempObj = {};
		//$scope.verifyVariantsInfoCompleted();
	};

	$scope.addVarientValuesDynamically = function(varientObject){
		var valueArray = varientObject.value.split(",");
		if(valueArray!=null && valueArray.length>0){
			var valueEntertained = false;
			for(var i=0;i<valueArray.length;i++){
				if(valueArray[i]!=''){
					$scope.tempObject = {};

					if(varientObject.id=="1"){
						if(!varifyValueExist($scope.productVariantValuesCollectionOne,valueArray[i])
								&& !varifyValueExist($scope.productVariantValuesCollectionTwo,valueArray[i])
								&& !varifyValueExist($scope.productVariantValuesCollectionThree,valueArray[i])){
							$scope.tempObject.value = valueArray[i];	
							$scope.tempObject.varientAttributeValueId = $scope.productVariantValuesCollectionOne.length+1;
							$scope.productVariantValuesCollectionOne.push($scope.tempObject);
							valueEntertained = true;
							if($scope.valueCollectionOne[0]==" "){
								$scope.valueCollectionOne.splice(0,1);
							}
							$scope.valueCollectionOne.push(valueArray[i]);
						}
					}
					if(varientObject.id=="2"){
						if(!varifyValueExist($scope.productVariantValuesCollectionOne,valueArray[i])
								&& !varifyValueExist($scope.productVariantValuesCollectionTwo,valueArray[i])
								&& !varifyValueExist($scope.productVariantValuesCollectionThree,valueArray[i])){
							$scope.tempObject.value = valueArray[i];	
							$scope.tempObject.varientAttributeValueId = $scope.productVariantValuesCollectionTwo.length+1;
							$scope.productVariantValuesCollectionTwo.push($scope.tempObject);
							valueEntertained = true;
							if($scope.valueCollectionTwo[0]==" "){
								$scope.valueCollectionTwo.splice(0,1);
							}
							$scope.valueCollectionTwo.push(valueArray[i]);
						}
					}
					if(varientObject.id=="3"){
						if(!varifyValueExist($scope.productVariantValuesCollectionOne,valueArray[i])
								&& !varifyValueExist($scope.productVariantValuesCollectionTwo,valueArray[i])
								&& !varifyValueExist($scope.productVariantValuesCollectionThree,valueArray[i])){
							$scope.tempObject.value = valueArray[i];
							$scope.tempObject.varientAttributeValueId = $scope.productVariantValuesCollectionThree.length+1;
							$scope.productVariantValuesCollectionThree.push($scope.tempObject);
							valueEntertained = true;
							if($scope.valueCollectionThree[0]==" "){
								$scope.valueCollectionThree.splice(0,1);
							}
							$scope.valueCollectionThree.push(valueArray[i]);
						}
					}

					$scope.tempObject = {};
				}

			}
			if(valueEntertained){
				$scope.preparcartesianProduct();
			}
		}
	};

	function makeFinalProductVariantValuesCollection(combinationsArray){
		$scope.productVariantValuesCollection = [];

		for(var i=0;i<$scope.verientsOutletList.length;i++){
			$scope.verientsOutletList[i].supplierCode = $scope.productBean.supplierId;
			$scope.verientsOutletList[i].supplyPriceExclTax = $scope.productBean.supplyPriceExclTax;
			$scope.verientsOutletList[i].markupPrct = $scope.productBean.markupPrct;
			$scope.verientsOutletList[i].retailPriceExclTax = $scope.productBean.retailPriceExclTax;

		}
		$scope.sku = $scope.data.sku+1;
		for(var index=0;index<combinationsArray.length;index++){
			var uuid = generateUUID();
			$scope.temp = {};
			//	$scope.temp.varientName = combinationsArray[index];
			$scope.temp.uUid = uuid;
			var temvariantName = combinationsArray[index].split("/");
			for(var i=0;i<$scope.verientsOutletList.length;i++){
				$scope.sku  = $scope.sku+1;
				if(temvariantName[0] !=" "){
					$scope.verientsOutletList[i].sku  = $scope.productBean.sku+'-'+temvariantName[0];
					$scope.temp.varientName = temvariantName[0];
				}
				if(temvariantName[1] !=" "){
					$scope.verientsOutletList[i].sku  = $scope.productBean.sku+'-'+temvariantName[0]+'-'+temvariantName[1];
					$scope.temp.varientName = temvariantName[0]+'/'+temvariantName[1];

				}if(temvariantName[2] !=" "){
					$scope.verientsOutletList[i].sku  = $scope.productBean.sku+'-'+temvariantName[0]+'-'+temvariantName[1]+'-'+temvariantName[2];
					$scope.temp.varientName = temvariantName[0]+'/'+temvariantName[1]+'/'+temvariantName[2];
				}
			}
			$scope.temp.varientsOutletList = angular.copy($scope.verientsOutletList);
			$scope.temp.varientId = $scope.productVariantValuesCollection.length+1;
			$scope.productVariantValuesCollection.push($scope.temp);
			$scope.temp = {};
		}
		if($scope.productBean.productVariantsBeans.length > 0){

			for(var j=0;j<$scope.productBean.productVariantsBeans.length;j++){
				for(var i=0;i<$scope.productVariantValuesCollection.length;i++){
					if($scope.productVariantValuesCollection[i].varientName==$scope.productBean.productVariantsBeans[j].variantAttributeName){
						$scope.productVariantValuesCollection.splice(i,1);
					}
				}
			}
		}
		$scope.addVariantsEntertained = true;

	};

	function allPossibleCases(arr) {
		if (arr.length === 0) {
			return [];
		} 
		else if (arr.length ===1){
			return arr[0];
		}
		else {
			var result = [];
			var allCasesOfRest = allPossibleCases(arr.slice(1));  // recur with the rest of array
			for (var c in allCasesOfRest) {
				for (var i = 0; i < arr[0].length; i++) {
					result.push(arr[0][i] +'/'+ allCasesOfRest[c]);
				}
			}
			return result;
		}

	};

	function generateUUID() {
		var d = new Date().getTime();
		var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
			var r = (d + Math.random()*16)%16 | 0;
			d = Math.floor(d/16);
			return (c=='x' ? r : (r&0x3|0x8)).toString(16);
		});
		return uuid;
	};

	$scope.preparcartesianProduct = function(){
		var allArrays = [];
		if($scope.valueCollectionOne.length<1){
			$scope.valueCollectionOne.push(" ");
		}
		if($scope.valueCollectionTwo.length<1){
			$scope.valueCollectionTwo.push(" ");
		}
		if($scope.valueCollectionThree.length<1){
			$scope.valueCollectionThree.push(" ");
		}
		allArrays.push($scope.valueCollectionOne);
		allArrays.push($scope.valueCollectionTwo);
		allArrays.push($scope.valueCollectionThree);
		var combinationsArray=allPossibleCases(allArrays);
		//alert(cominationsArray);
		//	$scope.verifyVariantsInfoCompleted();
		makeFinalProductVariantValuesCollection(combinationsArray);
		for(var k=0;k<$scope.productVariantsCollection.length;k++){
			$scope.productVariantsCollection[k].value="";
		}
	};

	$scope.updateProduct = function() {

		$scope.productSuccess = false;
		$scope.productError = false;
		$scope.productAddError = false;

		if($scope.productBean.productVariantsBeans.length > 0){
			$scope.productBean.productVariantValuesCollectionOne = $scope.productVariantValuesCollectionOne;
			$scope.productBean.productVariantValuesCollectionTwo = $scope.productVariantValuesCollectionTwo;
			$scope.productBean.productVariantValuesCollectionThree = $scope.productVariantValuesCollectionThree;
			for(var j=0;j<$scope.productBean.productVariantsBeans.length;j++){
				for(var i=0;i<$scope.productVariantValuesCollection.length;i++){
					if($scope.productVariantValuesCollection[i].varientName==$scope.productBean.productVariantsBeans[j].variantAttributeName){
						$scope.productVariantValuesCollection.splice(i,1);
					}
				}
			}

			$scope.productBean.productVariantValuesCollectionOne = $scope.productVariantValuesCollectionOne;
			$scope.productBean.productVariantValuesCollectionTwo = $scope.productVariantValuesCollectionTwo;
			$scope.productBean.productVariantValuesCollectionThree = $scope.productVariantValuesCollectionThree;	
			$scope.productBean.productVariantValuesCollection = $scope.productVariantValuesCollection;
			$scope.productBean.productVariantAttributeCollection = $scope.productVariantsCollection;

		}else{
			$scope.productBean.productVariantValuesCollectionOne = $scope.productVariantValuesCollectionOne;
			$scope.productBean.productVariantValuesCollectionTwo = $scope.productVariantValuesCollectionTwo;
			$scope.productBean.productVariantValuesCollectionThree = $scope.productVariantValuesCollectionThree;
			$scope.productBean.productVariantValuesCollection = $scope.productVariantValuesCollection;
			$scope.productBean.productVariantAttributeCollection = $scope.productVariantsCollection;
		}

		$scope.productBean.varientAttributeId = $scope.productVariantsCollection[0].varientAttributeId;
		$scope.productBean.tagList = $scope.tagList;
		$scope.productBean.outletList = $scope.outletList;
		$scope.productBean.productCanBeSold  = $scope.gui.productCanBeSold;
		$scope.productBean.trackingProduct = $scope.gui.trackingProduct;
		$scope.productBean.varientProducts = $scope.gui.varientProducts;
		$scope.productBean.standardProduct = $scope.gui.standardProduct;


		$scope.productBean.compositProductCollection = $scope.compositProductCollection;
		$scope.productBean.imageData = $scope.imageData;
		$scope.loading = true;
		$http.post('manageProduct/updateProduct/'+$scope._s_tk_com, $scope.productBean)
		.success(function(Response) {
			$scope.loading = false;

			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.productSuccess = true;
				$scope.productSuccessMessage = Response.data;
				$scope.showAddVariantsModal = false;

				$timeout(function(){
					$scope.productSuccess = false;
					$timeout(function(){
						$route.reload();
					}, 500);
					//	$window.location = Response.layOutPath;
				}, 1000);



			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.productError = true;
				$scope.productErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.productError = true;
				$scope.productErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.productError = true;
			$scope.productErrorMessage = $scope.systemBusy;
		});
	};

	$scope.evaluateVariantRetailPrice = function(verientValue,outlet){
		for(var index=0;index<$scope.productVariantValuesCollection.length;index++){
			if(verientValue.varientId==$scope.productVariantValuesCollection[index].varientId){
				for(var i=0;i<$scope.productVariantValuesCollection[index].varientsOutletList.length;i++){
					if(outlet.outletId==$scope.productVariantValuesCollection[index].varientsOutletList[i].outletId){
						var supplyPrice = parseFloat($scope.productVariantValuesCollection[index].varientsOutletList[i].supplyPriceExclTax);
						var markUp = parseFloat($scope.productVariantValuesCollection[index].varientsOutletList[i].markupPrct);
						if(isNaN(markUp)){
							markUp = 0.0;
							$scope.productVariantValuesCollection[index].varientsOutletList[i].markupPrct = "0";
						}
						var result = (supplyPrice*(markUp/100)+supplyPrice).toFixed(2);
						$scope.productVariantValuesCollection[index].varientsOutletList[i].retailPriceExclTax = result.toString();
					}
				}
			}
		}
	};

	$scope.adjustMarkUpByChangeOfVariantRetailPrice = function(verientValue,outlet){

		for(var index=0;index<$scope.productVariantValuesCollection.length;index++){
			if(verientValue.varientId==$scope.productVariantValuesCollection[index].varientId){
				for(var i=0;i<$scope.productVariantValuesCollection[index].varientsOutletList.length;i++){
					if(outlet.outletId==$scope.productVariantValuesCollection[index].varientsOutletList[i].outletId){

						var retailPrice = parseFloat($scope.productVariantValuesCollection[index].varientsOutletList[i].retailPriceExclTax);
						if(!isNaN(retailPrice)){
							var supplyPrice = parseFloat($scope.productVariantValuesCollection[index].varientsOutletList[i].supplyPriceExclTax);
							var markUp = round(parseFloat($scope.productVariantValuesCollection[index].varientsOutletList[i].markupPrct),5);
							if(isNaN(markUp)){
								markUp = 0.0;
							}
							markUp = round((retailPrice-supplyPrice)*100/supplyPrice,5);
							$scope.productVariantValuesCollection[index].varientsOutletList[i].markupPrct = markUp.toString();

						}
					}
				}
			}
		}
	};

	function round(value, decimals) {
		return Number(Math.round(value+'e'+decimals)+'e-'+decimals);
	}

	$scope.evaluateRetailPrice = function(){
		var supplyPrice = parseFloat($scope.productBean.supplyPriceExclTax);
		var markUp = parseFloat($scope.productBean.markupPrct);
		if(isNaN(markUp)){
			markUp = 0.0;
			$scope.productBean.markupPrct = "0";
		}
		var result = supplyPrice*(markUp/100)+supplyPrice;
		$scope.productBean.retailPriceExclTax = (result.toFixed(2)).toString();
		if($scope.outletList!=null && result>0){
			for(var i=0;i<$scope.outletList.length;i++){
				var salesTax = parseFloat($scope.outletList[i].defaultTax);
				var taxAmount = result*(salesTax/100);
				$scope.outletList[i].taxAmount = (taxAmount.toFixed(2)).toString();
				$scope.outletList[i].retailPrice = ((taxAmount+result).toFixed(2)).toString();
//				$scope.productBean.outletId = $scope.outletList[i].outletId;

			}
		}
	};

	$scope.skuinput = function(){
		if($scope.productSKU.includes('-')||$scope.productSKU.length>6){
			if($scope.productVariantMap[$scope.productSKU.toLowerCase()] != null){
				$scope.skudisable = true;
				$scope.stockOrderDetailBean.orderProdQty = 1;
				$scope.productVariantBean = $scope.productVariantMap[$scope.productSKU.toLowerCase()];
				//console.log($scope.productVariantMap[$scope.productSKU]);			
				$scope.checkProductStatus();
				$scope.productSKU = '';
				$scope.skudisable = false;
			}else{
				if($scope.productSKU.length>15){
					$scope.productSKU = '';
					$scope.skudisable = false;
				}
			}
		}
	};
//	Add Variants code end
	$scope.sessionValidation();
}];

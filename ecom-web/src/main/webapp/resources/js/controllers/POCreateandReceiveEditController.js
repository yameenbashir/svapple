'use strict'; 

/**
 * POCreateandReceiveEditController
 * @constructor
 */
var POCreateandReceiveEditController = ['$sce', '$filter', '$scope', '$http', '$timeout', '$window','$cookieStore','$rootScope','SessionService','POCreateandReceiveEditControllerPreLoad',function($sce, $filter, $scope, $http, $timeout, $window,$cookieStore,$rootScope,SessionService,POCreateandReceiveEditControllerPreLoad) {
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.stockOrderId = "";
	$scope.grandTotal = "0";
	$scope.stockOrderBean = {};
	$scope.showConfirmDeletePopup = false;
	$scope.productVariantBean = {};
	$scope.productVariantBeansList = [];
	$scope.stockOrderDetailBean = {};
	$scope.delStockOrderDetailBean = {};
	$scope.stockOrderDetailBeansList = [];
	$scope.counter = 1;
	$scope.hideRefValues = false;
	$scope.productVariantMap = [];
	$scope.productMap = [];
	$scope.productSKU = '';
	$scope.skudisable = false;
	$scope.inputTypeScan = true;

	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = POCreateandReceiveEditControllerPreLoad.loadControllerData();
			$scope.fetchData();
			$scope.stockOrderBean = $cookieStore.get('_ct_bl_ost');
			$scope.stockOrderBean.diliveryDueDate = new Date($scope.stockOrderBean.diliveryDueDate);
			$scope.stockOrderBean.itemCount = 0;
			$scope.itemCountTotal = 0;
			$scope.stockOrderBean.recItemCount = 0;
			$scope.AllInOne();
			/*$scope.calculateItemCount();
			$scope.calculateRecItemCount();*/
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
			if($scope.data.stockOrderDetailBeansList!=null){
				$scope.stockOrderDetailBeansList = $scope.data.stockOrderDetailBeansList;
				/*$scope.arrangeOrder();
				$scope.calculateGrandTotal();*/
			}

		}
		$rootScope.globalPageLoader = false;
	};

	/*
	$scope.populateProducts = function(supplierId){
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


	 */	/*$scope.selectFirst = function(){
		//$scope.productList = $scope.productVariantBeansList[0];
		$scope.productVariantBean = $scope.productVariantBeansList[0];	
	}*/


//	PurchaseOrder Create Start	




	$scope.addStockOrderUpdate = function() {		
		$scope.success = false;
		$scope.error = false;
		$scope.loading = true;
		$scope.stockOrderBean.statusId = "2"; // In Progress status at Stock Order Creation page
		$scope.stockOrderBean.stockOrderTypeId = "1"; // Supplier Order
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

	};
	$scope.showConfirmReceiveStockOrder = function(){
		$scope.showConfirmReceivePopup = true;
	};

	$scope.addStockOrderUpdateandReceive = function() {		
		$scope.success = false;
		$scope.error = false;
		$scope.loading = true;
		$scope.stockOrderBean.statusId = "3"; // Completed status at Stock Order Creation page
		$scope.stockOrderBean.stockOrderTypeId = "1"; // Supplier Order
		$http.post('purchaseOrder/updateStockOrder/'+$scope._s_tk_com, $scope.stockOrderBean)
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
		//$scope.calculateItemCount();
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
		/*$scope.arrangeOrder();
		$scope.calculateGrandTotal();
		$scope.calculateItemCount();
		$scope.calculateRecItemCount();*/
		$scope.AllInOne();
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
	};*/

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
	$scope.sessionValidation();
}];

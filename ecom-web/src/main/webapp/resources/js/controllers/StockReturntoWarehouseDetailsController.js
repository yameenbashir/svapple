'use strict';

/**
 * StockReturntoWarehouseDetailsController
 * @constructor
 */
var StockReturntoWarehouseDetailsController = ['$sce', '$scope', '$http', '$timeout', '$window','$cookieStore','$rootScope','SessionService','StockReturntoWarehouseDetailsControllerPreLoad',function($sce, $scope, $http, $timeout, $window,$cookieStore,$rootScope,SessionService,StockReturntoWarehouseDetailsControllerPreLoad) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.showConfirmDeletePopup = false;
	$scope.productVariantBean = {};
	$scope.productVariantBeansList = [];
	$scope.stockOrderDetailBean = {};
	$scope.delStockOrderDetailBean = {};
	$scope.stockOrderDetailBeansList = [];
	$scope.stockTransferDetailBeansList = [];
	$scope.stockTransferOrderBean = {};
	$scope.counter = 1;
	$scope.returnAllProducts = false;
	$scope.hideRefValues = false;
	$scope.productVariantMap = [];
	$scope.productMap = [];
	$scope.productSKU = '';
	$scope.skudisable = false;
	$scope.inputTypeScan = true;

	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.stockOrderBean = $cookieStore.get('_e_cOt_pio');
			$scope.roleId = $cookieStore.get('_s_tk_rId');
			$scope.headOffice = $cookieStore.get('_s_tk_iho');
			$scope.data = StockReturntoWarehouseDetailsControllerPreLoad.loadControllerData();
			$scope.fetchData();
			var orderDate = new Date($scope.stockOrderBean.diliveryDueDate);
			var day = orderDate.getDate();
			var month = orderDate.getMonth() + 1;
			var year = orderDate.getFullYear();
			$scope.stockOrderBean.diliveryDueDate = month.toString() + "/" + day.toString() + "/" + year.toString();
			$scope.AllInOne();
		}
	};

	$scope.isAdmin = function(){
		if($scope.headOffice != null && $scope.headOffice.toString() != ''){
			if($scope.headOffice.toString() == "true"){
				return false;
			}
			else{
				return true;
			}
		}
		else{
			return true;
		}
	};

	$scope.fetchData = function() {
		$rootScope.stockReturntoWarehouseDetailsLoadedFully = false;
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

			if($scope.data!=null){
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
				if($scope.data.stockTransferOrderBeansList != null){
					$scope.stockTransferOrderBeansList = $scope.data.stockTransferOrderBeansList;
					for (var i = 0; i < $scope.stockTransferOrderBeansList.length; i++) {
						if($scope.stockOrderBean.stockOrderId == $scope.stockTransferOrderBeansList[i].stockOrderId){
							$scope.stockTransferOrderBeansList.splice(i, 1);
						}
					}
				}
			}
		}
		$rootScope.globalPageLoader = false;
	};

	$scope.returnAllProducts = function(){
		if($scope.returnAllProductsBean == "1"){
			$scope.stockOrderDetailBeansList = [];
			$scope.copyAllProducts();			
		}else{
			$scope.stockOrderDetailBeansList = [];
		}
	}

	$scope.copyAllProducts = function(){
		$scope.globalPageLoader = true;
		if($scope.productVariantBeansList != null && $scope.productVariantBeansList.length > 0){
			$scope.counter = 1;
			for (var j = 0; j < $scope.productVariantBeansList.length; j++) {							
				if($scope.productVariantBeansList[j].productVariantId != null){
					$scope.productVariantBean = {};
					$scope.productVariantBean = angular.copy($scope.productVariantBeansList[j]);					
					$scope.stockOrderDetailBean.orderProdQty = angular.copy($scope.productVariantBeansList[j].currentInventory);
					$scope.checkProductStatusAllProducts();			
				}
			}
			for (var j = 0; j < $scope.productBeansList.length; j++) {							
				if($scope.productBeansList[j].productVariantId != null){
					$scope.productVariantBean = {};
					$scope.productVariantBean.productVariantId = angular.copy($scope.productBeansList[j].productId);
					$scope.hitVariant = false;
					for (var i = 0; i < $scope.productVariantBeansList.length; i++) {						
						if($scope.productVariantBeansList[i].productId == $scope.productVariantBean.productVariantId){
							$scope.hitVariant = true;
						}
					}
					if($scope.hitVariant == false){
						$scope.productVariantBean = {};
						$scope.productVariantBean = angular.copy($scope.productBeansList[j]);	
						$scope.stockOrderDetailBean.orderProdQty = angular.copy($scope.productBeansList[j].currentInventory);
						$scope.checkProductStatusAllProducts();
					}								
				}
			}
			$scope.globalPageLoader = false;
		}
		else{
			$scope.globalPageLoader = false;
		}
		$scope.AllInOne();
	};

	/*$scope.selectFirst = function(){
		//$scope.productList = $scope.productVariantBeansList[0];
		$scope.productVariantBean = $scope.productVariantBeansList[0];	
	}*/
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


	$scope.checkProductStatusAllProducts = function(){
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
						$scope.stockOrderDetailBean.orderProdQty = $scope.productVariantBeansList[i].currentInventory;
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
							if($scope.stockOrderBean.retailPriceBill == true){
								value.total = value.retailPrice * value.orderProdQty;
							}
							else{
								value.total = value.ordrSupplyPrice * value.orderProdQty;
							}
							if(isNaN(value.total)){
								value.total = "0";
							}
						}
						else{
							value.total = "0";
						}
						productVariantBeantoReplace =angular.copy(value);
						productVariantBeantoReplace.isDirty = true;
						var index = $scope.stockOrderDetailBeansList.indexOf(value);
						$scope.stockOrderDetailBeansList.splice(index, 1);
						$scope.stockOrderDetailBeansList.unshift(productVariantBeantoReplace);
						//$scope.arrangeOrder();
						$scope.dualEntry = true;
					}
				});
				if($scope.dualEntry != true){
					$scope.stockOrderDetailBean.productVariantId = obj.productVariantId;
					$scope.stockOrderDetailBean.variantAttributeName = obj.variantAttributeName;
					$scope.stockOrderDetailBean.productVariantCurrInventory = obj.currentInventory;
					$scope.stockOrderDetailBean.productVariantRecvOutletInventory = obj.recCurrentInventory;
					$scope.stockOrderDetailBean.isProduct = obj.isProduct;
					if(obj.supplyPriceExclTax != null){
						$scope.stockOrderDetailBean.ordrSupplyPrice = ""; 
						$scope.stockOrderDetailBean.ordrSupplyPrice = angular.copy(obj.supplyPriceExclTax);
					}
					if(obj.retailPriceExclTax != null){
						$scope.stockOrderDetailBean.retailPrice = ""; 
						$scope.stockOrderDetailBean.retailPrice = angular.copy(obj.retailPriceExclTax);
					}					
					if($scope.stockOrderBean.retailPriceBill == true){
						$scope.stockOrderDetailBean.total = $scope.stockOrderDetailBean.retailPrice * $scope.stockOrderDetailBean.orderProdQty;
					}
					else{
						$scope.stockOrderDetailBean.total = $scope.stockOrderDetailBean.ordrSupplyPrice * $scope.stockOrderDetailBean.orderProdQty;
					}					
					if(isNaN($scope.stockOrderDetailBean.total)){
						$scope.stockOrderDetailBean.total = "0";
					}
					$scope.stockOrderDetailBean.order = $scope.counter;
					$scope.counter++;
					$scope.stockOrderDetailBean.isDirty = true;
					$scope.stockOrderDetailBean.stockOrderId = $scope.stockOrderBean.stockOrderId;
					$scope.stockOrderDetailBeansList.unshift($scope.stockOrderDetailBean);
					//$scope.arrangeOrder();
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
							if($scope.stockOrderBean.retailPriceBill == true){
								value.total = value.retailPrice * value.orderProdQty;
							}
							else{
								value.total = value.ordrSupplyPrice * value.orderProdQty;
							}
							if(isNaN(value.total)){
								value.total = "0";
							}
						}
						else{
							value.total = "0";
						}
						productVariantBeantoReplace =angular.copy(value);
						productVariantBeantoReplace.isDirty = true;
						var index = $scope.stockOrderDetailBeansList.indexOf(value);
						$scope.stockOrderDetailBeansList.splice(index, 1);
						$scope.stockOrderDetailBeansList.unshift(productVariantBeantoReplace);
						//$scope.arrangeOrder();
						$scope.dualEntry = true;
					}
				});
				if($scope.dualEntry != true){
					$scope.stockOrderDetailBean.productVariantId = obj.productVariantId;
					$scope.stockOrderDetailBean.variantAttributeName = obj.variantAttributeName;
					$scope.stockOrderDetailBean.productVariantCurrInventory = obj.currentInventory;
					$scope.stockOrderDetailBean.productVariantRecvOutletInventory = obj.recCurrentInventory;
					$scope.stockOrderDetailBean.isProduct = obj.isProduct;
					if(obj.supplyPriceExclTax != null){
						$scope.stockOrderDetailBean.ordrSupplyPrice = ""; 
						$scope.stockOrderDetailBean.ordrSupplyPrice = angular.copy(obj.supplyPriceExclTax);
					}
					if(obj.retailPriceExclTax != null){
						$scope.stockOrderDetailBean.retailPrice = ""; 
						$scope.stockOrderDetailBean.retailPrice = angular.copy(obj.retailPriceExclTax);
					}
					if($scope.stockOrderBean.retailPriceBill == true){
						$scope.stockOrderDetailBean.total = $scope.stockOrderDetailBean.retailPrice * $scope.stockOrderDetailBean.orderProdQty;
					}
					else{
						$scope.stockOrderDetailBean.total = $scope.stockOrderDetailBean.ordrSupplyPrice * $scope.stockOrderDetailBean.orderProdQty;
					}
					if(isNaN($scope.stockOrderDetailBean.total)){
						$scope.stockOrderDetailBean.total = "0";
					}
					$scope.stockOrderDetailBean.order = $scope.counter;
					$scope.counter++;
					$scope.stockOrderDetailBean.stockOrderId = $scope.stockOrderBean.stockOrderId;
					$scope.stockOrderDetailBean.isDirty = true;
					$scope.stockOrderDetailBeansList.unshift($scope.stockOrderDetailBean);
					//$scope.arrangeOrder();
					$scope.dualEntry = false;
				}
			}
		}
		else{
			$scope.stockOrderDetailBean.productVariantId = obj.productVariantId;		
			$scope.stockOrderDetailBean.variantAttributeName = obj.variantAttributeName;
			$scope.stockOrderDetailBean.productVariantCurrInventory = obj.currentInventory;
			$scope.stockOrderDetailBean.productVariantRecvOutletInventory = obj.recCurrentInventory;
			$scope.stockOrderDetailBean.isProduct = obj.isProduct;
			if(obj.supplyPriceExclTax != null){
				$scope.stockOrderDetailBean.ordrSupplyPrice = ""; 
				$scope.stockOrderDetailBean.ordrSupplyPrice = angular.copy(obj.supplyPriceExclTax);
			}
			if(obj.retailPriceExclTax != null){
				$scope.stockOrderDetailBean.retailPrice = ""; 
				$scope.stockOrderDetailBean.retailPrice = angular.copy(obj.retailPriceExclTax);
			}
			if($scope.stockOrderBean.retailPriceBill == true){
				$scope.stockOrderDetailBean.total = $scope.stockOrderDetailBean.retailPrice * $scope.stockOrderDetailBean.orderProdQty;
			}
			else{
				$scope.stockOrderDetailBean.total = $scope.stockOrderDetailBean.ordrSupplyPrice * $scope.stockOrderDetailBean.orderProdQty;
			}
			$scope.stockOrderDetailBean.order = $scope.counter;
			$scope.counter++;
			$scope.stockOrderDetailBean.stockOrderId = $scope.stockOrderBean.stockOrderId;
			$scope.stockOrderDetailBean.isDirty = true;
			$scope.stockOrderDetailBeansList.unshift($scope.stockOrderDetailBean);
			//$scope.arrangeOrder();
		}
		//$scope.calculateTotal(obj.productVariantId);
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
		$scope.calculateItemCount();*/  //Covered in All in One
		$scope.AllInOne(); 
		//$scope.calculateRecItemCount(); for Stock Orders with WF only (e.g. Stock RTW, Stock Receive)
	};
	
	$scope.AllInOne = function(){
		$scope.counter = 1;
		$scope.stockOrderBean.itemCount = 0;
		$scope.grandTotal = "0";
		$scope.stockOrderBean.itemCountRecv = 0;
		$scope.grandrecvTotal = "0";				
		if ($scope.stockOrderDetailBeansList.length > 0) {
			for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
				//arrange Order
				$scope.stockOrderDetailBeansList[i].order = $scope.counter; 
				$scope.counter++;
				//Calculate Total for Item
				if($scope.stockOrderBean.retailPriceBill == true){ 
					$scope.stockOrderDetailBeansList[i].total = $scope.stockOrderDetailBeansList[i].retailPrice * $scope.stockOrderDetailBeansList[i].orderProdQty;
				}
				else{
					$scope.stockOrderDetailBeansList[i].total = $scope.stockOrderDetailBeansList[i].ordrSupplyPrice * $scope.stockOrderDetailBeansList[i].orderProdQty;
				}				
				if(isNaN($scope.stockOrderDetailBeansList[i].total)){
					$scope.stockOrderDetailBeansList[i].total = "0";
				}
				if(parseInt($scope.stockOrderDetailBeansList[i].productVariantCurrInventory) < parseInt($scope.stockOrderDetailBeansList[i].orderProdQty)){
					$scope.stockOrderDetailBeansList[i].greaterThanStock = true;
				}
				else{
					$scope.stockOrderDetailBeansList[i].greaterThanStock = false;
				}
				//Calculate Total Items Count
				$scope.stockOrderBean.itemCount = parseInt($scope.stockOrderBean.itemCount) + parseInt($scope.stockOrderDetailBeansList[i].orderProdQty);
				if(isNaN($scope.stockOrderBean.itemCount)){
					$scope.stockOrderBean.itemCount = "0";
				}
				//Calculate Total Grand Total
				$scope.grandTotal = parseFloat($scope.grandTotal) + parseFloat($scope.stockOrderDetailBeansList[i].total);
				if(isNaN($scope.grandTotal)){
					$scope.grandTotal = "0";
				}
				if($scope.isAdmin() == false){ //In Case of Admin
					//Calculate Total Recv for each Item
					if($scope.stockOrderBean.retailPriceBill == true){
						$scope.stockOrderDetailBeansList[i].recvTotal = $scope.stockOrderDetailBeansList[i].retailPrice * $scope.stockOrderDetailBeansList[i].recvProdQty;
					}
					else{
						$scope.stockOrderDetailBeansList[i].recvTotal = value.ordrSupplyPrice * $scope.stockOrderDetailBeansList[i].recvProdQty;
					}				
					if(isNaN($scope.stockOrderDetailBeansList[i].recvTotal)){
						$scope.stockOrderDetailBeansList[i].recvTotal = "0";
					}
					if(parseInt($scope.stockOrderDetailBeansList[i].productVariantCurrInventory) < parseInt($scope.stockOrderDetailBeansList[i].recvProdQty)){
						$scope.stockOrderDetailBeansList[i].greaterThanStock = true;
					}
					else{
						$scope.stockOrderDetailBeansList[i].greaterThanStock = false;
					}
					//Calculate Total Recv Items for Admin
					$scope.stockOrderBean.itemCountRecv = parseInt($scope.stockOrderBean.itemCountRecv) + parseInt($scope.stockOrderDetailBeansList[i].recvProdQty);
					if(isNaN($scope.stockOrderBean.itemCountRecv) || $scope.stockOrderBean.itemCountRecv == ""){
						$scope.stockOrderBean.itemCountRecv = "0";
					}
					//Calculate Grand Recv Total for Admin
					$scope.grandrecvTotal = parseFloat($scope.grandrecvTotal) + parseFloat($scope.stockOrderDetailBeansList[i].recvTotal);
					if(isNaN($scope.grandrecvTotal)){
						$scope.grandrecvTotal = "0";
					}
				}
			}
		}
	};


/*	$scope.arrangeOrder = function(){
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
				if($scope.stockOrderBean.retailPriceBill == true){
					value.total = value.retailPrice * value.orderProdQty;
				}
				else{
					value.total = value.ordrSupplyPrice * value.orderProdQty;
				}				
				if(isNaN(value.total)){
					value.total = "0";
				}
				if(parseInt(value.productVariantCurrInventory) < parseInt(value.orderProdQty)){
					value.greaterThanStock = true;
				}
				else{
					value.greaterThanStock = false;
				}
			}
		});	
		$scope.calculateGrandTotal();
		$scope.calculateItemCount();
	};

	$scope.calculateItemCount = function(){
		$scope.stockOrderBean.itemCount = 0;
		if($scope.stockOrderDetailBeansList != null){
			for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
				$scope.stockOrderBean.itemCount = parseInt($scope.stockOrderBean.itemCount) + parseInt($scope.stockOrderDetailBeansList[i].orderProdQty);
				if(isNaN($scope.stockOrderBean.itemCount)){
					$scope.stockOrderBean.itemCount = "0";
				}
			}
		}
	}; */

	$scope.calculateTotalforItem = function(productVariantId){
		angular.forEach($scope.stockOrderDetailBeansList, function(value,key){
			if(value.productVariantId == productVariantId){
				if($scope.stockOrderBean.retailPriceBill == true){
					value.total = value.retailPrice * value.orderProdQty;
				}
				else{
					value.total = value.ordrSupplyPrice * value.orderProdQty;
				}				
				if(isNaN(value.total)){
					value.total = "0";
				}
				if(parseInt(value.productVariantCurrInventory) < parseInt(value.orderProdQty)){
					value.greaterThanStock = true;
				}
				else{
					value.greaterThanStock = false;
				}
				value.isDirty = true;
			}
		});	
		/*$scope.calculateGrandTotal();
		$scope.calculateItemCount();*/
		$scope.AllInOne();
	};	
	
	$scope.calculateTotalAdminforItem = function(productVariantId){
		angular.forEach($scope.stockOrderDetailBeansList, function(value,key){
			if(value.productVariantId == productVariantId){
				if($scope.stockOrderBean.retailPriceBill == true){
					value.recvTotal = value.retailPrice * value.recvProdQty;
				}
				else{
					value.recvTotal = value.ordrSupplyPrice * value.recvProdQty;
				}				
				if(isNaN(value.recvTotal)){
					value.recvTotal = "0";
				}
				if(parseInt(value.productVariantCurrInventory) < parseInt(value.recvProdQty)){
					value.greaterThanStock = true;
				}
				else{
					value.greaterThanStock = false;
				}
				value.isDirty = true;
			}
		});	
		/*$scope.calculateGrandTotal();
		$scope.calculateItemCount();*/
		$scope.AllInOne();		
	}; 
	
	/*$scope.calculateGrandTotal = function(){
		$scope.grandTotal = "0";
		if ($scope.stockOrderDetailBeansList.length > 0) {
			for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
				if(isNaN($scope.stockOrderDetailBeansList[i].total)){
					$scope.stockOrderDetailBeansList[i].total = "0";
				}
				$scope.grandTotal = parseFloat($scope.grandTotal) + parseFloat($scope.stockOrderDetailBeansList[i].total);
				if(isNaN($scope.grandTotal)){
					$scope.grandTotal = "0";
				}
			}
		}
	}; */


	$scope.checkStockOrderDetailList = function() {
		if ($scope.stockOrderDetailBeansList.length > 0) { // your question said "more than one element"
			for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
				if($scope.stockOrderDetailBeansList[i].orderProdQty == "" || $scope.stockOrderDetailBeansList[i].orderProdQty == null){
					return false;
				}
				else if($scope.stockOrderDetailBeansList[i].ordrSupplyPrice == "" || $scope.stockOrderDetailBeansList[i].ordrSupplyPrice == null){
					return false;
				}		   
				else if($scope.stockOrderDetailBeansList[i].greaterThanStock == true){
					return false;
				}
			}
			return true;
		}
		else {
			return false;
		}
	};
	$scope.checkStockOrderDetailListAdmin = function() {
		if($scope.headOffice.toString() == "true")
		{		
			if ($scope.stockOrderDetailBeansList.length > 0) { // your question said "more than one element"
				for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
					if($scope.stockOrderDetailBeansList[i].orderProdQty == "" || $scope.stockOrderDetailBeansList[i].orderProdQty == null){
						return false;
					}
					else if($scope.stockOrderDetailBeansList[i].ordrSupplyPrice == "" || $scope.stockOrderDetailBeansList[i].ordrSupplyPrice == null){
						return false;
					}		   
					else if($scope.stockOrderDetailBeansList[i].greaterThanStock == true){
						return false;
					}
				}
				return true;
			}
			else {
				return false;
			}
		}else
			return false;
	};

	$scope.addStockOrderDetail = function(){
		if ($scope.stockOrderDetailBeansList.length > 0) {
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
					$cookieStore.put('_ct_bl_ost',  $cookieStore.get('_e_cOt_pio'));
					$cookieStore.put('_e_cOt_pio',"");
					if ($scope.stockOrderDetailBeansList.length > 0) {
						for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
							$scope.stockOrderDetailBeansList[i].isDirty = false; 
						}
					}
					$scope.AllInOne();
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

		$scope.isAdmin();
	};

	$scope.showSendTransferStockOrder = function(){
		$scope.showConfirmSendTransferPopup = true;
	};
	$scope.transferStockOrder = function(){		
		$scope.success = false;
		$scope.error = false;
		$scope.loading = true;
		//$scope.calculateGrandTotal();
		$scope.stockOrderBean.statusId = "3"; // Completed status
		$http.post('purchaseOrderDetails/updateAndReturntoHeadOffice/'+$scope._s_tk_com+'/'+$scope.grandTotal+'/'+$scope.stockOrderBean.itemCount, $scope.stockOrderDetailBeansList)
		.success(function(Response) {
			$scope.loading = false;

			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.productTypeBean = {};
				$scope.success = true;
				$scope.successMessage = "Request Processed successfully!";
				$scope.stockOrderBean.stockOrderId = Response.data;
				$scope.showConfirmSendTransferPopup = false;
				$timeout(function(){
					$scope.success = false;
					$window.location = Response.layOutPath;
				}, 1000);
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


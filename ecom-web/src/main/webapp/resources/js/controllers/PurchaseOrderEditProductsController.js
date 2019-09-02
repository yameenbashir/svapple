'use strict';

/**
 * PurchaseOrderEditProductsController
 * @constructor
 */
var PurchaseOrderEditProductsController = ['$scope', '$http','$timeout', '$window','$cookieStore','$rootScope','SessionService','PurchaseOrderEditProductsControllerPreLoad',function($scope, $http, $timeout, $window,$cookieStore,$rootScope,SessionService,PurchaseOrderEditProductsControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.productVariantBean = {};
	$scope.productVariantBeansList = [];
	$scope.stockOrderDetailBean = {};
	$scope.delStockOrderDetailBean = {};
	$scope.stockOrderDetailBeansList = [];	
	$scope.hideRefValues = false;
	
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = PurchaseOrderEditProductsControllerPreLoad.loadControllerData();
			$scope.fetchData();
			$scope.stockOrderBean = $cookieStore.get('_ct_bl_ost');
			var orderDate = new Date($scope.stockOrderBean.diliveryDueDate);
			var day = orderDate.getDate();
			var month = orderDate.getMonth() + 1;
			var year = orderDate.getFullYear();
			$scope.stockOrderBean.diliveryDueDate = month.toString() + "/" + day.toString() + "/" + year.toString();
		}
	};

	$scope.fetchData = function() {
		$rootScope.purchaseOrderEditProductsLoadedFully = false;
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
			
			if($scope.data.stockOrderDetailBeansList!=null){
				$scope.stockOrderDetailBeansList = $scope.data.stockOrderDetailBeansList;
				$scope.arrangeOrder();
			}
			if($scope.data.productVariantBeansList!=null){
				$scope.productVariantBeansList = $scope.data.productVariantBeansList;
			}
		}
		$rootScope.globalPageLoader = false;
	};

	$scope.addProduct  = function() {
		$scope.dualEntry = false;
		var productVariantBeantoReplace = {};
		var obj = JSON.parse($scope.productVariantBean);		
		if($scope.stockOrderDetailBeansList.length > 0){
		if(obj.isProduct != "true"){
		angular.forEach($scope.stockOrderDetailBeansList, function(value,key){
			if(value.productVariantId == obj.productVariantId && value.isProduct != "true"){
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
				$scope.stockOrderDetailBeansList.unshift(productVariantBeantoReplace);
				$scope.arrangeOrder();
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
					$scope.stockOrderDetailBeansList.unshift(productVariantBeantoReplace);
					$scope.arrangeOrder();
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
			$scope.stockOrderDetailBeansList.unshift($scope.stockOrderDetailBean);
		}
		$scope.stockOrderDetailBean = {};
	};
	
	
	$scope.showConfirmDelete = function(stockOrderDetailBean){
		$scope.delStockOrderDetailBean = {};
		$scope.delStockOrderDetailBean = angular.copy(stockOrderDetailBean);
		$scope.showConfirmDeletePopup = true;
	};
	
	$scope.delStockOrderDetail = function(){
		angular.forEach($scope.stockOrderDetailBeansList, function(value,key){
			if(value.productVariantId == $scope.delStockOrderDetailBean.productVariantId && value.isProduct == $scope.delStockOrderDetailBean.isProduct){
				var index = $scope.stockOrderDetailBeansList.indexOf(value);
				$scope.stockOrderDetailBeansList.splice(index, 1);
			}
		});	
		
		$scope.showConfirmDeletePopup = false; 
		$scope.delStockOrderDetailBean = {};
		$scope.arrangeOrder();	
		};

	$scope.arrangeOrder = function(){
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
	
		$scope.updateStockOrderDetail = function(){
			if ($scope.stockOrderDetailBeansList.length > 0) {
				$scope.success = false;
				$scope.error = false;
				$scope.loading = true;
				$http.post('purchaseOrderDetails/updateStockOrderDetail/'+$scope._s_tk_com, $scope.stockOrderDetailBeansList)
				.success(function(Response) {
					$scope.loading = false;					
					$scope.responseStatus = Response.status;
					if ($scope.responseStatus == 'SUCCESSFUL') {
						$scope.success = true;
						$scope.successMessage = Response.data;
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
	$scope.sessionValidation();

}];


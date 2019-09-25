'use strict';

/**
 * StockReturntoWarehouseEditProductsController
 * @constructor
 */
var StockReturntoWarehouseEditProductsController = ['$scope','$sce', '$http', '$timeout', '$window','$cookieStore','$rootScope','SessionService','StockReturntoWarehouseEditProductsControllerPreLoad',function($scope,$sce, $http, $timeout, $window,$cookieStore,$rootScope,SessionService,StockReturntoWarehouseEditProductsControllerPreLoad) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.productVariantBean = {};
	$scope.productVariantBeansList = [];
	$scope.stockOrderDetailBean = {};
	$scope.delStockOrderDetailBean = {};
	$scope.stockOrderDetailBeansList = [];	
	$scope.counter = 1;
	$scope.hideRefValues = false;

	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.roleId = $cookieStore.get('_s_tk_rId');
			$scope.stockOrderBean = $cookieStore.get('_ct_bl_ost');
			$scope.headOffice = $cookieStore.get('_s_tk_iho');
			if($scope.stockOrderBean.retailPriceBill != null) {
				if($scope.stockOrderBean.retailPriceBill.toString() == "true"){
					$scope.stockOrderBean.retailPriceBill = true;
				}
				else{
					$scope.stockOrderBean.retailPriceBill = false;
				}
			}
			$scope.data = StockReturntoWarehouseEditProductsControllerPreLoad.loadControllerData();
			$scope.fetchData();
			var orderDate = new Date($scope.stockOrderBean.diliveryDueDate);
			var day = orderDate.getDate();
			var month = orderDate.getMonth() + 1;
			var year = orderDate.getFullYear();
			$scope.stockOrderBean.diliveryDueDate = month.toString() + "/" + day.toString() + "/" + year.toString();
			$scope.isAdmin();
			$scope.stockOrderBean.itemCount = 0;
			$scope.calculateItemCount();
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
		$rootScope.stockReturntoWarehouseEditProductsLoadedFully = false;
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
			if($scope.data.productBeansList != null){
				$scope.productBeansList = $scope.data.productBeansList;
				for (var i = 0; i < $scope.productVariantBeansList.length; i++) {
					$scope.productBeansList.push($scope.productVariantBeansList[i]);
				}
			}
			//$scope.calculateGrandTotal();
			$scope.calculateTotalAll();
			if($scope.isAdmin() == false){
				$scope.calculateTotalAllAdmin();
			}
		}
		$rootScope.globalPageLoader = false;
	};

	$scope.checkProductStatus = function(){
		if($scope.isAdmin() == true){
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
		}else{
			var quantity = $scope.stockOrderDetailBean.orderProdQty;
			$scope.stockOrderDetailBean.recvProdQty = $scope.stockOrderDetailBean.orderProdQty;
			if($scope.productVariantBean.isVariant.toString() == "true"){
				$scope.addProductAdmin();
			}
			else{
				$scope.hit = 0;
				if($scope.productVariantBean.isProduct.toString() == "true"){
					$scope.addProductAdmin();
					$scope.hit++;
				}
				else{
					for (var i = 0; i < $scope.productVariantBeansList.length; i++) {
						var productVariant = null;
						if($scope.productVariantBean.productVariantId == $scope.productVariantBeansList[i].productId){
							productVariant = angular.copy($scope.productVariantBean);
							$scope.productVariantBean = angular.copy($scope.productVariantBeansList[i]);
							$scope.stockOrderDetailBean.recvProdQty = angular.copy(quantity);
							$scope.addProductAdmin();
							$scope.productVariantBean = angular.copy(productVariant);
							$scope.hit++;
						}
					}
				}
				if($scope.hit < 1){
					$scope.productVariantBean.isProduct = "true";
					$scope.addProductAdmin();
				}
			}			
		}
	};



	$scope.addProduct  = function() {
		$scope.dualEntry = false;
		var productVariantBeantoReplace = {};
		var obj = $scope.productVariantBean;		
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
					$scope.stockOrderDetailBeansList.unshift($scope.stockOrderDetailBean);
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
			$scope.stockOrderDetailBeansList.unshift($scope.stockOrderDetailBean);
		}
		$scope.calculateTotal(obj.productVariantId);
		$scope.stockOrderDetailBean = {};
		$scope.calculateItemCount();
		$scope.airportName = [];
	};

	$scope.addProductAdmin  = function() {
		$scope.dualEntry = false;
		var obj = $scope.productVariantBean;		
		var productVariantBeantoReplace = {};
		if($scope.stockOrderDetailBeansList.length > 0){
			if(obj.isProduct != "true"){
				angular.forEach($scope.stockOrderDetailBeansList, function(value,key){
					if(value.productVariantId == obj.productVariantId && value.isProduct != "true" && obj.isProduct != "true"){
						if(isNaN(value.recvProdQty)){
							value.recvProdQty = "0";
						}
						value.recvProdQty = parseInt(value.recvProdQty) + parseInt($scope.stockOrderDetailBean.recvProdQty);
						if(value.ordrSupplyPrice > 0){
							value.recvTotal = value.ordrSupplyPrice * value.recvProdQty;
							if($scope.stockOrderBean.retailPriceBill == true){
								value.recvTotal = value.retailPrice * value.recvProdQty;
							}
							else{
								value.recvTotal = value.ordrSupplyPrice * value.recvProdQty;
							}
							if(isNaN(value.total)){
								value.recvTotal = "0";
							}
						}
						else{
							value.recvTotal = "0";
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
						$scope.stockOrderDetailBean.recvTotal = $scope.stockOrderDetailBean.retailPrice * $scope.stockOrderDetailBean.recvProdQty;
					}
					else{
						$scope.stockOrderDetailBean.recvTotal = $scope.stockOrderDetailBean.ordrSupplyPrice * $scope.stockOrderDetailBean.recvProdQty;
					}					
					if(isNaN($scope.stockOrderDetailBean.total)){
						$scope.stockOrderDetailBean.recvTotal = "0";
					}
					$scope.stockOrderDetailBean.order = $scope.counter;
					$scope.counter++;
					$scope.stockOrderDetailBean.stockOrderId = $scope.stockOrderBean.stockOrderId;
					$scope.stockOrderDetailBean.orderProdQty = "0";
					$scope.stockOrderDetailBean.total = "0";
					$scope.stockOrderDetailBeansList.unshift($scope.stockOrderDetailBean);
					$scope.dualEntry = false;
				}
			}
			else{
				angular.forEach($scope.stockOrderDetailBeansList, function(value,key){
					if(value.productVariantId == obj.productVariantId && value.isProduct == "true"){
						if(isNaN(value.recvProdQty)){
							value.recvProdQty = "0";
						}
						value.recvProdQty = parseInt(value.recvProdQty) + parseInt($scope.stockOrderDetailBean.recvProdQty);

						if(value.ordrSupplyPrice > 0){
							value.recvTotal = value.ordrSupplyPrice * value.recvProdQty;
							if($scope.stockOrderBean.retailPriceBill == true){
								value.recvTotal = value.retailPrice * value.recvProdQty;
							}
							else{
								value.recvTotal = value.ordrSupplyPrice * value.recvProdQty;
							}
							if(isNaN(value.recvTotal)){
								value.recvTotal = "0";
							}
						}
						else{
							value.recvTotal = "0";
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
						$scope.stockOrderDetailBean.recvTotal = $scope.stockOrderDetailBean.retailPrice * $scope.stockOrderDetailBean.recvProdQty;
					}
					else{
						$scope.stockOrderDetailBean.recvTotal = $scope.stockOrderDetailBean.ordrSupplyPrice * $scope.stockOrderDetailBean.recvProdQty;
					}
					if(isNaN($scope.stockOrderDetailBean.recvTotal)){
						$scope.stockOrderDetailBean.recvTotal = "0";
					}
					$scope.stockOrderDetailBean.order = $scope.counter;
					$scope.counter++;
					$scope.stockOrderDetailBean.stockOrderId = $scope.stockOrderBean.stockOrderId;
					$scope.stockOrderDetailBean.orderProdQty = "0";
					$scope.stockOrderDetailBean.total = "0";
					$scope.stockOrderDetailBeansList.unshift($scope.stockOrderDetailBean);
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
				$scope.stockOrderDetailBean.recvTotal = $scope.stockOrderDetailBean.retailPrice * $scope.stockOrderDetailBean.recvProdQty;
			}
			else{
				$scope.stockOrderDetailBean.recvTotal = $scope.stockOrderDetailBean.ordrSupplyPrice * $scope.stockOrderDetailBean.recvProdQty;
			}
			$scope.stockOrderDetailBean.order = $scope.counter;
			$scope.counter++;
			$scope.stockOrderDetailBean.stockOrderId = $scope.stockOrderBean.stockOrderId;
			$scope.stockOrderDetailBean.orderProdQty = "0";
			$scope.stockOrderDetailBean.total = "0";
			$scope.stockOrderDetailBeansList.unshift($scope.stockOrderDetailBean);
		}
		$scope.calculateTotalAdmin(obj.productVariantId);
		$scope.stockOrderDetailBean = {};
		$scope.calculateItemCountAdmin();
		$scope.airportName = [];
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
		$scope.calculateItemCount();
		$scope.calculateGrandTotal();
	};

	$scope.arrangeOrder = function(){
		$scope.counter = 1;
		if ($scope.stockOrderDetailBeansList.length > 0) {
			for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
				$scope.stockOrderDetailBeansList[i].order = $scope.counter;
				if($scope.stockOrderDetailBeansList[i].recvProdQty == null || $scope.stockOrderDetailBeansList[i].recvProdQty == ""){
					$scope.stockOrderDetailBeansList[i].recvProdQty = "0";
				}
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

	$scope.calculateTotalAdmin = function(productVariantId){
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
			}
		});
		$scope.calculateItemCountAdmin();
		$scope.calculateGrandrecvTotal();
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
	};

	$scope.calculateItemCountAdmin = function(){
		$scope.stockOrderBean.itemCountRecv = 0;
		if($scope.stockOrderDetailBeansList != null){
			for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
				$scope.stockOrderBean.itemCountRecv = parseInt($scope.stockOrderBean.itemCountRecv) + parseInt($scope.stockOrderDetailBeansList[i].recvProdQty);
				if(isNaN($scope.stockOrderBean.itemCountRecv) || $scope.stockOrderBean.itemCountRecv == ""){
					$scope.stockOrderBean.itemCountRecv = "0";
				}
			}
		}
	};

	$scope.calculateTotalAll = function(){
		if ($scope.stockOrderDetailBeansList.length > 0) {
			for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
				$scope.calculateTotal($scope.stockOrderDetailBeansList[i].productVariantId);

			}
		}
		$scope.calculateItemCount();
	};

	$scope.calculateTotalAllAdmin = function(){
		if ($scope.stockOrderDetailBeansList.length > 0) {
			for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
				$scope.calculateTotalAdmin($scope.stockOrderDetailBeansList[i].productVariantId);

			}
		}
		$scope.calculateItemCountAdmin();
	};
	
	
	$scope.calculateGrandTotal = function(){
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
	};

	$scope.calculateGrandrecvTotal = function(){
		$scope.grandrecvTotal = "0";
		if ($scope.stockOrderDetailBeansList.length > 0) {
			for (var i = 0; i < $scope.stockOrderDetailBeansList.length; i++) {
				if(isNaN($scope.stockOrderDetailBeansList[i].recvTotal)){
					$scope.stockOrderDetailBeansList[i].recvTotal = "0";
				}
				$scope.grandrecvTotal = parseFloat($scope.grandrecvTotal) + parseFloat($scope.stockOrderDetailBeansList[i].recvTotal);
				if(isNaN($scope.grandrecvTotal)){
					$scope.grandrecvTotal = "0";
				}
			}
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
					if($scope.stockOrderDetailBeansList[i].recvProdQty == "" || $scope.stockOrderDetailBeansList[i].recvProdQty == null){
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
		$scope.isAdmin();
	};

	$scope.showSendTransferStockOrder = function(){
		$scope.showConfirmSendTransferPopup = true;
	};
	$scope.transferStockOrder = function(){		
		$scope.success = false;
		$scope.error = false;
		$scope.loading = true;
		$scope.calculateGrandTotal();
		$scope.calculateGrandrecvTotal();
		$scope.stockOrderBean.statusId = "3"; // Completed status
		$http.post('purchaseOrderDetails/updateAndReturntoHeadOffice/'+$scope._s_tk_com+'/'+$scope.grandrecvTotal, $scope.stockOrderDetailBeansList)
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

	$scope.sessionValidation();
}];

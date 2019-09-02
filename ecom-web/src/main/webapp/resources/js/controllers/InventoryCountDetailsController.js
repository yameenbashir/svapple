'use strict';

/**
 * InventoryCountDetailsController
 * @constructor
 */
var InventoryCountDetailsController = ['$sce', '$scope', '$http', '$timeout', '$window','$cookieStore','$rootScope','SessionService','InventoryCountDetailsControllerPreLoad',function($sce, $scope, $http, $timeout, $window,$cookieStore,$rootScope,SessionService,InventoryCountDetailsControllerPreLoad) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.showConfirmDeletePopup = false;
	$scope.productVariantBean = {};
	$scope.productVariantBeansList = [];
	$scope.inventoryCountDetailBean = {};
	$scope.delInventoryCountDetailBean = {};
	$scope.inventoryCountDetailBeansList = [];
	$scope.hideRefValues = false;

	$scope.counter = 1;

	$scope.sessionValidation = function(){
		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.inventoryCountBean = $cookieStore.get('_ct_sc_ost');
			$scope.inventoryCountBean.itemCountExp = 0;
			$scope.inventoryCountBean.itemCountCounted = 0;
			if($scope.inventoryCountBean.inventoryCountTypeDesc.toString() == "FULL"){
				$scope.isFull = true;
			}
			else{
				$scope.isFull = false;
			}
			$scope.data = InventoryCountDetailsControllerPreLoad.loadControllerData();
			$scope.fetchData();			
		}
	};

	$scope.fetchData = function() {
		$rootScope.inventoryCountDetailsLoadedFully = false;
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
				if($scope.data.allProductVariantBeansList!=null){
					$scope.allProductVariantBeansList = $scope.data.allProductVariantBeansList;
				}
				if($scope.data.allProductBeansList != null){
					$scope.allProductBeansList = $scope.data.allProductBeansList;
					for (var i = 0; i < $scope.allProductVariantBeansList.length; i++) {
						$scope.allProductBeansList.push($scope.allProductVariantBeansList[i]);
					}
					$scope.hideTransferInfo = false;
				}
				else{
					$scope.hideTransferInfo = true;
				}
				if($scope.data.inventoryCountDetailBeansList!=null){
					$scope.inventoryCountDetailBeansList = $scope.data.inventoryCountDetailBeansList;
				}
				else{
					if($scope.inventoryCountBean.inventoryCountTypeDesc.toString() == "FULL"){
						for (var i = 0; i < $scope.productBeansList.length; i++) {
							$scope.productVariantBean = $scope.productBeansList[i];
							$scope.inventoryCountDetailBean.countedProdQty = 0;
							$scope.checkProductStatus();
							$scope.productVariantBean = null;
							$scope.inventoryCountDetailBean.countedProdQty = null;
						}
						$scope.addInventoryCountDetail();
					}
				}
			}
		}
		$rootScope.globalPageLoader = false;
	};


	/*$scope.selectFirst = function(){
		//$scope.productList = $scope.productVariantBeansList[0];
		$scope.productVariantBean = $scope.productVariantBeansList[0];	
	}*/

	$scope.checkProductStatus = function(){
		var quantity = $scope.inventoryCountDetailBean.countedProdQty;
		if($scope.productVariantBean.isVariant.toString() == "true"){
			if($scope.productVariantBean.auditTransfer == "true"){
				$scope.inventoryCountDetailBean.auditTransfer = "true"
			}
			else{
				$scope.inventoryCountDetailBean.auditTransfer = "false";
			}
			$scope.addProduct();
		}
		else{
			$scope.hit = 0;
			if($scope.productVariantBean.isProduct.toString() == "true"){
				if($scope.productVariantBean.auditTransfer == "true"){
					$scope.inventoryCountDetailBean.auditTransfer = "true"
				}
				else{
					$scope.inventoryCountDetailBean.auditTransfer = "false";
				}
				$scope.addProduct();
				$scope.hit++;
			}
			else{
				if($scope.productVariantBean.auditTransfer == "true"){
					for (var i = 0; i < $scope.allProductVariantBeansList.length; i++) {
						var productVariant = null;
						if($scope.productVariantBean.productVariantId == $scope.allProductVariantBeansList[i].productId){
							productVariant = angular.copy($scope.productVariantBean);
							$scope.productVariantBean = angular.copy($scope.allProductVariantBeansList[i]);
							$scope.inventoryCountDetailBean.countedProdQty = angular.copy(quantity);
							if($scope.productVariantBean.auditTransfer == "true"){
								$scope.inventoryCountDetailBean.auditTransfer = "true"
							}
							else{
								$scope.inventoryCountDetailBean.auditTransfer = "false";
							}
							$scope.addProduct();
							$scope.productVariantBean = angular.copy(productVariant);
							$scope.hit++;
						}
					}
				}else{
					for (var i = 0; i < $scope.productVariantBeansList.length; i++) {
						var productVariant = null;
						if($scope.productVariantBean.productVariantId == $scope.productVariantBeansList[i].productId){
							productVariant = angular.copy($scope.productVariantBean);
							$scope.productVariantBean = angular.copy($scope.productVariantBeansList[i]);
							$scope.inventoryCountDetailBean.countedProdQty = angular.copy(quantity);
							if($scope.productVariantBean.auditTransfer == "true"){
								$scope.inventoryCountDetailBean.auditTransfer = "true"
							}
							else{
								$scope.inventoryCountDetailBean.auditTransfer = "false";
							}
							$scope.addProduct();
							$scope.productVariantBean = angular.copy(productVariant);
							$scope.hit++;
						}
					}
				}
			}
			if($scope.hit < 1){
				$scope.productVariantBean.isProduct = "true";
				$scope.addProduct();
			}
		}	
		$scope.calculateItemCount();
	};

	$scope.addProduct  = function() {
		$scope.dualEntry = false;
		var obj = $scope.productVariantBean;			
		var productVariantBeantoReplace = {};	
		if($scope.inventoryCountDetailBeansList.length > 0){
			if(obj.isProduct != "true"){
				angular.forEach($scope.inventoryCountDetailBeansList, function(value,key){
					if(value.productVariantId == obj.productVariantId && value.isProduct != "true" && obj.isProduct != "true"){
						if(isNaN(value.countedProdQty)){
							value.countedProdQty = "0";
						}
						value.countedProdQty = parseInt(value.countedProdQty) + parseInt($scope.inventoryCountDetailBean.countedProdQty);
						if(value.retailPriceCounted > 0){
							value.retailPriceCounted = value.retailPriceCounted * value.countedProdQty;
							if(isNaN(value.retailPriceCounted)){
								value.retailPriceCounted = "0";
							}
						}
						else{
							value.retailPriceCounted = "0";
						}
						if(value.supplyPriceCounted > 0){
							value.supplyPriceCounted = value.supplyPriceCounted * value.countedProdQty;
							if(isNaN(value.supplyPriceCounted)){
								value.supplyPriceCounted = "0";
							}
						}
						else{
							value.supplyPriceCounted = "0";
						}
						if($scope.inventoryCountBean.auditTransfer != null && $scope.inventoryCountBean.auditTransfer == true){
							if(parseInt(value.countedProdQty) > parseInt(value.expProdQty)){
								value.auditTransfer = true;
							}
							else{
								value.auditTransfer = false;
							}
						}else{
							value.auditTransfer = false;
						}
						productVariantBeantoReplace =angular.copy(value);;
						var index = $scope.inventoryCountDetailBeansList.indexOf(value);
						$scope.inventoryCountDetailBeansList.splice(index, 1);
						$scope.inventoryCountDetailBeansList.unshift(productVariantBeantoReplace);
						$scope.arrangeOrder();
						$scope.dualEntry = true;
					}
				});
				if($scope.dualEntry != true){
					$scope.inventoryCountDetailBean.productVariantId = obj.productVariantId;
					$scope.inventoryCountDetailBean.variantAttributeName = obj.variantAttributeName;
					if($scope.productVariantBean.auditTransfer == "true"){
						$scope.inventoryCountDetailBean.expProdQty = "0";
					}else{
						$scope.inventoryCountDetailBean.expProdQty = obj.currentInventory;
					}
					$scope.inventoryCountDetailBean.isProduct = obj.isProduct;
					if(obj.retailPriceExclTax != null){
						$scope.inventoryCountDetailBean.retailPriceExp = ""; 
						$scope.inventoryCountDetailBean.retailPriceExp = angular.copy(obj.retailPriceExclTax);
						$scope.inventoryCountDetailBean.retailPriceExp = $scope.inventoryCountDetailBean.retailPriceExp * $scope.inventoryCountDetailBean.expProdQty;
					}
					$scope.inventoryCountDetailBean.retailPriceCounted = obj.retailPriceExclTax * $scope.inventoryCountDetailBean.countedProdQty;
					if(isNaN($scope.inventoryCountDetailBean.retailPriceCounted)){
						$scope.inventoryCountDetailBean.retailPriceCounted = "0";
					}
					if(obj.supplyPriceExclTax != null){
						$scope.inventoryCountDetailBean.supplyPriceExp = ""; 
						$scope.inventoryCountDetailBean.supplyPriceExp = angular.copy(obj.supplyPriceExclTax);
						$scope.inventoryCountDetailBean.supplyPriceExp = $scope.inventoryCountDetailBean.supplyPriceExp * $scope.inventoryCountDetailBean.expProdQty;
					}
					$scope.inventoryCountDetailBean.supplyPriceCounted = obj.supplyPriceExclTax * $scope.inventoryCountDetailBean.countedProdQty;
					if(isNaN($scope.inventoryCountDetailBean.supplyPriceCounted)){
						$scope.inventoryCountDetailBean.supplyPriceCounted = "0";
					}
					$scope.inventoryCountDetailBean.order = $scope.counter;
					$scope.counter++;
					$scope.inventoryCountDetailBean.inventoryCountId = $scope.inventoryCountBean.inventoryCountId;
					if($scope.inventoryCountBean.auditTransfer != null && $scope.inventoryCountBean.auditTransfer == true){
						if(parseInt($scope.inventoryCountDetailBean.countedProdQty) > parseInt($scope.inventoryCountDetailBean.expProdQty)){
							$scope.inventoryCountDetailBean.auditTransfer = true;
						}
						else{
							$scope.inventoryCountDetailBean.auditTransfer = false;
						}
					}else{
						$scope.inventoryCountDetailBean.auditTransfer = false;
					}
					$scope.inventoryCountDetailBeansList.unshift($scope.inventoryCountDetailBean);
					$scope.dualEntry = false;
				}
			}
			else{
				angular.forEach($scope.inventoryCountDetailBeansList, function(value,key){
					if(value.productVariantId == obj.productVariantId && value.isProduct == "true"){
						if(isNaN(value.countedProdQty)){
							value.countedProdQty = "0";
						}
						value.countedProdQty = parseInt(value.countedProdQty) + parseInt($scope.inventoryCountDetailBean.countedProdQty);
						if(value.retailPriceCounted > 0){
							value.retailPriceCounted = value.retailPriceCounted * value.countedProdQty;
							if(isNaN(value.retailPriceCounted)){
								value.retailPriceCounted = "0";
							}
						}
						else{
							value.retailPriceCounted = "0";
						}
						if(value.supplyPriceCounted > 0){
							value.supplyPriceCounted = value.supplyPriceCounted * value.countedProdQty;
							if(isNaN(value.supplyPriceCounted)){
								value.supplyPriceCounted = "0";
							}
						}
						else{
							value.supplyPriceCounted = "0";
						}
						if($scope.inventoryCountBean.auditTransfer != null && $scope.inventoryCountBean.auditTransfer == true){
							if(parseInt(value.countedProdQty) > parseInt(value.expProdQty)){
								value.auditTransfer = true;
							}
							else{
								value.auditTransfer = false;
							}
						}else{
							value.auditTransfer = false;
						}
						productVariantBeantoReplace =angular.copy(value);;
						var index = $scope.inventoryCountDetailBeansList.indexOf(value);
						$scope.inventoryCountDetailBeansList.splice(index, 1);
						$scope.inventoryCountDetailBeansList.unshift(productVariantBeantoReplace);
						$scope.arrangeOrder();
						$scope.dualEntry = true;
					}
				});
				if($scope.dualEntry != true){
					$scope.inventoryCountDetailBean.productVariantId = obj.productVariantId;
					$scope.inventoryCountDetailBean.variantAttributeName = obj.variantAttributeName;
					if($scope.productVariantBean.auditTransfer == "true"){
						$scope.inventoryCountDetailBean.expProdQty = "0";
					}else{
						$scope.inventoryCountDetailBean.expProdQty = obj.currentInventory;
					}
					$scope.inventoryCountDetailBean.isProduct = obj.isProduct;
					if(obj.retailPriceExclTax != null){
						$scope.inventoryCountDetailBean.retailPriceExp = ""; 
						$scope.inventoryCountDetailBean.retailPriceExp = angular.copy(obj.retailPriceExclTax);
						$scope.inventoryCountDetailBean.retailPriceExp = $scope.inventoryCountDetailBean.retailPriceExp * $scope.inventoryCountDetailBean.expProdQty;
					}
					$scope.inventoryCountDetailBean.retailPriceCounted = obj.retailPriceExclTax * $scope.inventoryCountDetailBean.countedProdQty;
					if(isNaN($scope.inventoryCountDetailBean.retailPriceCounted)){
						$scope.inventoryCountDetailBean.retailPriceCounted = "0";
					}
					if(obj.supplyPriceExclTax != null){
						$scope.inventoryCountDetailBean.supplyPriceExp = ""; 
						$scope.inventoryCountDetailBean.supplyPriceExp = angular.copy(obj.supplyPriceExclTax);
						$scope.inventoryCountDetailBean.supplyPriceExp = $scope.inventoryCountDetailBean.supplyPriceExp * $scope.inventoryCountDetailBean.expProdQty;
					}
					$scope.inventoryCountDetailBean.supplyPriceCounted = obj.supplyPriceExclTax * $scope.inventoryCountDetailBean.countedProdQty;
					if(isNaN($scope.inventoryCountDetailBean.supplyPriceCounted)){
						$scope.inventoryCountDetailBean.supplyPriceCounted = "0";
					}
					$scope.inventoryCountDetailBean.order = $scope.counter;
					$scope.counter++;
					$scope.inventoryCountDetailBean.inventoryCountId = $scope.inventoryCountBean.inventoryCountId;
					if($scope.inventoryCountBean.auditTransfer != null && $scope.inventoryCountBean.auditTransfer == true){
						if(parseInt($scope.inventoryCountDetailBean.countedProdQty) > parseInt($scope.inventoryCountDetailBean.expProdQty)){
							$scope.inventoryCountDetailBean.auditTransfer = true;
						}
						else{
							$scope.inventoryCountDetailBean.auditTransfer = false;
						}
					}else{
						$scope.inventoryCountDetailBean.auditTransfer = false;
					}
					$scope.inventoryCountDetailBeansList.unshift($scope.inventoryCountDetailBean);
					$scope.dualEntry = false;
				}
			}
		}
		else{
			$scope.inventoryCountDetailBean.productVariantId = obj.productVariantId;		
			$scope.inventoryCountDetailBean.variantAttributeName = obj.variantAttributeName;
			if($scope.productVariantBean.auditTransfer == "true"){
				$scope.inventoryCountDetailBean.expProdQty = "0";
			}else{
				$scope.inventoryCountDetailBean.expProdQty = obj.currentInventory;
			}
			$scope.inventoryCountDetailBean.isProduct = obj.isProduct;
			if(obj.retailPriceExclTax != null){
				$scope.inventoryCountDetailBean.retailPriceExp = ""; 
				$scope.inventoryCountDetailBean.retailPriceExp = angular.copy(obj.retailPriceExclTax);
				$scope.inventoryCountDetailBean.retailPriceExp = $scope.inventoryCountDetailBean.retailPriceExp * $scope.inventoryCountDetailBean.expProdQty;
			}
			$scope.inventoryCountDetailBean.retailPriceCounted = obj.retailPriceExclTax * $scope.inventoryCountDetailBean.countedProdQty;
			if(isNaN($scope.inventoryCountDetailBean.retailPriceCounted)){
				$scope.inventoryCountDetailBean.retailPriceCounted = "0";
			}
			if(obj.supplyPriceExclTax != null){
				$scope.inventoryCountDetailBean.supplyPriceExp = ""; 
				$scope.inventoryCountDetailBean.supplyPriceExp = angular.copy(obj.supplyPriceExclTax);
				$scope.inventoryCountDetailBean.supplyPriceExp = $scope.inventoryCountDetailBean.supplyPriceExp * $scope.inventoryCountDetailBean.expProdQty;
			}
			$scope.inventoryCountDetailBean.supplyPriceCounted = obj.supplyPriceExclTax * $scope.inventoryCountDetailBean.countedProdQty;
			if(isNaN($scope.inventoryCountDetailBean.supplyPriceCounted)){
				$scope.inventoryCountDetailBean.supplyPriceCounted = "0";
			}
			$scope.inventoryCountDetailBean.order = $scope.counter;
			$scope.counter++;
			$scope.inventoryCountDetailBean.inventoryCountId = $scope.inventoryCountBean.inventoryCountId;
			if($scope.inventoryCountBean.auditTransfer != null && $scope.inventoryCountBean.auditTransfer == true){
				if(parseInt($scope.inventoryCountDetailBean.countedProdQty) > parseInt($scope.inventoryCountDetailBean.expProdQty)){
					$scope.inventoryCountDetailBean.auditTransfer = true;
				}
				else{
					$scope.inventoryCountDetailBean.auditTransfer = false;
				}
			}else{
				$scope.inventoryCountDetailBean.auditTransfer = false;
			}
			$scope.inventoryCountDetailBeansList.unshift($scope.inventoryCountDetailBean);
		}
		$scope.calculateTotal($scope.productVariantBean);
		$scope.inventoryCountDetailBean = {};
		$scope.arrangeOrder();
		$scope.airportName = [];
	};

	$scope.changeQuantity = function(inventoryCountDetailBean){
		var hit = false;
		if(inventoryCountDetailBean.isProduct.toString() == "true" ){
			for (var i = 0; i < $scope.productBeansList.length; i++) {
				var productVariant = null;
				if(inventoryCountDetailBean.productVariantId == $scope.productBeansList[i].productVariantId){
					productVariant = angular.copy($scope.productBeansList[i]);
					$scope.calculateTotal(productVariant);
					hit = true;
					break;
				}
			}
			if(hit  == false){
				for (var i = 0; i < $scope.allProductBeansList.length; i++) {
					var productVariant = null;
					if(inventoryCountDetailBean.productVariantId == $scope.allProductBeansList[i].productVariantId){
						productVariant = angular.copy($scope.allProductBeansList[i]);
						$scope.calculateTotal(productVariant);
						hit = true;
						break;
					}
				}
			}
		}
		else{
			for (var i = 0; i < $scope.productVariantBeansList.length; i++) {
				var productVariant = null;
				if(inventoryCountDetailBean.productVariantId == $scope.productVariantBeansList[i].productVariantId){
					productVariant = angular.copy($scope.productVariantBeansList[i]);
					$scope.calculateTotal(productVariant);
					hit = true;
					break;
				}
			}
			if(hit  == false){
				for (var i = 0; i < $scope.allProductVariantBeansList.length; i++) {
					var productVariant = null;
					if(inventoryCountDetailBean.productVariantId == $scope.allProductVariantBeansList[i].productVariantId){
						productVariant = angular.copy($scope.allProductVariantBeansList[i]);
						$scope.calculateTotal(productVariant);
						hit = true;
						break;
					}
				}
			}
		}
		$scope.calculateItemCount();
	};
	
	$scope.calculateTotal = function(productVariantBean){
		angular.forEach($scope.inventoryCountDetailBeansList, function(value,key){
			if(value.productVariantId == productVariantBean.productVariantId && value.isProduct == productVariantBean.isProduct){
				if(value.retailPriceExp != null){
					value.retailPriceExp = productVariantBean.retailPriceExclTax * value.expProdQty;
					if(isNaN(value.retailPriceExp)){
						value.retailPriceExp = "0";
					}
				}
				if(value.retailPriceExp != null){
					value.retailPriceCounted = productVariantBean.retailPriceExclTax * value.countedProdQty;
					if(isNaN(value.retailPriceCounted)){
						value.retailPriceCounted = "0";
					}
				}
				if(value.supplyPriceExp != null){
					value.supplyPriceExp = productVariantBean.supplyPriceExclTax * value.expProdQty;
					if(isNaN(value.supplyPriceExp)){
						value.supplyPriceExp = "0";
					}
				}
				if(value.supplyPriceCounted != null){
					value.supplyPriceCounted = productVariantBean.supplyPriceExclTax * value.countedProdQty;
					if(isNaN(value.supplyPriceCounted)){
						value.supplyPriceCounted = "0";
					}
				}
				if($scope.inventoryCountBean.auditTransfer != null && $scope.inventoryCountBean.auditTransfer == true){
					if(parseInt(value.countedProdQty) > parseInt(value.expProdQty)){
						value.auditTransfer = true;
					}
					else{
						value.auditTransfer = false;
					}
				}else{
					value.auditTransfer = false;
				}
				value.countDiff = value.countedProdQty - value.expProdQty;
				value.priceDiff = value.retailPriceCounted - value.retailPriceExp;
			}
		});
	};

	$scope.calculateItemCount = function(){
		$scope.inventoryCountBean.itemCountCounted = 0;
		$scope.inventoryCountBean.itemCountExp = 0;
		$scope.inventoryCountBean.countDiff = 0;
		$scope.inventoryCountBean.priceDiff = 0;
		$scope.inventoryCountBean.retailPriceExp = 0;
		$scope.inventoryCountBean.retailPriceCounted = 0;
		if($scope.inventoryCountDetailBeansList != null){
			for (var i = 0; i < $scope.inventoryCountDetailBeansList.length; i++) {
				$scope.inventoryCountBean.itemCountCounted = parseInt($scope.inventoryCountBean.itemCountCounted) + parseInt($scope.inventoryCountDetailBeansList[i].countedProdQty);
				if(isNaN($scope.inventoryCountBean.itemCountCounted)){
					$scope.inventoryCountBean.itemCountCounted = "0";
				}				
				$scope.inventoryCountBean.itemCountExp = parseInt($scope.inventoryCountBean.itemCountExp) + parseInt($scope.inventoryCountDetailBeansList[i].expProdQty);
				if(isNaN($scope.inventoryCountBean.itemCountExp)){
					$scope.inventoryCountBean.itemCountExp = "0";
				}
				$scope.inventoryCountBean.countDiff = parseInt($scope.inventoryCountBean.countDiff) + parseInt($scope.inventoryCountDetailBeansList[i].countDiff);
				if(isNaN($scope.inventoryCountBean.countDiff)){
					$scope.inventoryCountBean.countDiff = "0";
				}
				$scope.inventoryCountBean.priceDiff = parseInt($scope.inventoryCountBean.priceDiff) + parseInt($scope.inventoryCountDetailBeansList[i].priceDiff);
				if(isNaN($scope.inventoryCountBean.priceDiff)){
					$scope.inventoryCountBean.priceDiff = "0";
				}
				$scope.inventoryCountBean.retailPriceExp = parseInt($scope.inventoryCountBean.retailPriceExp) + parseInt($scope.inventoryCountDetailBeansList[i].retailPriceExp);
				if(isNaN($scope.inventoryCountBean.retailPriceExp)){
					$scope.inventoryCountBean.retailPriceExp = "0";
				}
				$scope.inventoryCountBean.retailPriceCounted = parseInt($scope.inventoryCountBean.retailPriceCounted) + parseInt($scope.inventoryCountDetailBeansList[i].retailPriceCounted);
				if(isNaN($scope.inventoryCountBean.retailPriceCounted)){
					$scope.inventoryCountBean.retailPriceCounted = "0";
				}
				$scope.inventoryCountBean.supplyPriceExp = parseInt($scope.inventoryCountBean.supplyPriceExp) + parseInt($scope.inventoryCountDetailBeansList[i].supplyPriceExp);
				if(isNaN($scope.inventoryCountBean.supplyPriceExp)){
					$scope.inventoryCountBean.supplyPriceExp = "0";
				}
				$scope.inventoryCountBean.supplyPriceCounted = parseInt($scope.inventoryCountBean.supplyPriceCounted) + parseInt($scope.inventoryCountDetailBeansList[i].supplyPriceCounted);
				if(isNaN($scope.inventoryCountBean.supplyPriceCounted)){
					$scope.inventoryCountBean.supplyPriceCounted = "0";
				}
				if($scope.inventoryCountBean.auditTransfer != null && $scope.inventoryCountBean.auditTransfer == true){
					if(parseInt($scope.inventoryCountDetailBeansList[i].countedProdQty) > parseInt($scope.inventoryCountDetailBeansList[i].expProdQty)){
						$scope.inventoryCountDetailBeansList[i].auditTransfer = true;
					}
					else{
						$scope.inventoryCountDetailBeansList[i].auditTransfer = false;
					}
				}else{
					$scope.inventoryCountDetailBeansList[i].auditTransfer = false;
				}
			}
		}
	};

	$scope.showConfirmDelete = function(inventoryCountDetailBean){
		$scope.delInventoryCountDetailBean = {};
		$scope.delInventoryCountDetailBean = angular.copy(inventoryCountDetailBean);
		$scope.showConfirmDeletePopup = true;
	};

	$scope.delInventoryCountDetail = function(){
		angular.forEach($scope.inventoryCountDetailBeansList, function(value,key){
			if(value.productVariantId == $scope.delInventoryCountDetailBean.productVariantId && value.isProduct == $scope.delInventoryCountDetailBean.isProduct){
				var index = $scope.inventoryCountDetailBeansList.indexOf(value);
				$scope.inventoryCountDetailBeansList.splice(index, 1);
			}
		});	

		$scope.showConfirmDeletePopup = false; 
		$scope.delInventoryCountDetailBean = {};
		$scope.arrangeOrder();	
	};

	$scope.arrangeOrder = function(){
		$scope.counter = 1;
		if ($scope.inventoryCountDetailBeansList.length > 0) {
			for (var i = 0; i < $scope.inventoryCountDetailBeansList.length; i++) {
				$scope.inventoryCountDetailBeansList[i].order = $scope.counter;
				$scope.counter++;
			}
		}
	};

	$scope.checkInventoryCountDetailList = function() {
		if($scope.isFull = false){
			if ($scope.inventoryCountDetailBeansList.length > 0) { // your question said "more than one element"
				for (var i = 0; i < $scope.inventoryCountDetailBeansList.length; i++) {
					if($scope.inventoryCountDetailBeansList[i].countedProdQty == "" || $scope.inventoryCountDetailBeansList[i].countedProdQty == null){
						return false;
					}	   
				}
				return true;
			}
			else {
				return false;
			}
		}
		else{
			return true;
		}
	};	

	$scope.addInventoryCountDetail = function(){
		if ($scope.inventoryCountDetailBeansList.length > 0) {
			$scope.success = false;
			$scope.error = false;
			$scope.loading = true;
			$scope.inventoryCountBean.inventoryCountDetailBeansList = angular.copy($scope.inventoryCountDetailBeansList);
			$http.post('inventoryCountDetails/updateInventoryCountDetail/'+$scope._s_tk_com, $scope.inventoryCountBean)
			.success(function(Response) {
				$scope.loading = false;					
				$scope.responseStatus = Response.status;
				if ($scope.responseStatus == 'SUCCESSFUL') {
					$scope.success = true;
					$scope.successMessage = Response.data;
					$cookieStore.put('_ct_sc_ost',"") ;
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

	$scope.addAndUpdateInventoryCountDetail = function(){
		if ($scope.inventoryCountDetailBeansList.length > 0) {
			$scope.success = false;
			$scope.error = false;
			$scope.loading = true;
			$scope.inventoryCountBean.inventoryCountDetailBeansList = angular.copy($scope.inventoryCountDetailBeansList);
			$http.post('inventoryCountDetails/updateQtyAndUpdateInventoryCountDetail/'+$scope._s_tk_com, $scope.inventoryCountBean)
			.success(function(Response) {
				$scope.loading = false;					
				$scope.responseStatus = Response.status;
				if ($scope.responseStatus == 'SUCCESSFUL') {
					$scope.success = true;
					$scope.successMessage = Response.data;
					$cookieStore.put('_ct_sc_ost',"") ;
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
					$scope.inventoryCountDetailBean.countedProdQty = 1;
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

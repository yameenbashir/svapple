'use strict';

/**
 * ProductDetailsController
 * @constructor
 */
var ProductDetailsController = ['$scope', '$http', '$window','$cookieStore','$rootScope','SessionService','ProductDetailsControllerPreLoad','$timeout',function($scope, $http, $window,$cookieStore,$rootScope,SessionService,ProductDetailsControllerPreLoad,$timeout) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.gui = [];
	$scope.productHistoryBeansList = [];
	$scope.gui.dataLoading = true;
	$scope.gui.dataLoadingCompleted = false;
	$scope.printCount =  40;
	$scope.roledId = $cookieStore.get('_s_tk_rId');
	$scope.bc = { "format": "CODE128", "width": 1, "height": 44, "displayValue": false, "marginRight": 10 }
	$scope.sesssionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.fetchData();
		}
	};	

	$scope.loadProductHistory = function() {
		
		$scope.gui.dataLoading = false;
		$http.post('productDetails/getProductHistoryByProductUuid/'+$scope._s_tk_com+'/'+$scope.product.productUuid)
		.success(function(Response) {
			$scope.gui.dataLoading = true;
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.gui.dataLoadingCompleted = true;
				if(Response.data!=null){

					$scope.productHistoryBeansList  = Response.data;
					var table = $('#myTable1').DataTable();
					if(table){
						 table.destroy();
					}
					setTimeout(
							function() 
							{
								$('#myTable1').DataTable( {
									responsive: true/*,
									paging: true,
									pageLength: 5,
									searching:true,
									bInfo : true,
									dom : 'Bfrtip',
									buttons :$rootScope.buttonsView*/
								} );
								
							}, 100);
				}
				
			}else  {
			
				//$window.location = Response.layOutPath;
			}
		}).error(function() {
			$scope.gui.dataLoading = true;
			$scope.outletError = true;
			$scope.outletErrorMessage = $scope.systemBusy;
		});
		
	};
	
	$scope.printLabels =  true;
	
	$scope.fetchData = function(){

		$scope.data = ProductDetailsControllerPreLoad.loadControllerData();
		$rootScope.productDetailsLoadedFully = false;
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
				if($scope.data.productBean!=null){
					$scope.product = {};
					$scope.product  = $scope.data.productBean;
					if($scope.product.productVariantsBeans!==null && $scope.product.productVariantsBeans.length>0){
						var sum = 0.0;
						for(var i=0;i<$scope.product.productVariantsBeans.length;i++){
							sum = sum + parseFloat($scope.product.productVariantsBeans[i].netPrice);
						}
						$scope.averagePrice = ((sum/$scope.product.productVariantsBeans.length).toFixed(2)).toString();
					}else{
						$scope.averagePrice = $scope.product.netPrice;
					}
					
				}
				if($scope.data.productHistoryBeans!=null){

					$scope.productHistoryBeansList  = $scope.data.productHistoryBeans;
				}
			}
		}
		$rootScope.globalPageLoader = false;
	};
	String.prototype.replaceAll = function(search, replacement) {
		var target = this;
		return target.replace(new RegExp(search, 'g'), replacement);
	};
		$scope.editProduct = function(){
			$rootScope.impersonate = $cookieStore.get("impersonate");
			if($rootScope.impersonate){
				$rootScope.permissionDenied();
				return;
			}
			$cookieStore.put('_e_cPi_gra',$scope.product.productId) ;
			$cookieStore.put('_e_cOi_gra',$scope.product.outletId) ;
			$window.location = "/app/#/manageProduct";

		};
		$scope.printCountChange = function(){
			$scope.printCount =  $scope.printCounting;
			$scope.layoutproducts = [];
			for(var index=0; index<$scope.printCount; index++){
				$scope.layoutproduct = {};
				$scope.layoutproduct.id=index;
				$scope.layoutproduct.productName=$scope.printLabelsProduct.productName ;
				$scope.layoutproduct.sku=$scope.printLabelsProduct.sku ;
				$scope.layoutproduct.price=$scope.printLabelsProduct.price ;
				$scope.layoutproducts.push($scope.layoutproduct);
			}
			$timeout(function(){
				JsBarcode(".barcode").init();
				$scope.printLabels =  false;
			    }, 1500);
		}
		$scope.layoutproducts = [];
		$scope.layoutproduct = {};
		$scope.printLoading = false;
		$scope.printLabelsProduct = {};
		$scope.printLabelsAll = function(productList){
			$scope.printLoading = true;
			for(var indexParent=0; indexParent<productList.productVariantsBeans.length; indexParent++){
				var product =  productList.productVariantsBeans[indexParent];
				if(product.variantAttributeName){
					product.productName = "" + product.productName  +" " + product.variantAttributeName.replaceAll("/","-");
				}
				for(var index=0; index<product.printCount; index++){
					$scope.layoutproduct = {};
					$scope.layoutproduct.id=index;
					$scope.layoutproduct.productName=product.productName ;
					$scope.layoutproduct.sku= product.sku;
					$scope.layoutproduct.price=product.netPrice ;
					$scope.layoutproduct.productNameOnly=product.productName;
					$scope.layoutproduct.variantAttributeValue1= product.variantAttributeValue1;
					$scope.layoutproduct.variantAttributeValue2=product.variantAttributeValue2 ;
					
					$scope.layoutproducts.push($scope.layoutproduct);
				}
			}
			
			
			
			
			$timeout(function(){
				JsBarcode(".barcode").init();
				$scope.printLabels =  false;
				$scope.printLoading = false;
				$scope.hideCount = true;
				
			    }, 1500);
			
		};
		$scope.hideCount = false;
		$scope.printLabels = function(product){
			$scope.printLoading = true;
			$scope.printLabelsProduct.sku = product.sku;
			
			$scope.printLabelsProduct.productName = product.productName;
			if(product.variantAttributeName){
				$scope.printLabelsProduct.productName = "" + $scope.printLabelsProduct.productName  +" " + product.variantAttributeName.replaceAll("/","-");
			}
			$scope.printLabelsProduct.price = product.netPrice;
	//		$rootScope.productId = angular.copy($scope.product.productId);
	//		$rootScope.outletId = angular.copy($scope.product.outletId);
	//		$window.location = "/app/#/printLabels";
			for(var index=0; index<product.printCount; index++){
				$scope.layoutproduct = {};
				$scope.layoutproduct.id=index;
				$scope.layoutproduct.productName=$scope.printLabelsProduct.productName ;
				$scope.layoutproduct.productNameOnly=product.productName;
				$scope.layoutproduct.sku=$scope.printLabelsProduct.sku ;
				$scope.layoutproduct.price=$scope.printLabelsProduct.price ;
				$scope.layoutproduct.variantAttributeValue1= product.variantAttributeValue1;
				$scope.layoutproduct.variantAttributeValue2=product.variantAttributeValue2 ;
				//$scope.layoutproduct.productDesc = $scope.product.productDesc;
				$scope.layoutproducts.push($scope.layoutproduct);
			}
			$timeout(function(){
				JsBarcode(".barcode").init();
				$scope.printLabels =  false;
				$scope.printLoading = false;
			    }, 1500);
			
		};
		$scope.printThisElementA4 = "printThisElement";
		$scope.printOptionsA4 = true;
		$scope.changePrinter = function(){
			if($scope.printOptions=="A4"){
				$scope.printOptionsA4 = true;
				$scope.printOptionsContinous = false;
				$scope.printOptionsContinousLarge = false;
				$scope.printOptionsContinousNew = false;
				$scope.printOptionsContinousNewZebra = false;
				$scope.printOptionsContinousDouble = false;
				$scope.printOptionsContinousCustom = false;
				$scope.printThisElementA4 = "printThisElement";
				$scope.printThisElementContinous = "";
				$scope.printThisElementContinousLarge = "";
				$scope.printThisElementContinousNew = "";
				$scope.printThisElementContinousNewZebra = "";
				$scope.printThisElementContinousDouble = "";
				$scope.printThisElementContinousCustom = "";
			}else if($scope.printOptions=="Continous"){
				$scope.printOptionsA4 = false;
				$scope.printOptionsContinous = true;
				$scope.printOptionsContinousLarge = false;
				$scope.printOptionsContinousNew = false;
				$scope.printOptionsContinousNewZebra = false;
				$scope.printOptionsContinousDouble = false;
				$scope.printOptionsContinousCustom = false;
				$scope.printThisElementA4 = "";
				$scope.printThisElementContinous = "printThisElement";
				$scope.printThisElementContinousLarge = "";
				$scope.printThisElementContinousNew = "";
				$scope.printThisElementContinousNewZebra = "";
				$scope.printThisElementContinousDouble = "";
				$scope.printThisElementContinousCustom = "";
			}else if($scope.printOptions=="ContinousNew"){
				$scope.printOptionsA4 = false;
				$scope.printOptionsContinous = false;
				$scope.printOptionsContinousLarge = false;
				$scope.printOptionsContinousNew = true;
				$scope.printOptionsContinousNewZebra = false;
				$scope.printOptionsContinousDouble = false;
				$scope.printOptionsContinousCustom = false;
				$scope.printThisElementA4 = "";
				$scope.printThisElementContinousNew = "printThisElement";
				$scope.printThisElementContinousLarge = "";
				$scope.printThisElementContinous = "";
				$scope.printThisElementContinousNewZebra = "";
				$scope.printThisElementContinousDouble = "";
				$scope.printThisElementContinousCustom = "";
			}else if($scope.printOptions=="ContinousLarge"){
				$scope.printOptionsA4 = false;
				$scope.printOptionsContinous = false;
				$scope.printOptionsContinousLarge = true;
				$scope.printOptionsContinousNew = false;
				$scope.printOptionsContinousNewZebra = false;
				$scope.printOptionsContinousDouble = false;
				$scope.printOptionsContinousCustom = false;
				$scope.printThisElementA4 = "";
				$scope.printThisElementContinous = "";
				$scope.printThisElementContinousLarge = "printThisElement";
				$scope.printThisElementContinousNew = "";
				$scope.printThisElementContinousNewZebra = "";
				$scope.printThisElementContinousDouble = "";
				$scope.printThisElementContinousCustom = "";
			}else if($scope.printOptions=="ContinousNewZebra"){
				$scope.printOptionsA4 = false;
				$scope.printOptionsContinous = false;
				$scope.printOptionsContinousLarge = false;
				$scope.printOptionsContinousNew = false;
				$scope.printOptionsContinousNewZebra = true;
				$scope.printOptionsContinousDouble = false;
				$scope.printOptionsContinousCustom = false;
				$scope.printThisElementA4 = "";
				$scope.printThisElementContinousNew = "";
				$scope.printThisElementContinousLarge = "";
				$scope.printThisElementContinous = "";
				$scope.printThisElementContinousNewZebra = "printThisElement";
				$scope.printThisElementContinousDouble = "";
				$scope.printThisElementContinousCustom = "";
			}else if($scope.printOptions=="ContinousDouble"){
				$scope.printOptionsA4 = false;
				$scope.printOptionsContinous = false;
				$scope.printOptionsContinousLarge = false;
				$scope.printOptionsContinousNew = false;
				$scope.printOptionsContinousNewZebra = false;
				$scope.printOptionsContinousDouble = true;
				$scope.printOptionsContinousCustom = false;
				$scope.printThisElementA4 = "";
				$scope.printThisElementContinousNew = "";
				$scope.printThisElementContinousLarge = "";
				$scope.printThisElementContinous = "";
				$scope.printThisElementContinousNewZebra = "";
				$scope.printThisElementContinousDouble = "printThisElement";
				$scope.printThisElementContinousCustom = "";
			}else if($scope.printOptions=="ContinousCustom"){
				$scope.printOptionsA4 = false;
				$scope.printOptionsContinous = false;
				$scope.printOptionsContinousLarge = false;
				$scope.printOptionsContinousNew = false;
				$scope.printOptionsContinousNewZebra = false;
				$scope.printOptionsContinousDouble = false;
				$scope.printOptionsContinousCustom = true;
				$scope.printThisElementA4 = "";
				$scope.printThisElementContinous = "";
				$scope.printThisElementContinousLarge = "";
				$scope.printThisElementContinousNew = "";
				$scope.printThisElementContinousNewZebra = "";
				$scope.printThisElementContinousDouble = "";
				$scope.printThisElementContinousCustom = "printThisElement";
			}
			
			
		};
		$scope.sesssionValidation();

}];
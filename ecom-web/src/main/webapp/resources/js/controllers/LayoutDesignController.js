'use strict';

/**
 * LayoutDesignController
 * @constructor
 */
var LayoutDesignController = ['$timeout','$scope', '$http','$sce', '$window','$cookieStore','$rootScope','SessionService','LayoutDesignControllerPreLoad',function($timeout,$scope, $http,$sce, $window,$cookieStore,$rootScope,SessionService,LayoutDesignControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	//SessionService.validate();	
	$scope.layoutProducts = [];
	$scope.layoutProduct = {};
	$scope.sesssionValidation = function() {

		if (SessionService.validate()) {
			$scope._s_tk_com = $cookieStore.get('_s_tk_com');

			$scope.layoutDesignControllerBean = LayoutDesignControllerPreLoad.loadControllerData();
			$scope.allProducts = $scope.layoutDesignControllerBean.productsBean;
			$rootScope.layoutDesignLoadedFully = false;
			$scope.showProductLayout();
			localforage.getItem('_s_tk_sell').then(function(value) {
				$scope.InvoiceMainBean = value;
				if ($scope.InvoiceMainBean == ''
						|| typeof $scope.InvoiceMainBean == 'undefined') {
					$scope.InvoiceMainBean = {};
				}
			});
		
		}
	};
	$scope.allProductsSelected =[];
	$scope.saveLayout = function() {
		$scope.doneLoading = true;
		for (var i = 0; i < $scope.layoutProducts.length ; i++) {
			for (var j = 0; j < $scope.allProducts.length ; j++) {
				if($scope.allProducts[j].productId === $scope.layoutProducts[i].id){
					$scope.allProducts[j].display = "true"; 
					$scope.allProductsSelected[$scope.allProducts[j]];
				}
			}
		}
		$scope.success = false;
		
		$http.post('layoutDesign/saveLayout/' + $scope._s_tk_com,$scope.allProducts)
				.success(function(Response) {
							

							$scope.responseStatus = Response.status;
							if ($scope.responseStatus == 'SUCCESSFUL') {
								// $scope.InvoiceMainBean = {};
								$scope.success = true;
								$timeout(function() {
									$scope.success = false;
								}, 1000);
								$scope.doneLoading = false;
								$scope.successMessage = Response.data;
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
	
	$scope.showProductLayout = function() {
	
		for (var i = 0; i < $scope.allProducts.length ; i++) {

			if($scope.allProducts[i].display === "true"){
				$scope.layoutProduct = {};
				$scope.layoutProduct.productName =  $scope.allProducts[i].productName;
				$scope.layoutProduct.id = $scope.allProducts[i].productId;
				$scope.layoutProducts.push($scope.layoutProduct);
			}
		
		}
		for (var i = $scope.allProducts.length; i < 40-$scope.allProductsSelected.length; i++) {

			$scope.layoutProduct = {};
			$scope.layoutProduct.productName = "";
			$scope.layoutProduct.id = "dummy" + i;
			$scope.layoutProducts.push($scope.layoutProduct);

		}
		$rootScope.globalPageLoader = false;
	};
	$scope.addProduct = function(product) {
		$scope.layoutProduct = {};
		$scope.layoutProduct.id = product.productId;
		$scope.layoutProduct.productName = product.productName;
		$scope.layoutProducts.push($scope.layoutProduct);
		
		for (var i = 0; i < $scope.allProducts.length ; i++) {

			if($scope.allProducts[i].productId == product.productId){
				$scope.allProducts[i].display = "true"; 
			}
		
		}


	};
	$scope.autoCompleteOptions = {
			minimumChars : 1,
			dropdownHeight : '200px',
			data : function(term) {
				term = term.toLowerCase();
				$scope.productVariantsBeans = [];
				var productResults = _.filter($scope.allProducts, function(val) {
					return val.productName.toLowerCase().includes(term) && val.display != "true";

				});
				
				return productResults;
			},
			renderItem : function(item) {
				var result = {
						value : item.productName,
						label : $sce.trustAsHtml("<table class='auto-complete'>"
								+ "<tbody>" + "<tr>" + "<td style='width: 90%'>"
								+ item.productName + "</td>"
								+ "<td style='width: 10%'>" + item.retailPriceExclTax
								+ "</td>" + "</tr>" + "</tbody>" + "</table>")
					};
				return result;
			},
			itemSelected : function(item) {
				$scope.selectedItem = item;
				$scope.addProduct($scope.selectedItem.item);
				$scope.airportName = [];
			}
		}
	$scope.sesssionValidation ();
	
}];
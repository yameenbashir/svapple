'use strict';

/**
 * EcomProductsController
 * @constructor
 */
var EcomProductsController = ['$scope', '$http', '$window','$cookieStore','$rootScope','SessionService','EcomProductsControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,SessionService,EcomProductsControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = EcomProductsControllerPreLoad.loadControllerData();
			$scope.fetchData();
						
		}
	};

	$scope.fetchData = function() {
		$rootScope.ecomProductsLoadedFully = false;
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
			
		}
		$rootScope.globalPageLoader = false;
	};
	$scope.sessionValidation();
	$scope.allowSale=true;
	$scope.addProduct = function() {
		$window.location = "/app/#/newProduct";
	};
	$scope.showProduct = function() {
		$window.location = "/app/#/productDetails";
	};
}];
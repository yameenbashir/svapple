'use strict';

/**
 * SmsDetailController
 * @constructor
 */
var SmsDetailController = ['$scope', '$http', '$window','$cookieStore','$rootScope','SessionService','SmsDetailControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,SessionService,SmsDetailControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.roledId = $cookieStore.get('_s_tk_rId');
	$scope.addProduct = function() {
		$window.location = "/app/#/newProduct";
	};
	$scope.showProduct = function(product) {
		
		$cookieStore.put('_d_cPi_gra',product.productId) ;
		$window.location = "/app/#/productDetails";
	};
	
	$scope.editProduct = function(product){
		
		$cookieStore.put('_e_cPi_gra',product.productId) ;
		$cookieStore.put('_e_cOi_gra',product.outletId) ;
		$window.location = "/app/#/manageProduct";
	};
	
	$scope.sesssionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.fetchData();
		}
	};
	
	$scope.fetchData = function(){
		$scope.data = SmsDetailControllerPreLoad.loadControllerData();
		
		if($scope.data == 'NORECORDFOUND' ||$scope.data == 'No record found !'){

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
				$scope.productsList = null;
				$scope.productsList  = $scope.data.smsReportBeanList;
				$scope.smsUsedCount  = $scope.data.smsUsedCount;
				$scope.smsRemained  = $scope.data.smsRemained;
				

			}
		}
		
		$rootScope.globalPageLoader = false;
	};
	
	
	
	$scope.sesssionValidation();
}];
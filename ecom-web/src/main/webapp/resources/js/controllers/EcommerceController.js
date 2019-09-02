'use strict';

/**
 * EcommerceController
 * @constructor
 */
var EcommerceController = ['$scope', '$http', '$window','$cookieStore','$rootScope','SessionService','EcommerceControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,SessionService,EcommerceControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = EcommerceControllerPreLoad.loadControllerData();
			$scope.fetchData();
						
		}
	};

	$scope.fetchData = function() {
		$rootScope.ecommerceLoadedFully = false;
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
}];
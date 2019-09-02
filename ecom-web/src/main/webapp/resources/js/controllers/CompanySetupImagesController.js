'use strict';

/**
 * CompanySetupImagesController
 * 
 * @constructor
 */
var CompanySetupImagesController = ['$scope',' $http', '$window', '$cookieStore','$rootScope', 'SessionService', 'CompanySetupImagesControllerPreLoad','$timeout','$route',function($scope, $http, $window, $cookieStore,$rootScope, SessionService, CompanySetupImagesControllerPreLoad,$timeout,$route) {

	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.companyImpagesList = [];
	$scope.configuration = {};
	
	$scope.sessionValidation = function(){
		
		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = CompanySetupImagesControllerPreLoad.loadControllerData();
			$scope.fetchData();
		}
	};
	
	$scope.fetchData = function() {
		if($scope.data == 'NORECORDFOUND' || $scope.data == 'No record found !'){

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
				$scope.companyImpagesList = $scope.data;
			}
		}
		$rootScope.globalPageLoader = false;
	};

	$scope.showImage = function(configurationId){
		if($scope.companyImpagesList.length>0){
			for(var i=0;i<$scope.companyImpagesList.length;i++){
				if(configurationId == $scope.companyImpagesList[i].configurationId){
					$scope.configuration = {};
					$scope.configuration = angular.copy($scope.companyImpagesList[i]);
				}
			}
		}
		
	};
	
	
	$scope.cashInOut = function() {
		$http.post('cashManagement/cashInOut/' + $scope._s_tk_com,$scope.cashManagmentBean).success(
				function(Response) {
					$scope.responseStatus = Response.status;
					if ($scope.responseStatus == 'SUCCESSFUL') {
						$scope.showCashModal = false;
						$scope.cashManagmentList = Response.data;
						$scope.addCashProcessingButton = false;
						$scope.removeCashProcessingButton = false;
						$timeout(function(){
						      $route.reload();
						    }, 1000);
					} else if ($scope.responseStatus == 'SYSTEMBUSY'
							|| $scope.responseStatus == 'INVALIDUSER'
							|| $scope.responseStatus == 'ERROR'
							|| $scope.responseStatus == 'INVALIDSESSION') {
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
			$scope.errorMessage = $scope.systemBusy;
		});
		
	};
	
	
	
	$scope.sessionValidation();
}];
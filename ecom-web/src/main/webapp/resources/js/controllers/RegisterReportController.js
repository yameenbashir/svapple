'use strict';

/**
 * RegisterReportController
 * @constructor
 */

var RegisterReportController = ['$scope', '$http', '$window','$cookieStore','$rootScope','SessionService','$timeout','$interval','$route','$filter','RegisterReportControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,SessionService,$timeout,$interval,$route,$filter,RegisterReportControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.inventoryReportSuccess = false;
	$scope.inventoryReportError = false;
	$scope.inventoryReportError = false;
	$scope.isHeadOffice = false;
	$scope.registerReport = [];
	$scope.outlets = [];
	
	$scope.outletSelected = $rootScope.inventoryReportOutletName;
	$scope.sesssionValidation = function(){
		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = RegisterReportControllerPreLoad.loadControllerData();
			$scope.fetchData();
		}
	};


	$scope.fetchData = function() {
		
		if($scope.data == 'NORECORDFOUND'|| $scope.data =='No record found !'){

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
				if($scope.data.registerReportBeansList!=null){
					$scope.registerReport = $scope.data.registerReportBeansList;
				}
				if($scope.data.outletBeans!=null){
					$scope.outlets = $scope.data.outletBeans;
				}
				if($scope.data.headOffice!=null){
					$scope.isHeadOffice = $scope.data.headOffice;
				}
			}
		}
		$rootScope.globalPageLoader = false;
	};
		
	
	
	$scope.changeOutletName = function() {
		$rootScope.inventoryReportOutletName = $scope.outletName;
		$route.reload();
	};
	
	$scope.showRegisterDetail = function(register) {
		
		$cookieStore.put('_d_Ri_gra',register.dailyRegisterId) ;
		$window.location = "/app/#/registerDetail";
	};
	
	
	
	$scope.sesssionValidation();
}];
'use strict';

/**
 * ManageOutletController
 * @constructor
 */
var ManageOutletController = ['$scope', '$http', '$window','$cookieStore','$rootScope','$timeout','SessionService','ManageOutletControllerPreLoad',function($scope, $http, $window,$cookieStore,$rootScope,$timeout,SessionService,ManageOutletControllerPreLoad) {
	
	$rootScope.MainSideBarhideit = false;
	$rootScope.MainHeaderideit = false;
	$scope.loading = false;
	$scope.outletSuccess = false;
	$scope.outletError = false;
	$scope.systemBusy = 'System is Busy Please try again';
	
	$scope.sessionValidation = function(){

		if(SessionService.validate()){
			$scope._s_tk_com =  $cookieStore.get('_s_tk_com') ;
			$scope.data = ManageOutletControllerPreLoad.loadControllerData();
			$scope.fetchData();
		}
	};

	$scope.fetchData = function() {
		$rootScope.manageOutletLoadedFully = false;
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
				if($scope.data.timeZoneBeans!=null){
					$scope.timeZonesList = $scope.data.timeZoneBeans;
				}
				if($scope.data.countryListBeans!=null){
					$scope.countryList = $scope.data.countryListBeans;
				}
				if($scope.data.salesTaxListBeans!=null){
					$scope.salesTaxList = $scope.data.salesTaxListBeans;
				}
				if($scope.data.outletBean!=null){
					$scope.outletBean = $scope.data.outletBean;
					
				}

			}
		}
		$rootScope.globalPageLoader = false;
	};

	$scope.updateOutlet = function() {
		$scope.outletSuccess = false;
		$scope.outletError = false;
		$scope.loading = true;
		$http.post('manageOutlet/updateOutlet/'+$scope._s_tk_com, $scope.outletBean)
		.success(function(Response) {
			$scope.loading = false;
			
			$scope.responseStatus = Response.status;
			if ($scope.responseStatus == 'SUCCESSFUL') {
				$scope.companyBean = {};
				$scope.outletSuccess = true;
				$scope.outletSuccessMessage = Response.data;
				$timeout(function(){
					$scope.outletSuccess = false;
					$window.location = Response.layOutPath;
				    }, 1000);
				
			}else if($scope.responseStatus == 'INVALIDSESSION'||$scope.responseStatus == 'SYSTEMBUSY') {
				$scope.outletError = true;
				$scope.outletErrorMessage = Response.data;
				$window.location = Response.layOutPath;
			}else {
				$scope.outletError = true;
				$scope.outletErrorMessage = Response.data;
			}
		}).error(function() {
			$scope.loading = false;
			$scope.outletError = true;
			$scope.outletErrorMessage = $scope.systemBusy;
		});
	};

	$scope.sessionValidation();
	$rootScope.globalPageLoader = false;
}];